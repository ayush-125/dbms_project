package com.store_management_system.sms.controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.store_management_system.sms.repository.CustomerRepository;
import com.store_management_system.sms.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.store_management_system.sms.model.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserService userService;

    @GetMapping("/customers")
    public String getCustomers(Model model) {
        try {
            List<Customer> customers=customerRepository.findAll();
            model.addAttribute("customers",customers);
            model.addAttribute("count", 5);
            
            return "customers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error fetching all customers..."+e.getMessage());
            model.addAttribute("count", 5);
            
            return "customers";
        }
    }
    

    @GetMapping("/create/customer")
    public String getCreateCustomer(Model model) {
        try {
            Customer customer=new Customer();
            customer.setAccount((double)0);
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
            if(customer.getEmails()!=null){
                customer.getEmails().removeIf(email -> email.getCustomerEmail() == null || email.getCustomerEmail().isEmpty());
            }
            customerRepository.save(customer);
            return "redirect:/customers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "createCustomer";
        }
    }

    @GetMapping("/view/customer/{id}")
    public String getViewCustomer(@PathVariable Long id,Model model ) {
        try {
            Customer customer = customerRepository.findById(id);
                if (customer == null) {
                    model.addAttribute("errorMessage", "This customer does not exist...");
                    model.addAttribute("customer", customer);
                    return "viewcustomer";
                }
                if(customer.getEmails()==null){
                    customer.setEmails(new ArrayList<>());
                }
                for (int i = customer.getEmails().size(); i < 5; i++) {
                    customer.getEmails().add(new CustomerMail());
                }
            model.addAttribute("customer", customer);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to fetch customer details...");
            
            return "viewcustomer";
        }
        return "viewcustomer";
    }

    @PostMapping("/update/customer/{id}")
    public String postUpdateCustomer(Model model,@ModelAttribute Customer customer,@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            model.addAttribute("currentUser", currentUser);
            if(currentUser.getRoleId().equals(  1L) || currentUser.getRoleId().equals(2L)){
                if(customer.getEmails()!=null){
                    customer.getEmails().removeIf(email -> email.getCustomerEmail() == null || email.getCustomerEmail().isEmpty());
                }
                
                customerRepository.save(customer);
            }else{
                return "error/403";
            }
            
            return "redirect:/view/customer/{id}";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to update customer details.."+e.getMessage());
            model.addAttribute("customer", customer);
            return "viewcustomer";
        }
        
    }

    @PostMapping("/delete/customer/{id}")
    public String deleteCustomer(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails,Model model) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
    try {
        if (!(currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("EMPLOYEE")))  ) {
            customerRepository.deleteById(id);
            return "redirect:/customers";
        } else { 
            return "error/403";
        }
        
        }catch(Exception e){
            model.addAttribute("errorMessage","Failed to delete the customer."+e.getMessage());
            return "redirect:/customers";
        }
    
    }
    // @GetMapping("/search/customer/{phoneNo}")
    // public String getMethodName(@PathVariable String firstName) {
    //     return new String();
    // }
    

    
}
