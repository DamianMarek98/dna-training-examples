package dna.example.demo.subscription.domain.model.event.sourcing;

import dna.example.demo.common.Result;
import io.vavr.API;
import io.vavr.Predicates;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.vavr.API.$;
import static io.vavr.collection.List.ofAll;

class Subscription {
    private final SubscriptionId subscriptionId;
    private final Pauses pauses = new Pauses();
    private Status status = Status.New;
    private final List<DomainEvent> pendingEvents = new ArrayList<>();

    Subscription(SubscriptionId subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    //if only we had snapshots -> here we would add snapshot parameter
    static Subscription recreateFrom(List<DomainEvent> domainEvents, Subscription initialState) {
        return ofAll(domainEvents)
                .foldLeft(initialState, Subscription::apply);
    }

    private Subscription apply(DomainEvent event) {
        return API.Match(event).of(
                API.Case($(Predicates.instanceOf(SubscriptionActivated.class)), this::handle),
                API.Case($(Predicates.instanceOf(SubscriptionDeactivated.class)), this::handle),
                API.Case($(Predicates.instanceOf(SubscriptionPaused.class)), this::handle),
                API.Case($(Predicates.instanceOf(SubscriptionResumed.class)), this::handle),
                API.Case($(Predicates.instanceOf(SubscriptionMarkedAsPastDue.class)), this::handle));
    }

    public SubscriptionId getSubscriptionId() {
        return subscriptionId;
    }

    //komenda, stan -> zdarzenia
    Result activate() {
        handle(new SubscriptionActivated(subscriptionId, Instant.now()));
        return Result.success();
    }

    //stan, zdarzenie -> stan
    //when loading we should not add to pendingEvents - can add flag e.g. isHistory
    Subscription handle(SubscriptionActivated event) {
        pendingEvents.add(event);
        status = Status.Activated;
        return this;
    }

    Subscription deactivate() {
        handle(new SubscriptionDeactivated(subscriptionId, Instant.now()));
        return this;
    }

    Subscription handle(SubscriptionDeactivated event) {
        pendingEvents.add(event);
        status = Status.Deactivated;
        return this;
    }

    Result pause() {
        return pause(Instant.now());
    }

    Result pause(Instant when) { // command  - blue card
        if (isActive() && pauses.canPauseAt(when)) { //invariants - yellow cards
            handle(new SubscriptionPaused(subscriptionId, Instant.now(), when)); //domain event - orange card
            return Result.success();
        }
        return Result.failure("Cannot pause");
    }

    Subscription handle(SubscriptionPaused event) {
        pendingEvents.add(event);
        pauses.markNewPauseAt(event.timeOfPause());
        status = Status.Paused;
        return this;
    }

    private boolean isActive() {
        return status == Status.Activated;
    }

    Result resume() {
        if (isPaused()) {
            handle(new SubscriptionResumed(subscriptionId, Instant.now()));
            return Result.success();
        }
        return Result.failure("Cannot resume");
    }

    Subscription handle(SubscriptionResumed event) {
        pendingEvents.add(event);
        status = Status.Activated;
        return this;
    }

    private boolean isPaused() {
        return status == Status.Paused;
    }

    Result markAsPastDue() {
        handle(new SubscriptionMarkedAsPastDue(subscriptionId, Instant.now()));
        return Result.success();
    }

    Subscription handle(SubscriptionMarkedAsPastDue event) {
        pendingEvents.add(event);
        status = Status.PastDue;
        return this;
    }

    public List<DomainEvent> getPendingEvents() {
        return Collections.unmodifiableList(pendingEvents);
    }

    public void flushEvents() {
        pendingEvents.clear();
    }

    enum Status {New, Activated, Deactivated, Paused, PastDue}
}
