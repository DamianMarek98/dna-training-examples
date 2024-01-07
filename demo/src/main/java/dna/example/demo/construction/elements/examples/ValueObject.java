package dna.example.demo.construction.elements.examples;

import java.util.Objects;

final class ValueObject {

    final int capacity;

    ValueObject(int capacity) {
        this.capacity = capacity;
    }

    public static ValueObject of(int capacity) {
        if (capacity < 0) {
            throw new CapacityCannotBeNegativeException("Capacity cannot be negative!");
        }
        return new ValueObject(capacity);
    }

    public static ValueObject of(int capacity, boolean specificType) { //should be for example Enum or type of something
        if (capacity < 0) {
            throw new CapacityCannotBeNegativeException("Capacity cannot be negative!");
        }

        if (specificType && capacity > 20) {
            throw new MaximumCapacityFOrSpecificTypeException("Specific type have maximum capacity of 20!");
        }

        return new ValueObject(capacity);
    }

    public boolean canAccept(int expectedAttendance) {
        return expectedAttendance <= capacity;
    }

    public ValueObject add(int additionalCapacity) {
        return of(capacity + additionalCapacity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueObject that = (ValueObject) o;
        return capacity == that.capacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity);
    }
}
