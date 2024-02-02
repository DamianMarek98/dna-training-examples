package dna.example.demo.subscription.domain.model.event.sourcing;

import java.time.Instant;

public record SubscriptionDeactivated(SubscriptionId subscriptionId, Instant timestamp) implements DomainEvent {
}
