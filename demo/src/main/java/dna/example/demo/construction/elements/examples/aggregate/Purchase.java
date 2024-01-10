package dna.example.demo.construction.elements.examples.aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

class Purchase {
    final PurchaseId purchaseId;
    private List<Product> products = new ArrayList<>();
    private List<Product> freeProducts = new ArrayList<>();
    private List<Product> intentionallyRemovedProducts = new ArrayList<>();

    public Purchase(PurchaseId purchaseId) {
        this.purchaseId = purchaseId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(purchaseId, purchase.purchaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId);
    }

    public String print() {
        Stream<String> products = this.products.stream()
                .map(product -> product.name)
                .sorted();
        Stream<String> freeProducts = this.freeProducts.stream()
                .map(product -> "[FREE] " + product.name)
                .sorted();
        return Stream.concat(products, freeProducts)
                .collect(groupingBy(identity(), counting()))
                .toString();
    }

    void addProduct(Product product, ExtraProductPolicy productPolicy) {
        products.add(product);
        freeProducts.addAll(productPolicy.getExtraProductFor(product));
    }

    void removeProduct(Product product, ExtraProductPolicy productPolicy) {
        products.remove(product);
        freeProducts.removeAll(productPolicy.getExtraProductFor(product));
    }

    public void intentionallyRemoveFreeProduct(Product product) {
        if (freeProducts.remove(product)) {
            intentionallyRemovedProducts.add(product);
        }
    }

    public void addBackFreeProduct(Product product) {
        if (productWasIntentionallyRemoved(product)) {
            freeProducts.add(product);
            intentionallyRemovedProducts.remove(product);
        }
    }

    private boolean productWasIntentionallyRemoved(Product product) {
        return intentionallyRemovedProducts.contains(product);
    }
}
