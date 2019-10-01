package ru.uglic.troncwest.testdata;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.uglic.troncwest.model.Stock;

@Component
public class StockData extends AbstractData<Stock> {
    public final static int INDEX_FREE = 3;

    public StockData(CrudRepository<Stock, Long> repository) {
        super(repository);
    }

    @Override
    public void addData() {
        add(create(null, "Stock 1"));
        add(create(null, "Stock 3"));
        add(create(null, "Stock 2"));
        add(create(null, "Stock 4"));
    }

    private Stock create(Long id, String name) {
        return createNamed(new Stock(), id, name);
    }
}
