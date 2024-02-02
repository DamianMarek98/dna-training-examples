package dna.example.demo.subscription.domain.model.event.sourcing

import spock.lang.Specification
import spock.lang.Subject

class SubscriptionRepositoryTest extends Specification {
    @Subject
    SubscriptionRepository subscriptionRepository = new EventSourcedSubscriptionRepository();

    def 'should save and load'() {
        given:
        Subscription sub = new Subscription(new SubscriptionId(1L))
        and:
        sub.activate()
        and:
        subscriptionRepository.save(sub)
        when:
        Subscription load = subscriptionRepository.findById(sub.getSubscriptionId())
        then:
        load.resume().isFailure()
    }
}
