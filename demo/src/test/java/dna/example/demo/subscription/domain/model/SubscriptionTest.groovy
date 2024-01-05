package dna.example.demo.subscription.domain.model

import spock.lang.Specification
import spock.lang.Subject

class SubscriptionTest extends Specification {
    @Subject
    Subscription subscription = new Subscription();

    def 'should activate new sub'() {
        expect:
        subscription.activate().isSuccessful()
    }

    def 'should deactivate active sub'() {

    }

    def 'should pause activated sub'() {
        given:
        subscription.activate()
        expect:
        subscription.pause().isSuccessful()
    }

    def 'should not pause not active sub'() {
        expect:
        subscription.pause().isFailure()
    }

    def 'should not pause when all pauses used'() {
        given:
        subscription.activate()
        and:
        2.times {
            assert subscription.pause().isSuccessful()
            assert subscription.resume().isSuccessful()
        }

        expect:
        subscription.pause().isFailure()
    }

    def 'should nit pause if less than 10 days from last pause'() {

    }

    def 'should resume sub'() {
        given:
        subscription.activate()
        and:
        subscription.pause()
        expect:
        subscription.resume().isSuccessful()
    }

    def 'should mark as past due'() {

    }
}
