package dna.example.demo.subscription.domain.model.event.sourcing;

import java.time.Instant;

public record SubscriptionResumed(SubscriptionId subscriptionId, Instant timestamp) implements DomainEvent {
}
