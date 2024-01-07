package dna.example.demo.construction.elements.examples;

import java.util.List;

public class CompanySubscriptionEntity {
    private WaitingList waitingList;
    private Subscribers subscribers;
    private WaitingListCapacityCheck waitingListCapacityCheck = new WaitingListCapacityCheck();

    public boolean enrol(SubscriberId subscriberId, EnrolmentPolicy enrolmentPolicy) {
        if (waitingList.alreadyContains(subscriberId)) {
            return false; //possibly throw domain exception
        }

        if (!enrolmentPolicy.canEnrol(subscribers.numberOfSubscribers())) {
            enrollForWaitingList(subscriberId);
            return false; //possibly throw domain exception
        }

        // etc ...

        return true;
    }

    public boolean enrollForWaitingList(SubscriberId subscriberId) {
        if (waitingListCapacityCheck.satisfiedBy(this)) {
            //enroll
            return true;
        }

        return false;
    }


    class WaitingList {
        List<SubscriberId> waitingList;

        boolean alreadyContains(SubscriberId subscriberId) {
            return waitingList.contains(subscriberId);
        }
        //domain operations, ubiquitous language, validation etc
    }

    class Subscribers {
        List<SubscriberId> subscribers;
        //domain operations, ubiquitous language, validation etc

        int numberOfSubscribers() {
            return subscribers.size();
        }
    }
}
