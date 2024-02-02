package dna.example.demo.subscription.domain.model.event.sourcing;

import java.time.Instant;

public record SubscriptionPaused(SubscriptionId subscriptionId, Instant timestamp,
                                 Instant timeOfPause) implements DomainEvent {
}
