package org.appMain.controllers;

import org.appMain.entities.dto.CustomersDTO;
import org.appMain.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public @ResponseBody CustomersDTO getAllCustomers(){
        CustomersDTO customersDTO = new CustomersDTO();
        customersDTO.setCustomers(customerService.getAllCustomers());
        return customersDTO;
    }
}
