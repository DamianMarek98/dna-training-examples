package dna.example.demo.subscription.domain.model.event.sourcing;

import java.time.Instant;

public record SubscriptionMarkedAsPastDue(SubscriptionId subscriptionId, Instant timestamp) implements DomainEvent {
}
