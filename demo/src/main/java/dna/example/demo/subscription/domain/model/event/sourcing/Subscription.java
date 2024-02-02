package dna.example.demo.subscription.domain.model.event.sourcing;

import dna.example.demo.common.Result;

import java.time.Instant;

class Subscription {
    private SubscriptionId subscriptionId;
    private final Pauses pauses = new Pauses();
    private Status status = Status.New;

    Result activate() {
        subscriptionActivated(new SubscriptionActivated(subscriptionId, Instant.now()));
        return Result.success();
    }

    private void subscriptionActivated(SubscriptionActivated event) {
        status = Status.Activated;
    }

    Result deactivate() {
        subscriptionDeactivated(new SubscriptionDeactivated(subscriptionId, Instant.now()));
        return Result.success();
    }

    private void subscriptionDeactivated(SubscriptionDeactivated event) {
        status = Status.Deactivated;
    }

    Result pause() {
        return pause(Instant.now());
    }

    Result pause(Instant when) { // command  - blue card
        if (isActive() && pauses.canPauseAt(when)) { //invariants - yellow cards
            subscriptionPaused(new SubscriptionPaused(subscriptionId, Instant.now(), when)); //domain event - orange card
            return Result.success();
        }
        return Result.failure("Cannot pause");
    }

    private void subscriptionPaused(SubscriptionPaused event) {
        pauses.markNewPauseAt(event.timeOfPause());
        status = Status.Paused;
    }

    private boolean isActive() {
        return status == Status.Activated;
    }

    Result resume() {
        if (isPaused()) {
            subscriptionResumed(new SubscriptionResumed(subscriptionId, Instant.now()));
            return Result.success();
        }
        return Result.failure("Cannot resume");
    }

    private void subscriptionResumed(SubscriptionResumed event) {
        status = Status.Activated;
    }

    private boolean isPaused() {
        return status == Status.Paused;
    }

    Result markAsPastDue() {
        subscriptionMarkedAsPastDue(new SubscriptionMarkedAsPastDue(subscriptionId, Instant.now()));
        return Result.success();
    }

    private void subscriptionMarkedAsPastDue(SubscriptionMarkedAsPastDue event) {
        status = Status.PastDue;
    }

    enum Status {New, Activated, Deactivated, Paused, PastDue}
}
