package com.turkcell.turkcellcrm.customerService.api.controller;

import com.turkcell.turkcellcrm.customerService.business.abstracts.AddressService;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.address.CreateAddressRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.address.UpdateAddressRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.CreatedAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.GetAllAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.GetByIdAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.UpdatedAddressResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerservice/api/v1/customers/address")
@AllArgsConstructor
public class AddressController {
    private AddressService addressService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedAddressResponse add(@Valid @RequestBody CreateAddressRequest createAddressRequest){
        return this.addressService.add(createAddressRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllAddressResponse> getAll(){
        return this.addressService.getAll();
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetByIdAddressResponse getById(@PathVariable int id){
        return this.addressService.getById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UpdatedAddressResponse update(@Valid @RequestBody UpdateAddressRequest updateAddressRequest){
        return this.addressService.update(updateAddressRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        this.addressService.delete(id);
    }
}
