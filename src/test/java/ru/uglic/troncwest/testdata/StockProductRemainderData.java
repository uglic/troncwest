package ru.uglic.troncwest.testdata;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.uglic.troncwest.model.Product;
import ru.uglic.troncwest.model.Stock;
import ru.uglic.troncwest.model.StockProductRemainder;

@Component
public class StockProductRemainderData extends AbstractData<StockProductRemainder> {
    public final static int INDEX_FREE = 0;
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
        add(create(null, productData.get(0), stockData.get(0), 91));
        add(create(null, productData.get(1), stockData.get(0), 92));
        add(create(null, productData.get(2), stockData.get(0), 93));
        add(create(null, productData.get(0), stockData.get(1), 94));
        add(create(null, productData.get(1), stockData.get(1), 95));
        add(create(null, productData.get(2), stockData.get(1), 96));
        add(create(null, productData.get(0), stockData.get(2), 97));
        add(create(null, productData.get(1), stockData.get(2), 98));
        add(create(null, productData.get(2), stockData.get(2), 99));
    }

    private StockProductRemainder create(Long id, Product product, Stock stock, long quantity) {
        StockProductRemainder entity = new StockProductRemainder();
        entity.setId(id);
        entity.setStock(stock);
        entity.setProduct(product);
        entity.setQuantity(quantity);
        return entity;
    }
}
