package com.turkcell.turkcellcrm.customerService.api.controller;

import com.turkcell.turkcellcrm.customerService.business.abstracts.IndividualCustomerService;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.customer.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.customer.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.CreatedIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.GetAllIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.GetByIdIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.UpdatedIndividualCustomerResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerservice/api/v1/customers/individualcustomers")
@AllArgsConstructor
public class IndividualCustomerController {

    private IndividualCustomerService individualCustomerService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedIndividualCustomerResponse add(@Valid @RequestBody CreateIndividualCustomerRequest createIndividualCustomerRequest){
        return this.individualCustomerService.add(createIndividualCustomerRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllIndividualCustomerResponse> getAll(){
        return this.individualCustomerService.getAll();
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetByIdIndividualCustomerResponse getById(@PathVariable int id){
        return this.individualCustomerService.getById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UpdatedIndividualCustomerResponse update(@Valid @RequestBody UpdateIndividualCustomerRequest updateIndividualCustomerRequest){
        return this.individualCustomerService.update(updateIndividualCustomerRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable int id){

        this.individualCustomerService.delete(id);
        return "Silme Başarılı";
    }

    @GetMapping("/getAddressByCustomerId/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public int getAddressIdByCustomerId(@PathVariable int customerId){
        return this.individualCustomerService.getAddresIdByCustomerId(customerId);
    }
}
