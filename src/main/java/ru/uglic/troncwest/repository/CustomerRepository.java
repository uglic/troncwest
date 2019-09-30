package ru.uglic.troncwest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uglic.troncwest.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
