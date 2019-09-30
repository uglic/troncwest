package ru.uglic.troncwest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uglic.troncwest.model.StockReservedProductRemainder;

@Repository
public interface StockReservedProductRemainderRepository extends CrudRepository<StockReservedProductRemainder, Long> {
    @Query("select sum(r.quantity) as s from StockReservedProductRemainder r" +
            " inner join r.product inner join r.stock" +
            " where r.product.id = :productId and r.stock.id = :stockId" +
            " group by r.product.id, r.stock.id")
    Long getSumByGoodAndStock(long productId, long stockId);
}
