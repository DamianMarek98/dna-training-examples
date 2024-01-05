package dna.example.demo.subscription.domain.model;

import dna.example.demo.common.Result;

class Subscription {
    Result activate() {
        return Result.success();
    }

    Result deactivate() {
        return Result.success();
    }

    Result pause() {
        return Result.success();
    }

    Result resume() {
        return Result.success();
    }

    Result markAsPastDue() {
        return Result.success();
    }
}
