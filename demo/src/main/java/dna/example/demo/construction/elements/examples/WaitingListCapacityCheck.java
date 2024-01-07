package dna.example.demo.construction.elements.examples;

import dna.example.demo.common.Specification;

//FIXME could be additional WaitingListSizeSpecification interface (that extends Specification) to extend
class WaitingListCapacityCheck implements Specification<CompanySubscriptionEntity> {
    @Override
    public boolean satisfiedBy(CompanySubscriptionEntity companySubscriptionEntity) {
        return false;
        //return companySubscriptionEntity.numberOfWaitingSubscribers() < 10;
    }
}
