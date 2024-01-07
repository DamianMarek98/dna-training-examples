package dna.example.demo.common;

public interface Specification<T> {
    boolean satisfiedBy(T t);

    default Specification<T> and(Specification<T> specification) {
        return t -> satisfiedBy(t) && specification.satisfiedBy(t);
    }
    // etc ...
}
