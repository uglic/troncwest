package ru.uglic.troncwest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uglic.troncwest.model.StockCustomerReservedGood;

@Repository
public interface StockCustomerReservedGoodRepository extends CrudRepository<StockCustomerReservedGood, Long> {
}
