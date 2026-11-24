# Load Balancing and Auto-Scaling

## Load balancing

| Tier | Strategy |
|---|---|
| **Edge** | Global LB (latency/geo routing) |
| **API** | Round-robin / least-connections |
| **gRPC** | Client-side LB or service mesh (Istio) |
| **Database** | Read replicas, connection pooling |

## Auto-scaling

| Workload | Scaling signal |
|---|---|
| **Stateless services** | CPU / RPS / queue depth |
| **AI inference** | GPU utilisation, queue latency |
| **Event consumers** | Consumer lag |
| **Workers** | Queue depth |

## Scaling strategies

- **Horizontal** first (stateless services).
- **Vertical** for stateful (DB, GPU nodes).
- **Predictive** for known peaks (payday, holidays).
- **Scheduled** for batch windows.

## Kubernetes

- **Karpenter / Cluster Autoscaler** for node provisioning.
- **HPA** (horizontal pod autoscaler) on custom metrics.
- **Resource requests/limits** to ensure QoS.
- **PodDisruptionBudgets** for availability.

## Capacity headroom

- **N+1** redundancy for Tier 0.
- **Burst capacity** via cloud elasticity.
- **Load tests** validate headroom quarterly.