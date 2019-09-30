package ru.uglic.troncwest.testdata;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.uglic.troncwest.model.Product;

@Component
public class ProductData extends AbstractData<Product> {
    public final static int INDEX_FREE = 3;

    public ProductData(CrudRepository<Product, Long> repository) {
        super(repository);
    }

    @Override
    public void addData() {
        add(create(null, "Product 4"));
        add(create(null, "Product 1"));
        add(create(null, "Product 2"));
        add(create(null, "Product 3"));
    }

    private Product create(Long id, String name) {
        return createNamed(new Product(), id, name);
    }
}
