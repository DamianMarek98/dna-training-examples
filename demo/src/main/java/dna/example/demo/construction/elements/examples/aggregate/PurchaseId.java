package dna.example.demo.construction.elements.examples.aggregate;

import java.util.Objects;
import java.util.UUID;

record PurchaseId(UUID uuid) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseId purchaseId = (PurchaseId) o;
        return Objects.equals(uuid, purchaseId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
