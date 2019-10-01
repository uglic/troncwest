package ru.uglic.troncwest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uglic.troncwest.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
