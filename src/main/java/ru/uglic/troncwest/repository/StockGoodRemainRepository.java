package ru.uglic.troncwest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uglic.troncwest.model.StockGoodRemain;

@Repository
public interface StockGoodRemainRepository extends CrudRepository<StockGoodRemain, Long> {
}
