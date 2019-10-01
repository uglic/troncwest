package ru.uglic.troncwest.testdata;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.uglic.troncwest.model.Customer;

@Component
public class CustomerData extends AbstractData<Customer> {
    public final static int INDEX_FREE = 3;

    public CustomerData(CrudRepository<Customer, Long> repository) {
        super(repository);
    }

    @Override
    public void addData() {
        add(create(null, "Customer 2"));
        add(create(null, "Customer 1"));
        add(create(null, "Customer 4"));
        add(create(null, "Customer 3"));
    }

    private Customer create(Long id, String name) {
        return createNamed(new Customer(), id, name);
    }
}
