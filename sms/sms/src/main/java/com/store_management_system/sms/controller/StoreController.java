package com.store_management_system.sms.controller;

import com.store_management_system.sms.exception.CustomServiceException;
import com.store_management_system.sms.model.*;
import com.store_management_system.sms.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.sql.SQLException;
import java.util.List;

@Controller
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;
   
    @GetMapping("/admin/stores")
    public String listStores(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        
        try {
            List<Store> stores = storeService.findAllStores();
            model.addAttribute("stores", stores);
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error fetching store data");
        }
        model.addAttribute("currentUser", currentUser);
        return "stores"; 
    }

    @GetMapping("/create/store")
    public String createStoreForm(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
        if (!(currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) ) {
            return "error/403"; 
        }
        Store store = new Store();
        model.addAttribute("store", store);
        return "createStore";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "createStore";
        }
        
    }

    @PostMapping("/create/store")
    public String createStore(@ModelAttribute Store store, Model model,@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
        if (!(currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) ) {
            return "error/403"; 
        }   
            if(store.getManagerId()!=null){
                Employee employee= employeeService.getEmployeeById(store.getManagerId());
                if(employee==null || employee.getDesignation()==null || !employee.getDesignation().equals("Manager")){
                    model.addAttribute("errorMessage", "Incorrect ManagerId");
                    model.addAttribute("store", store);
                    return "createStore";
                }

            }
            
            storeService.createStore(store);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Wrong details, Creating store not allowed.."+e.getMessage());
            model.addAttribute("store", store);
            return "createStore";
        }
        return "redirect:/admin/stores";
    }


    @GetMapping("/view/store/{id}")
public String viewStoreDetails(Model model, @AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
    try {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        if ((currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) ) {
            
            Store store = storeService.getById(id);
            if (store == null) {
                model.addAttribute("errorMessage", "This store does not exist...");
                return "viewstore"; 
            }
            model.addAttribute("store", store);
            model.addAttribute("currentUser", currentUser);
        } else if((currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("MANAGER")))) {
            Long currStoreId=userService.getStoreIdById(currentUser.getId());
            if(!(id!=null && id.equals(currStoreId))){
                model.addAttribute("errorMessage", "Permission denied to view store details...");
                return "viewstore"; 
            }
            
        }else{
            return "error/403"; 
        }
        
    } catch (Exception e) {
        model.addAttribute("errorMessage", "Failed to fetch store details...");
        return "viewstore"; 
    }
    return "viewstore"; 
}

@PostMapping("/update/store/{id}")
public String updateStore(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, 
                          @ModelAttribute Store store, Model model) {
    try {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        if (!(currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) ) {
            return "error/403"; 
        }
        if(store.getManagerId()!=null){
            Employee employee= employeeService.getEmployeeById(store.getManagerId());
            if(employee==null ||employee.getDesignation()==null || !(employee.getDesignation().equals("Manager"))){
               
                model.addAttribute("errorMessage", "Failed to update store. Incorrect ManagerId");
                model.addAttribute("store", store); 
                return "createStore";
            }

        }
        storeService.updateStore(store);
        return "redirect:/view/store/" + id;
    } catch (Exception e) {
        model.addAttribute("errorMessage", "Failed to update store. Please try again.");
        model.addAttribute("store", store); 
        return "viewstore"; 
    }
}

@PostMapping("/delete/store/{id}")
public String deleteStore(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, Model model) {
    try {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        if (!(currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) ) {
            return "error/403"; 
        }
        storeService.deleteStoreById(id);
        return "redirect:/admin/stores";
    } catch (Exception e) {
        model.addAttribute("errorMessage", "Failed to update store. Please try again.");
        return "redirect:/admin/stores"; 
    }
}

}

