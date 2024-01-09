package dna.example.demo.construction.elements.examples.event.publication;

import dna.example.demo.common.DomainEvent;

import java.util.UUID;

public class ExampleEntityWithInternalEventsCollection extends EntityWithInternalEventsCollection {
    private boolean paused;

    public void domainOperation() {//command - blue design level event storming card
        if (paused) { //rule yellow design level event storming card
            return;
        }
        paused = true;
        raise(new PausedDomainEvent(UUID.randomUUID())); //event orange design level event storming card
    }
}

record PausedDomainEvent(UUID eventId) implements DomainEvent {
}