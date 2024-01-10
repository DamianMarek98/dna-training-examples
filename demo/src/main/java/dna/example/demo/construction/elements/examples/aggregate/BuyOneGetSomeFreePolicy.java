package dna.example.demo.construction.elements.examples.aggregate;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class BuyOneGetSomeFreePolicy implements ExtraProductPolicy {
    private final Set<ExtraProduct> extraProducts = new HashSet<>();

    @Override
    public Set<Product> getExtraProductFor(Product product) {
        return extraProducts.stream()
                .filter(extraProduct -> extraProduct.isFreeFor(product))
                .map(ExtraProduct::freeProduct)
                .collect(Collectors.toSet());
    }

    public void addNewExtraProduct(ExtraProduct extraProduct) {
        extraProducts.add(extraProduct);
    }
}

record ExtraProduct(Product forProduct, Product freeProduct) {
    boolean isFreeFor(Product product) {
        return product.equals(forProduct);
    }
}
