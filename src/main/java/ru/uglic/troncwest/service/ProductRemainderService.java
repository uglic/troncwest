package ru.uglic.troncwest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.uglic.troncwest.dto.CustomerReservedDto;
import ru.uglic.troncwest.model.StockProductRemainder;
import ru.uglic.troncwest.repository.*;

@Service
public class ProductRemainderService {
    private static final int TRANS_TIMEOUT = 2;
    private final Logger log = LoggerFactory.getLogger(StockProductRemainder.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockReservedProductRemainderRepository stockReservedProductRemainderRepository;
    @Autowired
    private StockProductRemainderRepository stockProductRemainderRepository;

    /**
     * Gets remainder of particular product on particular stock taking into account the reserve.
     * Returns zero in case of any error.
     *
     * @param productId Product id
     * @param stockId   Stock id
     * @return Total remainder of good subtract sum of reserves for it
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = TRANS_TIMEOUT)
    public long getFreeBalance(long productId, long stockId) {
        Long fullBalance = stockProductRemainderRepository.getQuantityByGoodAndStock(productId, stockId);
        Long reserved = stockReservedProductRemainderRepository.getSumByGoodAndStock(productId, stockId);
        if (fullBalance != null && fullBalance < 0) {
            log.error("error.goods.balance.negative g={} s={} q={}", productId, stockId, fullBalance);
            return 0; // or throw new IllegalStateException();
        }
        if (reserved != null && reserved < 0) {
            log.error("error.goods.reserved.negative g={} s={} r={}", productId, stockId, reserved);
            return 0; //throw new IllegalStateException();
        }
        long fullBalanceValue = fullBalance != null ? fullBalance : 0;
        long reservedValue = reserved != null ? reserved : 0;
        if (fullBalanceValue >= reservedValue) {
            return fullBalanceValue - reservedValue;
        } else {
            log.error("error.goods.remainder.negative g={} s={} f={} r={}", productId, stockId, fullBalance, reserved);
            return 0; //throw new IllegalStateException();
        }
    }

    public long getFullBalance(long productId, long stockId) {
        return 0;
    }

    public void reserveCustomer(long productId, long stockId, long customerId, long quantity) {
    }

    public void freeCustomer(long productId, long stockId, long customerId, long quantity) {
    }

    public CustomerReservedDto getCustomerReserved(long customerId) {
        return null;
    }
}
