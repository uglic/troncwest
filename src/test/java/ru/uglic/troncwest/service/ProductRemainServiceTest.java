package ru.uglic.troncwest.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.uglic.troncwest.AbstractSpringTest;
import ru.uglic.troncwest.testdata.ProductData;
import ru.uglic.troncwest.testdata.StockData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductRemainServiceTest extends AbstractSpringTest {
    @Autowired
    private ProductRemainderService productRemainderService;

    static {
        isClearSqlAfterEachTest = true;
    }

    @Test
    void getFreeBalance() {
        ProductData products = testData.getProductData();
        StockData stocks = testData.getStockData();
        assertEquals(87, productRemainderService.getFreeBalance(products.get(0).getId(), stocks.get(0).getId()));
        assertEquals(92, productRemainderService.getFreeBalance(products.get(1).getId(), stocks.get(1).getId()));
        assertEquals(82, productRemainderService.getFreeBalance(products.get(2).getId(), stocks.get(0).getId()));
        assertEquals(94, productRemainderService.getFreeBalance(products.get(2).getId(), stocks.get(2).getId()));
    }
}