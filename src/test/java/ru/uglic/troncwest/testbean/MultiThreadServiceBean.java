package ru.uglic.troncwest.testbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.uglic.troncwest.service.ProductRemainderService;
import ru.uglic.troncwest.testdata.CustomerData;
import ru.uglic.troncwest.testdata.ProductData;
import ru.uglic.troncwest.testdata.StockData;
import ru.uglic.troncwest.testdata.TestData;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MultiThreadServiceBean {
    private final Logger log = LoggerFactory.getLogger(MultiThreadServiceBean.class);
    @Autowired
    private ProductRemainderService service;

    @Autowired
    private TestData testData;
    private CustomerData customers;
    private ProductData products;
    private StockData stocks;

    @PostConstruct
    void postConstruct() {
        customers = testData.getCustomerData();
        products = testData.getProductData();
        stocks = testData.getStockData();
    }

    public long reserveCustomerMultiThread(int threadCount) throws InterruptedException {
        final long ADD_COUNT = 1;
        final long productId = products.get(0).getId();
        final long stockId = stocks.get(0).getId();
        final long customerId = customers.get(0).getId();
        final long availableBefore = service.getFreeBalance(productId, stockId);
        final Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(50, 500));
                } catch (InterruptedException e) {
                }
                service.reserveCustomer(productId, stockId, customerId, ADD_COUNT);
            });
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        long availableAfter = service.getFreeBalance(productId, stockId);
        log.info("availableBefore - availableAfter: {} b={} a={} ADD={}",
                availableBefore - availableAfter, availableBefore, availableAfter, ADD_COUNT * threads.length);
        return availableBefore - availableAfter;
    }
}
