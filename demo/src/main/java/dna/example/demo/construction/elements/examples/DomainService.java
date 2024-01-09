package dna.example.demo.construction.elements.examples;

import dna.example.demo.common.Result;

class DomainService {

    // gets domain objects - do not talk with db or other systems, contexts
    // testable without with unit tests
    Result doSomething(CompanySubscriptionEntity companySubscriptionEntity) {
        final SubscriberId subscriberId = new SubscriberId(1L);
        if (true) {
            companySubscriptionEntity.enrol(subscriberId, new EnrolmentStandardPolicy());
            return Result.success();
        }

        companySubscriptionEntity.enrol(subscriberId, new EnrolmentPremiumPolicy());
        return Result.success();
    }
}
