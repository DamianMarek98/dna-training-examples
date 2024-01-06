package dna.example.demo.subscription.domain.model;

import dna.example.demo.common.Result;

import java.time.Instant;

class IndividualSubscription {

    private final Pauses pauses = new Pauses();
    private Status status = Status.New;

    Result activate() {
        status = Status.Activated;
        return Result.success();
    }

    Result deactivate() {
        status = Status.Deactivated;
        return Result.success();
    }

    Result pause() {
        return pause(Instant.now());
    }

    Result pause(Instant when) {
        if (isActive() && pauses.canPauseAt(when)) {
            pauses.markNewPauseAt(when);
            status = Status.Paused;
            return Result.success();
        }
        return Result.failure("Cannot pause");
    }

    private boolean isActive() {
        return status == Status.Activated;
    }

    Result resume() {
        if (isPaused()) {
            status = Status.Activated;
            return Result.success();
        }
        return Result.failure("Cannot resume");
    }

    private boolean isPaused() {
        return status == Status.Paused;
    }

    Result markAsPastDue() {
        status = Status.PastDue;
        return Result.success();
    }

    enum Status {New, Activated, Deactivated, Paused, PastDue}
}
