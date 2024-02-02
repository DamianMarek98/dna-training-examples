package dna.example.demo.subscription.domain.model.event.sourcing


import spock.lang.Specification
import spock.lang.Subject

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class SubscriptionTest extends Specification {

    Instant someDay = LocalDate.of(1989, 12, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()

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
        subscription.handle(new SubscriptionActivated(subscription.getSubscriptionId(), Instant.now()))
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
        assert subscription.pause(someDay + Duration.ofDays(10)).isSuccessful()
        assert subscription.resume().isSuccessful()
        and:
        assert subscription.pause(someDay + Duration.ofDays(20)).isSuccessful()
        assert subscription.resume().isSuccessful()

        expect:
        subscription.pause(someDay + Duration.ofDays(100)).isFailure()

    }

    def 'should not pause if less than 10 days from last pause'() {
        given:
        subscription.activate()
        and:
        assert subscription.pause(someDay + Duration.ofDays(10)).isSuccessful()
        assert subscription.resume().isSuccessful()

        expect:
        subscription.pause(someDay + Duration.ofDays(19)).isFailure()
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

