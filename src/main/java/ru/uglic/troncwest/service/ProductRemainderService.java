package ru.uglic.troncwest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.uglic.troncwest.dto.CustomerReservedDto;
import ru.uglic.troncwest.exception.EntityNotFoundByIdException;
import ru.uglic.troncwest.exception.IllegalArgumentByIdException;
import ru.uglic.troncwest.model.*;
import ru.uglic.troncwest.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(timeout = ProductRemainderService.TRANS_TIMEOUT)
public class ProductRemainderService {
    static final int TRANS_TIMEOUT = 2;
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
            return 0;
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
    public void reserveCustomer(long productId, long stockId, long customerId, long quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentByIdException("add-low", productId, stockId, customerId, quantity);
        }
        long balanceBefore = getFreeBalance(productId, stockId);
        StockReservedProductRemainder reserve = getReserveEntity(productId, stockId, customerId);
        long currQuantity = reserve.getQuantity();
        if (currQuantity < 0) {
            throw new IllegalArgumentByIdException("exist-negative", productId, stockId, customerId, currQuantity);
        }
        if (balanceBefore - quantity < 0) {
            throw new IllegalArgumentByIdException("add-too-many", productId, stockId, customerId, quantity, balanceBefore);
        } else {
            reserve.setQuantity(currQuantity + quantity);
            stockReservedProductRemainderRepository.save(reserve);
        }
    }

    /**
     * Free from reserve a quantity of particular product at particular stock for particular customer
     *
     * @param productId  Product id
     * @param stockId    Stock id
     * @param customerId Customer id
     * @param quantity   Quantity of product to free from reserve
     * @throws java.lang.IllegalArgumentException          If quantity is non-positive or more then existing reserve
     * @throws javax.persistence.EntityNotFoundException   If product, stock or customer does not found by it's id
     * @throws org.springframework.dao.DataAccessException If was error during save() or delete() of reserve
     */
    public void freeCustomer(long productId, long stockId, long customerId, long quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentByIdException("free-low", productId, stockId, customerId, quantity);
        }
        StockReservedProductRemainder reserve = getReserveEntity(productId, stockId, customerId);
        long currQuantity = reserve.getQuantity();
        if (currQuantity < quantity) {
            if (currQuantity < 0) {
                throw new IllegalArgumentByIdException("exist-negative", productId, stockId, customerId, currQuantity);
            } else {
                throw new IllegalArgumentByIdException("free-too-many", productId, stockId, customerId, quantity, currQuantity);
            }
        } else if (currQuantity == quantity) {
            stockReservedProductRemainderRepository.deleteById(reserve.getId());
        } else {
            reserve.setQuantity(currQuantity - quantity);
            stockReservedProductRemainderRepository.save(reserve);
        }
    }

    /**
     * List of reserved products per stock with reserved quantity
     *
     * @param customerId Customer id
     * @return {@link List}<{@link CustomerReservedDto}> List of DTO entities
     */
    public List<CustomerReservedDto> getCustomerReserved(long customerId) {
        List<StockReservedProductRemainder> reservedList = stockReservedProductRemainderRepository
                .getReservedByCustomerIdOrderByProductIdAscStockIdAsc(customerId);
        List<CustomerReservedDto> resultList = new ArrayList<>();
        reservedList.forEach(e -> resultList.add(
                CustomerReservedDto.asDto(e.getProduct(), e.getStock(), e.getQuantity())));
        return resultList;
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
                .orElseThrow(() -> new EntityNotFoundByIdException("by-id", id, clazz));
    }

    private StockReservedProductRemainder getReserveEntity(long productId, long stockId, long customerId) {
        return stockReservedProductRemainderRepository
                .getByProductIdAndStockIdAndCustomerId(productId, stockId, customerId)
                .orElseGet(() -> {
                    StockReservedProductRemainder entity = new StockReservedProductRemainder();
                    entity.setProduct(getFromRepository(productRepository, productId, Product.class));
                    entity.setStock(getFromRepository(stockRepository, stockId, Stock.class));
                    entity.setCustomer(getFromRepository(customerRepository, customerId, Customer.class));
                    return entity;
                });
    }
}
