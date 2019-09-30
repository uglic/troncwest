package ru.uglic.troncwest.testdata;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.uglic.troncwest.model.Product;
import ru.uglic.troncwest.model.Stock;
import ru.uglic.troncwest.model.StockProductRemainder;

@Component
public class StockProductRemainderData extends AbstractData<StockProductRemainder> {
    public final static int INDEX_FREE = 3;
    private final StockData stockData;
    private final ProductData productData;

    public StockProductRemainderData(CrudRepository<StockProductRemainder, Long> repository,
                                     StockData stockData, ProductData productData) {
        super(repository);
        this.stockData = stockData;
        this.productData = productData;
    }

    @Override
    public void addData() {
        add(create(null, stockData.get(0), productData.get(0), 91));
        add(create(null, stockData.get(0), productData.get(1), 92));
        add(create(null, stockData.get(0), productData.get(2), 93));
        add(create(null, stockData.get(1), productData.get(0), 94));
        add(create(null, stockData.get(1), productData.get(1), 95));
        add(create(null, stockData.get(1), productData.get(2), 96));
        add(create(null, stockData.get(2), productData.get(0), 97));
        add(create(null, stockData.get(2), productData.get(1), 98));
        add(create(null, stockData.get(2), productData.get(2), 99));
    }

    private StockProductRemainder create(Long id, Stock stock, Product product, long quantity) {
        StockProductRemainder entity = new StockProductRemainder();
        entity.setId(id);
        entity.setStock(stock);
        entity.setProduct(product);
        entity.setQuantity(quantity);
        return entity;
    }
}
