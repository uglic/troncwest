package ru.uglic.troncwest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uglic.troncwest.model.StockProductRemainder;

@Repository
public interface StockProductRemainderRepository extends CrudRepository<StockProductRemainder, Long> {
    @Query("select r.quantity as q from StockProductRemainder r" +
            " inner join r.product inner join r.stock" +
            " where r.product.id = :productId and r.stock.id = :stockId")
    Long getQuantityByGoodAndStock(long productId, long stockId);
}
