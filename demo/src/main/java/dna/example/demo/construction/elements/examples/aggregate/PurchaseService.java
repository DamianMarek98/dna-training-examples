package dna.example.demo.construction.elements.examples.aggregate;

class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ExtraProductPolicy productPolicy; //moglibyśmy wstrzyknąc fabrykę polityk

    public PurchaseService(PurchaseRepository purchaseRepository, ExtraProductPolicy extraProductPolicy) {
        this.purchaseRepository = purchaseRepository;
        this.productPolicy = extraProductPolicy;
    }

    void addProduct(Product product, PurchaseId purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId);
        purchase.addProduct(product, productPolicy);
        purchaseRepository.save(purchase);
    }

    void removeProduct(Product product, PurchaseId purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId);
        purchase.removeProduct(product, productPolicy);
        purchaseRepository.save(purchase);
    }

    void intentionallyRemoveFreeProduct(Product product, PurchaseId purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId);
        purchase.intentionallyRemoveFreeProduct(product);
        purchaseRepository.save(purchase);
    }

    void addBackFreeProduct(Product product, PurchaseId purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId);
        purchase.addBackFreeProduct(product);
        purchaseRepository.save(purchase);
    }
}
