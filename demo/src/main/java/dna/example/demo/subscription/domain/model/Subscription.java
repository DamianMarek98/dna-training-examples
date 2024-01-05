package dna.example.demo.subscription.domain.model;

import dna.example.demo.common.Result;

import java.time.Duration;
import java.time.Instant;

class Subscription {

    private Status status = Status.New;
    private int yetAvailablePauses = 2;
    private Instant lastPause = null;

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
        if (isActive() && enoughDaysSinceLastPause(when) && anyPauseAvailable()) {
            lastPause = when;
            yetAvailablePauses--;
            status = Status.Paused;
            return Result.success();
        }
        return Result.failure("Cannot pause");
    }

    private boolean anyPauseAvailable() {
        return yetAvailablePauses > 0;
    }

    private boolean enoughDaysSinceLastPause(Instant when) {
        if (lastPause == null) {
            return true;
        }

        return Duration.between(lastPause, when).toDays() >= 10;
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
