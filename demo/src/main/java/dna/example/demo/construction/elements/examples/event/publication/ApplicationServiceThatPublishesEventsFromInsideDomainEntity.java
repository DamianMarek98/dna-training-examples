package dna.example.demo.construction.elements.examples.event.publication;

import dna.example.demo.common.DomainEvent;

public class ApplicationServiceThatPublishesEventsFromInsideDomainEntity {
    //private final Repository repository;
    private final DomainEventPublisher publisher = new DomainEventPublisher() {
        @Override
        public void publish(DomainEvent event) {
            //fixme publish event, can be any implementation, should be extracted as component
        }
    };

    //db transaction etc
    void doSomething(Long companySubscriptionId) {
        //fixme find from repo
        var entity = new ExampleEntityWithInternalEventsCollection();
        entity.domainOperation(); //TODO instead of collection inside class domain events could be returned in result Either or just as list
        var events = entity.getChanges();
        publisher.publish(events);
        entity.clearEvents();
        //fixme save to repo
        //fixme return successful result
    }

    //TODO note that:
    //in this implementation we do everything in one transaction, we could extract publishing events to additional transaction,
    //or do transaction per event listener/handler
    //if we want more flexibility we can publish events async with at least once delivery (idempotent events listener operation) or at most once
    //we save events to database and try to publish then with scheduler, job (it's called OUTBOX PATTERN)
    //in such case we just save event to special bucket for them
}
