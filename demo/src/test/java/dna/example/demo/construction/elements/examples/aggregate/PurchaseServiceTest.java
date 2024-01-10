package dna.example.demo.construction.elements.examples.aggregate;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseServiceTest {
    PurchaseId PURCHASE_ID = new PurchaseId(UUID.randomUUID());
    Product CODING_SUB = new Product("CODING_SUB");
    Product DDD_BOOK = new Product("DDD_BOOK");

    PurchaseRepository purchaseRepository = new PurchaseDatabase();
    BuyOneGetSomeFreePolicy productPolicy = new BuyOneGetSomeFreePolicy();
    PurchaseService purchaseService = new PurchaseService(purchaseRepository, productPolicy);

    @Test
    void can_add_a_subscription() {
        //given
        Purchase purchase = aPurchase();

        //when
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID);

        //then
        assertEquals("{CODING_SUB=1}", purchase.print());
    }

    @Test
    void can_remove_a_subscription() {
        //given
        Purchase purchase = aPurchase();

        //when
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID);
        purchaseService.removeProduct(CODING_SUB, PURCHASE_ID);

        //then
        assertEquals("{}", purchase.print());
    }

    @Test
    void can_two_same_subscriptions() {
        //given
        Purchase purchase = aPurchase();

        //when
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID);
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID);

        //then
        assertEquals("{CODING_SUB=2}", purchase.print());
    }

    @Test
    void buy_one_subscription_and_get_free_book() {
        //given
        Purchase purchase = aPurchase();
        //and
        forCodingSubThereIsFreeDDDBook();

        //when
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID);


        //then
        assertEquals("{[FREE] DDD_BOOK=1, CODING_SUB=1}", purchase.print());
    }

    @Test
    void removing_subscription_removes_free_book() {
        //given
        Purchase purchase = aPurchase();
        //and
        forCodingSubThereIsFreeDDDBook();

        //when
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID);
        purchaseService.removeProduct(CODING_SUB, PURCHASE_ID);

        //then
        assertEquals("{}", purchase.print());
    }

    private void forCodingSubThereIsFreeDDDBook() {
        productPolicy.addNewExtraProduct(new ExtraProduct(CODING_SUB, DDD_BOOK));
    }

    @Test
    void can_remove_a_free_book() {
        //given
        Purchase purchase = aPurchase();
        //and
        forCodingSubThereIsFreeDDDBook();
        //and
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID);

        //when
        purchaseService.intentionallyRemoveFreeProduct(DDD_BOOK, PURCHASE_ID);

        //then
        assertEquals("{CODING_SUB=1}", purchase.print());
    }

    @Test
    void can_ask_for_previous_returned_free_book() {
        //given
        Purchase purchase = aPurchase();
        //and
        forCodingSubThereIsFreeDDDBook();
        //and
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID);
        //and
        purchaseService.intentionallyRemoveFreeProduct(DDD_BOOK, PURCHASE_ID);

        //when
        purchaseService.addBackFreeProduct(DDD_BOOK, PURCHASE_ID);

        //then
        assertEquals("{[FREE] DDD_BOOK=1, CODING_SUB=1}", purchase.print());
    }

    @Test
    void after_getting_two_free_books_someone_wants_3rd_book() {
        //given
        Purchase purchase = aPurchase();
        //and
        forCodingSubThereIsFreeDDDBook();

        //when
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID);
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID);
        purchaseService.addProduct(DDD_BOOK, PURCHASE_ID);

        //then
        assertEquals("{[FREE] DDD_BOOK=2, CODING_SUB=2, DDD_BOOK=1}", purchase.print());
    }

    @Test
    void someone_claims_free_book_which_wast_gifted() {
        //given
        Purchase purchase = aPurchase();
        //and
        forCodingSubThereIsFreeDDDBook();

        //when
        purchaseService.addBackFreeProduct(DDD_BOOK, PURCHASE_ID);

        //then
        assertEquals("{}", purchase.print());
    }

    @Test
    void someone_claims_free_book_after_simulating_free_return() {
        //given
        Purchase purchase = aPurchase();
        //and
        forCodingSubThereIsFreeDDDBook();

        //when
        purchaseService.intentionallyRemoveFreeProduct(DDD_BOOK, PURCHASE_ID);
        purchaseService.addBackFreeProduct(DDD_BOOK, PURCHASE_ID);

        //then
        assertEquals("{}", purchase.print());
    }

    private Purchase aPurchase() {
        var purchase = new Purchase(PURCHASE_ID);
        purchaseRepository.save(purchase);
        return purchase;
    }
}
