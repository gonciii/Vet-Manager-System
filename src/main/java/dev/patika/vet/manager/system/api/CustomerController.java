package dev.patika.vet.manager.system.api;


import dev.patika.vet.manager.system.business.abstracts.ICustomerService;
import dev.patika.vet.manager.system.core.config.modelmapper.IModelMapperService;
import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.result.Result;
import dev.patika.vet.manager.system.core.result.ResultData;
import dev.patika.vet.manager.system.core.utilies.ResultHelper;
import dev.patika.vet.manager.system.dto.request.animal.AnimalSaveRequest;
import dev.patika.vet.manager.system.dto.request.animal.AnimalUpdateRequest;
import dev.patika.vet.manager.system.dto.request.customer.CustomerSaveRequest;
import dev.patika.vet.manager.system.dto.request.customer.CustomerUpdateRequest;
import dev.patika.vet.manager.system.dto.response.CursorResponse;
import dev.patika.vet.manager.system.dto.response.animal.AnimalResponse;
import dev.patika.vet.manager.system.dto.response.customer.CustomerResponse;
import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers")

public class CustomerController {
    private final ICustomerService customerService;
    private final IModelMapperService modelMapper;


    public CustomerController(ICustomerService customerService, IModelMapperService modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    // kayıt işlemi --> SAVE
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest) {


        // REQUEST--> customer

        Customer saveCustomer = this.modelMapper.forRequest().map(customerSaveRequest,Customer.class);
        this.customerService.save(saveCustomer);

        // customer --> RESPONSE
        return ResultHelper.created(this.modelMapper.forResponse().map(saveCustomer,CustomerResponse.class));
    }

    // ID'ye göre customer getirme
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get(@PathVariable("id") long id) {
        Customer customer = this.customerService.get(id);
        CustomerResponse customerResponse = this.modelMapper.forResponse().map(customer,CustomerResponse.class);
        return ResultHelper.success(customerResponse);
    }

    // UPDATE ----> animal güncelleme
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        Customer updateCustomer = this.modelMapper.forRequest().map(customerUpdateRequest,Customer.class);
        this.customerService.update(updateCustomer);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateCustomer,CustomerResponse.class));
    }

    // DELETE----> animal silme
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.customerService.delete(id);
        return ResultHelper.ok();
    }

    // cursor ---> customer sayfalama
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CustomerResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize
    ) {
        Page<Customer> customerPage= this.customerService.cursor(page, pageSize);
        Page<CustomerResponse> customerResponsePage = customerPage
                .map(animal -> this.modelMapper.forResponse().map(animal, CustomerResponse.class));

        // CURSOR HATASI ALIRSAN BURAYA BAK !!!
        CursorResponse<CustomerResponse> cursor = new CursorResponse<>();
        cursor.setItems(customerResponsePage.getContent());
        cursor.setPageNumber(customerResponsePage.getNumber());
        cursor.setPageSize(customerResponsePage.getSize());
        cursor.setTotalElement(customerResponsePage.getTotalElements());

        return ResultHelper.success(cursor);
    }

}
