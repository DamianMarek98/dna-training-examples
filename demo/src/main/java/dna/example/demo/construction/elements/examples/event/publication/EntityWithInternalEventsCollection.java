package dna.example.demo.construction.elements.examples.event.publication;

import dna.example.demo.common.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityWithInternalEventsCollection {
    private final List<DomainEvent> events = new ArrayList<>();

    protected void raise(DomainEvent event) {
        events.add(event);
    }

    public List<DomainEvent> getChanges() {
        return List.copyOf(events);
    }

    public void clearEvents() {
        events.clear();
    }
}
