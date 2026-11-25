package com.aegis.security;

import com.aegis.security.command.RaiseAlert;
import com.aegis.security.command.RegisterDevice;
import com.aegis.security.domain.Device;
import com.aegis.security.domain.FraudAlert;
import com.aegis.security.error.AlertNotFound;
import com.aegis.security.error.DeviceNotFound;
import com.aegis.security.web.DeviceView;
import com.aegis.security.web.FraudAlertView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

/**
 * Security &amp; Fraud application service.
 *
 * <p>Concurrency: a per-device and a per-alert {@link ReentrantLock} serialize all mutations to a
 * single entity, so concurrent trust/revoke or confirm/dismiss calls can never interleave (mirrors
 * the reference implementation's per-entity lock). Storage is in-memory {@link ConcurrentHashMap}s.
 */
@Service
public class SecurityService {

  private final Map<UUID, Device> devices = new ConcurrentHashMap<>();
  private final Map<UUID, FraudAlert> alerts = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> deviceLocks = new ConcurrentHashMap<>();
  private final Map<UUID, ReentrantLock> alertLocks = new ConcurrentHashMap<>();
  private final Set<String> frozen = ConcurrentHashMap.newKeySet();

  public DeviceView registerDevice(RegisterDevice req) {
    Device device = new Device(UUID.randomUUID(), req.customerId(), req.fingerprint());
    devices.put(device.id(), device);
    return DeviceView.from(device);
  }

  public DeviceView getDevice(UUID id) {
    return DeviceView.from(requireDevice(id));
  }

  public List<DeviceView> listDevices() {
    List<DeviceView> out = new ArrayList<>(devices.size());
    for (Device d : devices.values()) {
      out.add(DeviceView.from(d));
    }
    return out;
  }

  public DeviceView trustDevice(UUID id) {
    return mutateDevice(id, Device::trust);
  }

  public DeviceView revokeDevice(UUID id) {
    return mutateDevice(id, Device::revoke);
  }

  public FraudAlertView raiseAlert(RaiseAlert req) {
    double score = FraudAlert.riskScore(req.amount().doubleValue());
    FraudAlert alert = new FraudAlert(UUID.randomUUID(), req.customerId(), req.amount(), score);
    alerts.put(alert.id(), alert);
    return FraudAlertView.from(alert);
  }

  public FraudAlertView getAlert(UUID id) {
    return FraudAlertView.from(requireAlert(id));
  }

  public List<FraudAlertView> listAlerts() {
    List<FraudAlertView> out = new ArrayList<>(alerts.size());
    for (FraudAlert a : alerts.values()) {
      out.add(FraudAlertView.from(a));
    }
    return out;
  }

  public FraudAlertView confirmAlert(UUID id) {
    return mutateAlert(id, FraudAlert::confirm);
  }

  public FraudAlertView dismissAlert(UUID id) {
    return mutateAlert(id, FraudAlert::dismiss);
  }

  public void freeze(String customerId) {
    frozen.add(requireText(customerId, "customerId"));
  }

  public boolean isFrozen(String customerId) {
    return frozen.contains(customerId);
  }

  private DeviceView mutateDevice(UUID id, Consumer<Device> action) {
    ReentrantLock lock = deviceLocks.computeIfAbsent(id, k -> new ReentrantLock());
    lock.lock();
    try {
      Device device = requireDevice(id);
      action.accept(device);
      return DeviceView.from(device);
    } finally {
      lock.unlock();
    }
  }

  private FraudAlertView mutateAlert(UUID id, Consumer<FraudAlert> action) {
    ReentrantLock lock = alertLocks.computeIfAbsent(id, k -> new ReentrantLock());
    lock.lock();
    try {
      FraudAlert alert = requireAlert(id);
      action.accept(alert);
      return FraudAlertView.from(alert);
    } finally {
      lock.unlock();
    }
  }

  private Device requireDevice(UUID id) {
    Device device = devices.get(id);
    if (device == null) {
      throw new DeviceNotFound(id);
    }
    return device;
  }

  private FraudAlert requireAlert(UUID id) {
    FraudAlert alert = alerts.get(id);
    if (alert == null) {
      throw new AlertNotFound(id);
    }
    return alert;
  }

  private static String requireText(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(field + " must not be blank");
    }
    return value;
  }
}