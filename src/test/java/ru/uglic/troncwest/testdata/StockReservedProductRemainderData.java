package ru.uglic.troncwest.testdata;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.uglic.troncwest.model.*;

@Component
public class StockReservedProductRemainderData extends AbstractData<StockReservedProductRemainder> {
    public final static int INDEX_FREE = 3;
    private final StockData stockData;
    private final ProductData productData;
    private final CustomerData customerData;

    public StockReservedProductRemainderData(CrudRepository<StockReservedProductRemainder, Long> repository,
                                             StockData stockData, ProductData productData, CustomerData customerData) {
        super(repository);
        this.stockData = stockData;
        this.productData = productData;
        this.customerData = customerData;
    }

    @Override
    public void addData() {
        add(create(null, stockData.get(0), productData.get(0), customerData.get(0), 1));
        add(create(null, stockData.get(0), productData.get(0), customerData.get(1), 1));
        add(create(null, stockData.get(0), productData.get(0), customerData.get(2), 2));
        add(create(null, stockData.get(0), productData.get(1), customerData.get(1), 3));
        add(create(null, stockData.get(0), productData.get(1), customerData.get(2), 4));
        add(create(null, stockData.get(0), productData.get(2), customerData.get(1), 5));
        add(create(null, stockData.get(0), productData.get(2), customerData.get(2), 6));

        add(create(null, stockData.get(1), productData.get(1), customerData.get(0), 2));
        add(create(null, stockData.get(1), productData.get(1), customerData.get(1), 1));
        add(create(null, stockData.get(1), productData.get(2), customerData.get(1), 1));

        add(create(null, stockData.get(2), productData.get(2), customerData.get(0), 3));
        add(create(null, stockData.get(2), productData.get(2), customerData.get(2), 2));
    }

    private StockReservedProductRemainder create(Long id, Stock stock, Product product,
                                                 Customer customer, long quantity) {
        StockReservedProductRemainder entity = new StockReservedProductRemainder();
        entity.setId(id);
        entity.setStock(stock);
        entity.setProduct(product);
        entity.setCustomer(customer);
        entity.setQuantity(quantity);
        return entity;
    }
}
