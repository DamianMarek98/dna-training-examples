package dna.example.demo.subscription.domain.model.event.sourcing;

public interface SubscriptionRepository {
    void save(Subscription subscription);

    Subscription findById(SubscriptionId id);
}
