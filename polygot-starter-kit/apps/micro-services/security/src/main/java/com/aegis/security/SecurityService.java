package com.aegis.security;
import java.util.*; import java.util.concurrent.ConcurrentHashMap; import org.springframework.stereotype.Service;
@Service
public class SecurityService {
  private final Map<UUID,Device> devices = new ConcurrentHashMap<>();
  private final Set<String> frozen = ConcurrentHashMap.newKeySet();
  public Device register(String customerId, String fp){ Device d=new Device(UUID.randomUUID(),customerId,fp,false); devices.put(d.id(),d); return d; }
  public Device trust(UUID id){ Device d=devices.get(id); if(d==null) return null; Device n=new Device(d.id(),d.customerId(),d.fingerprint(),true); devices.put(id,n); return n; }
  public double risk(String customerId, double amount){ return amount>10000?0.9:0.1; }
  public void freeze(String customerId){ frozen.add(customerId); }
  public boolean isFrozen(String customerId){ return frozen.contains(customerId); }
  public List<Device> devices(){ return new ArrayList<>(devices.values()); }
}
