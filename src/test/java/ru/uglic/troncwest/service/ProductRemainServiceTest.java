package ru.uglic.troncwest.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.uglic.troncwest.AbstractSpringTest;
import ru.uglic.troncwest.dto.CustomerReservedDto;
import ru.uglic.troncwest.testbean.MultiThreadServiceBean;
import ru.uglic.troncwest.testdata.CustomerData;
import ru.uglic.troncwest.testdata.ProductData;
import ru.uglic.troncwest.testdata.StockData;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductRemainServiceTest extends AbstractSpringTest {
    private CustomerData customers;
    private ProductData products;
    private StockData stocks;

    @Autowired
    private ProductRemainderService service;

    @Autowired
    private MultiThreadServiceBean multiThreadServiceBean;

    static {
        isClearSqlAfterEachTest = true;
    }

    @PostConstruct
    void postConstruct() {
        customers = testData.getCustomerData();
        products = testData.getProductData();
        stocks = testData.getStockData();
    }

    @Test
    void getFreeBalance() {
        assertEquals(87, service.getFreeBalance(products.get(0).getId(), stocks.get(0).getId()));
        assertEquals(92, service.getFreeBalance(products.get(1).getId(), stocks.get(1).getId()));
        assertEquals(82, service.getFreeBalance(products.get(2).getId(), stocks.get(0).getId()));
        assertEquals(94, service.getFreeBalance(products.get(2).getId(), stocks.get(2).getId()));
        assertEquals(0, service.getFreeBalance(products.get(3).getId(), stocks.get(3).getId()));
    }

    @Test
    void getFullBalance() {
        assertEquals(91, service.getFullBalance(products.get(0).getId(), stocks.get(0).getId()));
        assertEquals(95, service.getFullBalance(products.get(1).getId(), stocks.get(1).getId()));
        assertEquals(93, service.getFullBalance(products.get(2).getId(), stocks.get(0).getId()));
        assertEquals(99, service.getFullBalance(products.get(2).getId(), stocks.get(2).getId()));
        assertEquals(0, service.getFullBalance(products.get(3).getId(), stocks.get(3).getId()));
    }

    @Test
    void reserveCustomer() {
        final long ADD_COUNT = 5;
        long productId = products.get(0).getId();
        long stockId = stocks.get(0).getId();
        long availableBefore = service.getFreeBalance(productId, stockId);
        service.reserveCustomer(productId, stockId, customers.get(0).getId(), ADD_COUNT);
        long availableAfter = service.getFreeBalance(productId, stockId);
        assertEquals(ADD_COUNT, availableBefore - availableAfter);
    }

    @Test
    void reserveCustomerMulti() throws InterruptedException {
        long reserved = multiThreadServiceBean.reserveCustomerMultiThread(5);
        assertTrue(reserved > 1);
    }

    @Test
    void freeCustomer() {
        final long FREE_COUNT = 1;
        long productId = products.get(0).getId();
        long stockId = stocks.get(0).getId();
        long availableBefore = service.getFreeBalance(productId, stockId);
        service.freeCustomer(productId, stockId, customers.get(0).getId(), FREE_COUNT);
        long availableAfter = service.getFreeBalance(productId, stockId);
        assertEquals(FREE_COUNT, availableAfter - availableBefore);
    }

    @Test
    void getCustomerReserved() {
        List<CustomerReservedDto> expected = new ArrayList<>();
        expected.add(CustomerReservedDto.asDto(products.get(0), stocks.get(0), 1));
        expected.add(CustomerReservedDto.asDto(products.get(1), stocks.get(1), 2));
        expected.add(CustomerReservedDto.asDto(products.get(2), stocks.get(2), 3));
        List<CustomerReservedDto> actual = service.getCustomerReserved(customers.get(0).getId());
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}