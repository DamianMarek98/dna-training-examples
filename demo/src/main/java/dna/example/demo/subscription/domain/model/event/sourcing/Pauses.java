package dna.example.demo.subscription.domain.model.event.sourcing;

import java.time.Duration;
import java.time.Instant;

class Pauses {
    int yetAvailablePauses = 2;
    Instant lastPause = null;

    void markNewPauseAt(Instant when) {
        lastPause = when;
        yetAvailablePauses--;
    }

    boolean canPauseAt(Instant when) {
        return anyPauseAvailable() && enoughDaysSinceLastPause(when);
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
}
