package ru.uglic.troncwest.service;

import org.springframework.stereotype.Service;
import ru.uglic.troncwest.dto.CustomerReservedDto;

@Service
public class GoodRemainService {

    public long getFreeBalance(long good, long stock) {
        return 0;
    }

    public long getFullBalance(long good, long stock) {
        return 0;
    }

    public void reserveCustomer(long good, long stock, long customer, long quantity) {
    }

    public void freeCustomer(long good, long stock, long customer, long quantity) {
    }

    public CustomerReservedDto getCustomerReserved(long customer) {
        return null;
    }
}
