package com.sam.shop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    @Test
    void totals_and_find_work() {
        Inventory inv = new Inventory();
        inv.add(new Product(1, "Apple", 10, 1.0));
        inv.add(new Product(2, "Orange", 2, 2.0));
        assertEquals(14.0, inv.totalValue(), 1e-9);
        assertNotNull(inv.findById(2));
        assertNull(inv.findById(9));
    }
}
