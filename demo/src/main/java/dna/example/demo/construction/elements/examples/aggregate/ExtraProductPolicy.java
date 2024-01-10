package dna.example.demo.construction.elements.examples.aggregate;

import java.util.Set;

public interface ExtraProductPolicy {
    Set<Product> getExtraProductFor(Product product);
}
