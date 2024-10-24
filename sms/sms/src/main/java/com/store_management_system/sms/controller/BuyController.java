package com.store_management_system.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import com.store_management_system.sms.model.*;
import com.store_management_system.sms.repository.SupplierRepository;
import com.store_management_system.sms.repository.InventoryRepository;
import com.store_management_system.sms.repository.BuyRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.store_management_system.sms.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class BuyController {
    @Autowired
    private UserService userService;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private BuyRepository buyRepository;

    @GetMapping("/buy/{inventoryId}")
    public String createBuy(@PathVariable Long inventoryId,@AuthenticationPrincipal UserDetails userDetails,Model model) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        try {
        Buy buy=new Buy();
        // buy.setEmployeeId(currentUser.getEmployeeId());
        buy.setInventoryId((long)inventoryId);
        
        buy.setPrice(inventoryRepository.getPriceById(inventoryId));
        List<Supplier> suppliers = supplierRepository.findAll();
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("buy", buy);
            return "buy";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            // model.addAttribute("buy", buy);
            model.addAttribute("currentUser", currentUser);
           return "buy";
        }
    }

    @PostMapping("/buy/create")
    public String postCreateBuy(Model model,@AuthenticationPrincipal UserDetails userDetails,@ModelAttribute Buy buy) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        
        try {
            model.addAttribute("currentUser", currentUser);
            Long quantity=buy.getQuantity();
            Long supplierId=buy.getSupplierId();
            Long inventoryId=buy.getInventoryId();
            Inventory inventory=inventoryRepository.findById(inventoryId);
            Long qmax=inventory.getQuantity();

            if(supplierRepository.findById(supplierId)==null ){
                model.addAttribute("errorMessage", "Incorrect Details..");
            model.addAttribute("buy", buy);
            model.addAttribute("currentUser", currentUser);
           return "buy";
            }
            inventory.setQuantity(qmax+quantity);
            inventoryRepository.save(inventory);
            buyRepository.save(buy);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Incorrect Details.."+e.getMessage());
            model.addAttribute("buy", buy);
            model.addAttribute("currentUser", currentUser);
           return "buy";
        }
        
        return "redirect:/suppliers";
    }
    
    @PostMapping("/delete/buy/{id}")
    public String deleteBuy(Model model,@PathVariable Long id) {
        try {
            Buy buy=buyRepository.findById(id);
            Inventory inventory=inventoryRepository.findById(buy.getInventoryId());
            Supplier supplier=supplierRepository.findById(buy.getSupplierId());
            Double supplierAccount=supplier.getAccount();
            
            
            supplier.setAccount(supplierAccount-buy.getPrice()*buy.getQuantity()+buy.getPayment());
            Long q=inventory.getQuantity();
            inventory.setQuantity(q-buy.getQuantity());
            supplierRepository.save(supplier);
            inventoryRepository.save(inventory);
            buyRepository.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unable to delete."+e.getMessage());
        
        }
        
        return "redirect:/suppliers";
    }
    
}
