package dna.example.demo.construction.elements.examples.aggregate;

import java.util.HashMap;
import java.util.Map;

interface PurchaseRepository {
    Purchase findById(PurchaseId purchaseId);

    void save(Purchase purchase);
}

class PurchaseDatabase implements PurchaseRepository {
    private final Map<PurchaseId, Purchase> purchases = new HashMap<>();

    @Override
    public Purchase findById(PurchaseId purchaseId) {
        return purchases.get(purchaseId);
    }

    @Override
    public void save(Purchase purchase) {
        purchases.put(purchase.purchaseId, purchase);
    }
}
