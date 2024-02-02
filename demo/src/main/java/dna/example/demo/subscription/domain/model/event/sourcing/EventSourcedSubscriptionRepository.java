package dna.example.demo.subscription.domain.model.event.sourcing;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EventSourcedSubscriptionRepository implements SubscriptionRepository {
    private final Map<SubscriptionId, List<DomainEvent>> storedEvents = new HashMap<>();

    @Override
    public void save(Subscription subscription) {
        var currentEvents = storedEvents.getOrDefault(subscription.getSubscriptionId(), new ArrayList<>());
        currentEvents.addAll(subscription.getPendingEvents());
        storedEvents.put(subscription.getSubscriptionId(), currentEvents);
        subscription.flushEvents();
    }

    @Override
    public Subscription findById(SubscriptionId id) {
        return Subscription.recreateFrom(storedEvents.get(id), new Subscription(id));
    }
}
