package com.store_management_system.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import com.store_management_system.sms.model.*;
import com.store_management_system.sms.repository.CustomerRepository;
import com.store_management_system.sms.repository.InventoryRepository;
import com.store_management_system.sms.repository.OrderRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.store_management_system.sms.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/order/{inventoryId}")
    public String createOrder(@PathVariable Long inventoryId,@AuthenticationPrincipal UserDetails userDetails,Model model) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        try {
        Order order=new Order();
        order.setEmployeeId(currentUser.getEmployeeId());
        order.setInventoryId((long)inventoryId);
        
        order.setPrice(inventoryRepository.getPriceById(inventoryId));
        model.addAttribute("order", order);
            return "order";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            // model.addAttribute("order", order);
            model.addAttribute("currentUser", currentUser);
           return "order";
        }
    }

    @PostMapping("/order/create")
    public String postCreateOrder(Model model,@AuthenticationPrincipal UserDetails userDetails,@ModelAttribute Order order) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        
        try {
            model.addAttribute("currentUser", currentUser);
            Long quantity=order.getQuantity();
            Long customerId=order.getCustomerId();
            Long inventoryId=order.getInventoryId();
            Inventory inventory=inventoryRepository.findById(inventoryId);
            Long qmax=inventory.getQuantity();

            if(customerRepository.findById(customerId)==null || quantity==null || quantity>qmax){
                model.addAttribute("errorMessage", "Incorrect Details..");
            model.addAttribute("order", order);
            model.addAttribute("currentUser", currentUser);
           return "order";
            }
            inventory.setQuantity(qmax-quantity);
            inventoryRepository.save(inventory);
            orderRepository.save(order);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Incorrect Details.."+e.getMessage());
            model.addAttribute("order", order);
            model.addAttribute("currentUser", currentUser);
           return "order";
        }
        
        return "redirect:/customers";
    }
    
    @PostMapping("/delete/order/{id}")
    public String deleteOrder(Model model,@PathVariable Long id) {
        try {
            Order order=orderRepository.findById(id);
            Inventory inventory=inventoryRepository.findById(order.getInventoryId());
            Customer customer=customerRepository.findById(order.getCustomerId());
            Double customerAccount=customer.getAccount();
            
            
            customer.setAccount(customerAccount-order.getPrice()*order.getQuantity()+order.getPayment());
            Long q=inventory.getQuantity();
            inventory.setQuantity(q+order.getQuantity());
            customerRepository.save(customer);
            inventoryRepository.save(inventory);
            orderRepository.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unable to delete."+e.getMessage());
        
        }
        
        return "redirect:/customers";
    }
    
}
