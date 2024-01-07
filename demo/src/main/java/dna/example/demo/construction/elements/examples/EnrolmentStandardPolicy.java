package dna.example.demo.construction.elements.examples;

class EnrolmentStandardPolicy implements EnrolmentPolicy {
    @Override
    public boolean canEnrol(int numberOfParticipants) {
        return numberOfParticipants <= 20;
    }
}
