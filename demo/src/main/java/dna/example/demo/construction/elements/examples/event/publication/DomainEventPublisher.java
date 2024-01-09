package dna.example.demo.construction.elements.examples.event.publication;

import dna.example.demo.common.DomainEvent;

import java.util.List;

interface DomainEventPublisher {
    void publish(DomainEvent event);

    default void publish(List<DomainEvent> events) {
        events.forEach(this::publish);
    }
}
