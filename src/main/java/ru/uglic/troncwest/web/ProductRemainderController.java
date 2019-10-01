package ru.uglic.troncwest.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.uglic.troncwest.dto.CustomerReservedDto;
import ru.uglic.troncwest.dto.in.ProductReserveRequestDto;
import ru.uglic.troncwest.service.ProductRemainderService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = ProductRemainderController.BASE_PATH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductRemainderController {
    final static String BASE_PATH = "/remainders";
    private final ProductRemainderService service;

    public ProductRemainderController(ProductRemainderService service) {
        this.service = service;
    }

    @GetMapping("/free")
    public long getFreeBalance(@RequestParam("productId") int productId, @RequestParam("stockId") int stockId) {
        return service.getFreeBalance(productId, stockId);
    }

    @GetMapping("/full")
    public long getFullBalance(@RequestParam("productId") int productId, @RequestParam("stockId") int stockId) {
        return service.getFullBalance(productId, stockId);
    }

    @PutMapping(value = "/reserve/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reserveCustomer(@RequestBody @Valid ProductReserveRequestDto request) {
        service.reserveCustomer(request.getProductId(), request.getStockId(), request.getCustomerId(), request.getQuantity());
    }

    @PutMapping(value = "/reserve/free", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void freeCustomer(@RequestBody @Valid ProductReserveRequestDto request) {
        service.freeCustomer(request.getProductId(), request.getStockId(), request.getCustomerId(), request.getQuantity());
    }

    @GetMapping("/by-customer/{customerId}")
    public List<CustomerReservedDto> getFullBalance(@PathVariable("customerId") int customerId) {
        return service.getCustomerReserved(customerId);
    }
}
