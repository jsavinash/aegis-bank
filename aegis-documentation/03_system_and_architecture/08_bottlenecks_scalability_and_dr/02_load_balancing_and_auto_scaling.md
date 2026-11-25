# Load Balancing and Auto-Scaling

## Load balancing

| Tier | Strategy |
|---|---|
| **Edge** | Global LB (latency/geo routing) |
| **API** | Round-robin / least-connections |
| **gRPC** | Client-side LB or service mesh (Istio) |
| **Database** | Read replicas, connection pooling |

- **Health checks** (liveness/readiness); **session affinity** where stateful; **consistent hashing** for caches.

## Auto-scaling

| Workload | Scaling signal |
|---|---|
| **Stateless services** | CPU / RPS / queue depth |
| **AI inference** | GPU utilisation, queue latency |
| **Event consumers** | Consumer lag |
| **Workers** | Queue depth (scale-to-zero when idle) |

## Scaling strategies

- **Horizontal** first (stateless services); **vertical** for stateful (DB, GPU nodes).
- **Predictive** for known peaks (payday, holidays); **scheduled** for batch windows.
- **Cooldown** to avoid flapping.

## Kubernetes

- **Karpenter / Cluster Autoscaler** for node provisioning.
- **HPA** on custom metrics; **resource requests/limits** for QoS.
- **PodDisruptionBudgets**; **topology spread** across AZs.

## Service mesh

- **Istio:** mTLS, retries, timeouts, **traffic splitting** (canary), circuit breaking.

## Capacity headroom

- **N+1** redundancy for Tier 0; **burst capacity** via cloud elasticity.
- **Load tests** validate headroom quarterly; autoscaling events logged/alerted.
