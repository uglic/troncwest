package ru.uglic.troncwest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.uglic.troncwest.dto.CustomerReservedDto;
import ru.uglic.troncwest.exception.EntityNotFoundByIdException;
import ru.uglic.troncwest.exception.IllegalArgumentByIdException;
import ru.uglic.troncwest.model.*;
import ru.uglic.troncwest.repository.*;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductRemainderService {
    private static final int TRANS_TIMEOUT = 2;
    private final Logger log = LoggerFactory.getLogger(StockProductRemainder.class);

    private final StockReservedProductRemainderRepository stockReservedProductRemainderRepository;
    private final StockProductRemainderRepository stockProductRemainderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public ProductRemainderService(StockReservedProductRemainderRepository stockReservedProductRemainderRepository,
                                   StockProductRemainderRepository stockProductRemainderRepository,
                                   CustomerRepository customerRepository, ProductRepository productRepository,
                                   StockRepository stockRepository) {
        this.stockReservedProductRemainderRepository = stockReservedProductRemainderRepository;
        this.stockProductRemainderRepository = stockProductRemainderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    /**
     * Gets remainder of particular product on particular stock taking into account the reserve.
     * Returns zero in case of any error.
     *
     * @param productId Product id
     * @param stockId   Stock id
     * @return Total remainder of product subtract sum of reserves for it
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = TRANS_TIMEOUT)
    public long getFreeBalance(long productId, long stockId) {
        Long fullBalance = stockProductRemainderRepository.getQuantityByGoodAndStock(productId, stockId);
        Long reserved = stockReservedProductRemainderRepository.getSumByGoodAndStock(productId, stockId);
        if (isNotValidBalance(fullBalance, "error.products.balance.negative", productId, stockId)
                || isNotValidBalance(reserved, "error.products.reserved.negative", productId, stockId)) {
            return 0;
        }
        long fullBalanceValue = fullBalance != null ? fullBalance : 0;
        long reservedValue = reserved != null ? reserved : 0;
        if (fullBalanceValue >= reservedValue) {
            return fullBalanceValue - reservedValue;
        } else {
            log.error("error.products.remainder.negative g={} s={} f={} r={}", productId, stockId, fullBalance, reserved);
            return 0; //throw new IllegalStateException();
        }
    }

    /**
     * Gets remainder of particular product on particular stock without taking into account the reserve.
     * Returns zero in case of any error.
     *
     * @param productId Product id
     * @param stockId   Stock id
     * @return Total remainder of product ignoring the reserve
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = TRANS_TIMEOUT)
    public long getFullBalance(long productId, long stockId) {
        Long fullBalance = stockProductRemainderRepository.getQuantityByGoodAndStock(productId, stockId);
        if (isNotValidBalance(fullBalance, "error.products.balance.negative", productId, stockId)) {
            return 0;
        }
        return fullBalance != null ? fullBalance : 0;
    }

    /**
     * Reserve quantity of particular product at particular stock for particular customer.
     *
     * @param productId  Product id to reserve of
     * @param stockId    Stock id to reserve at
     * @param customerId Customer id to reserve for
     * @param quantity   Quantity of product to reserve
     * @throws java.lang.IllegalArgumentException          If quantity is negative or existing reserve is negative
     * @throws javax.persistence.EntityNotFoundException   If product, stock or customer does not found by it's id
     * @throws org.springframework.dao.DataAccessException If was error during save() of reserve
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = TRANS_TIMEOUT)
    public void reserveCustomer(long productId, long stockId, long customerId, long quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentByIdException(productId, stockId, customerId, quantity);
        }
        long balanceBefore = getFreeBalance(productId, stockId);
        StockReservedProductRemainder reserve = stockReservedProductRemainderRepository
                .getByProductIdAndStockIdAndCustomerId(productId, stockId, customerId)
                .orElseGet(() -> {
                    StockReservedProductRemainder entity = new StockReservedProductRemainder();
                    entity.setProduct(getFromRepository(productRepository, productId, Product.class));
                    entity.setStock(getFromRepository(stockRepository, stockId, Stock.class));
                    entity.setCustomer(getFromRepository(customerRepository, customerId, Customer.class));
//                    entity.setQuantity(0);
//                    entity.setId(null);
                    return entity;
                });
        long newQuantity = reserve.getQuantity();
        if (newQuantity < 0) {
            throw new IllegalArgumentByIdException(productId, stockId, customerId, newQuantity);
        }
        newQuantity += quantity;
        if (newQuantity > balanceBefore) {
            throw new IllegalArgumentByIdException(productId, stockId, customerId, quantity);
        } else {
            reserve.setQuantity(newQuantity);
            stockReservedProductRemainderRepository.save(reserve);
        }
    }

    public void freeCustomer(long productId, long stockId, long customerId, long quantity) {
    }

    public CustomerReservedDto getCustomerReserved(long customerId) {
        return null;
    }

    /* ********************************* */

    private boolean isNotValidBalance(Long balance, String message, long productId, long stockId) {
        if (balance != null && balance < 0) {
            log.error("{} p={} s={} balance={}", message, productId, stockId, balance);
            return true;
        }
        return false;
    }

    private <T> T getFromRepository(CrudRepository<T, Long> repository, long id, Class<T> clazz) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(id, clazz));
    }
}
