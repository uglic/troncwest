package ru.uglic.troncwest.testdata;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.uglic.troncwest.AbstractSpringTest;

import javax.annotation.PostConstruct;

@Component
public class TestData extends AbstractSpringTest {
    @Autowired
    @Getter
    private CustomerData customerData;
    @Autowired
    @Getter
    private ProductData productData;
    @Autowired
    @Getter
    private StockData stockData;
    @Autowired
    @Getter
    private StockProductRemainderData stockProductRemainderData;
    @Autowired
    @Getter
    private StockReservedProductRemainderData stockReservedProductRemainderData;

    @PostConstruct
    public void restoreAll() {
        deleteAll();
        customerData.addData();
        productData.addData();
        stockData.addData();
        stockProductRemainderData.addData();
        stockReservedProductRemainderData.addData();
    }

    // sequence counter will be continuing
    private void deleteAll() {
        stockReservedProductRemainderData.deleteAll();
        stockProductRemainderData.deleteAll();
        stockData.deleteAll();
        productData.deleteAll();
        customerData.deleteAll();
    }
}
