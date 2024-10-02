package com.store_management_system.sms.controller;

import org.springframework.stereotype.Controller;
import com.store_management_system.sms.model.*;
import com.store_management_system.sms.repository.*;
import com.store_management_system.sms.service.StoreService;
import com.store_management_system.sms.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class InventoryController {
    @Autowired
    private UserService userService;
    @Autowired
    private InventoryRepository inventoryRepository;

    

    @Autowired
    private StoreService storeService;

    // Display the inventory management page
    @GetMapping("/inventory")
    public String showInventoryManagement(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            if(currentUser.getRoleId().equals(1L)){
                List<Store> stores = storeService.findAllStores();
                model.addAttribute("stores", stores);
                model.addAttribute("currentUser", currentUser);
            }else{
                Long storeId=userService.getStoreIdById(currentUser.getId());
                List<Store> stores = storeService.findById(storeId);
                model.addAttribute("stores", stores);
                model.addAttribute("currentUser", currentUser);
                return "storeinventorys";
            }
        
                 
        // model.addAttribute("inventory", inventory);
        return "inventorys"; 
        } catch (Exception e) {
           model.addAttribute("errorMessage", e.getMessage());
        //    model.addAttribute("inventory", new Inventory());
        return "inventorys"; 
        }
        
    }

    // Create a new inventory
    @PostMapping("/create/inventory")
    public String createInventory(@RequestParam("quantity") Long quantity,@RequestParam("storeId") Long storeId,@RequestParam("productId") Long productId ,Model model,@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            if(currentUser.getRoleId().equals(3L)){
                return "error/403";
            }
            Inventory inventory=new Inventory();  
            inventory.setQuantity(quantity);
            inventory.setProductId(productId);
            inventory.setStoreId(storeId);
            inventoryRepository.save(inventory);
            return "redirect:/inventory";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "inventorys";
        }
         
    }

    // Update an existing inventory
    @PostMapping("/update/inventory/{id}")
    public String updateInventory(@PathVariable("id") Integer id, @RequestParam("quantity") Long quantity,Model model, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            if(currentUser.getRoleId().equals(3L)){
                return "error/403";
            }
            Inventory inventory=new Inventory();
            inventory=inventoryRepository.findById((long)id);
            inventory.setQuantity(quantity);
            // inventory.setId((long)id); // Set the ID to ensure it updates the correct record
        inventoryRepository.save(inventory);
        return "redirect:/inventory"; 
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "inventorys";
        }
        
    }

    // Delete an inventory
    @PostMapping("/delete/inventory/{id}")
    public String deleteInventory(@PathVariable("id") Integer id,Model model, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            if(currentUser.getRoleId().equals(3L)){
                return "error/403";
            }
            inventoryRepository.deleteById((long)id);
        return "redirect:/inventory"; 
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "inventorys";
        }
        
        
        
    }
}
