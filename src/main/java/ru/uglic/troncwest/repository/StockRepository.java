package ru.uglic.troncwest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uglic.troncwest.model.Stock;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
}
