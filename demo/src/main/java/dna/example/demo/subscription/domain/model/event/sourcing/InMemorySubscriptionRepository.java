package dna.example.demo.subscription.domain.model.event.sourcing;

import java.util.HashMap;
import java.util.Map;

public class InMemorySubscriptionRepository implements SubscriptionRepository {
    private final Map<SubscriptionId, Subscription> subscriptions = new HashMap<>();

    @Override
    public void save(Subscription subscription) {
        subscriptions.put(subscription.getSubscriptionId(), subscription);
    }

    @Override
    public Subscription findById(SubscriptionId id) {
        return subscriptions.get(id);
    }
}
