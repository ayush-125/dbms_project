package com.store_management_system.sms.controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.store_management_system.sms.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.store_management_system.sms.model.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;


    @GetMapping("/customers")
    public String getCustomers(Model model) {
        try {
            List<Customer> customers=customerRepository.findAll();
            model.addAttribute("customers",customers);
            return "customers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "customers";
        }
    }
    

    @GetMapping("/create/customer")
    public String getCreateCustomer(Model model) {
        try {
            Customer customer=new Customer();
            
            if(customer.getEmails()==null){
                customer.setEmails(new ArrayList<>());
            }
            for (int i = customer.getEmails().size(); i < 5; i++) {
                customer.getEmails().add(new CustomerMail());
            }
            model.addAttribute("customer",customer);
            return "createCustomer";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "createCustomer";
        }
        
    }
    @PostMapping("/create/customer")
    public String postCreateCustomer(@ModelAttribute Customer customer,Model model) {
        try {
            customer.getEmails().removeIf(email -> email.getCustomerEmail() == null || email.getCustomerEmail().isEmpty());
        
            customerRepository.save(customer);
            return "redirect:/customers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "createCustomer";
        }
    }

    @GetMapping("/view/customer/{id}")
    public String getViewCustomer(@PathVariable Long id,Model model ) {
        return new String();
    }
    @PostMapping("/update/customer")
    public String postUpdateCustomer(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/search/customer/{firstName}/{lastName}")
    public String getMethodName(@PathVariable String firstName) {
        return new String();
    }
    

    
}
