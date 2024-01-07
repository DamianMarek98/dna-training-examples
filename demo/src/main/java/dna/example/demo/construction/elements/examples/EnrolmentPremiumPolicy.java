package dna.example.demo.construction.elements.examples;

class EnrolmentPremiumPolicy implements EnrolmentPolicy {
    @Override
    public boolean canEnrol(int numberOfParticipants) {
        return numberOfParticipants <= 100;
    }
}
