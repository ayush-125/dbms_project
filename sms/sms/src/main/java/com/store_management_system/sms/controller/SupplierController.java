package com.store_management_system.sms.controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.store_management_system.sms.repository.SupplierRepository;
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
public class SupplierController {
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    UserService userService;

    @GetMapping("/suppliers")
    public String getSuppliers(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            if(currentUser.getRoleId().equals(3L)){
                return "error/403";
            }
            List<Supplier> suppliers=supplierRepository.findAll();
            model.addAttribute("suppliers",suppliers);
            return "suppliers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "suppliers";
        }
    }
    

    @GetMapping("/create/supplier")
    public String getCreateSupplier(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            if(currentUser.getRoleId().equals(3L)){
                return "error/403";
            }

            Supplier supplier=new Supplier();
            
            if(supplier.getEmails()==null){
                supplier.setEmails(new ArrayList<>());
            }
            for (int i = supplier.getEmails().size(); i < 5; i++) {
                supplier.getEmails().add(new SupplierMail());
            }
            model.addAttribute("supplier",supplier);
            return "createSupplier";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "createSupplier";
        }
        
    }
    @PostMapping("/create/supplier")
    public String postCreateSupplier(@ModelAttribute Supplier supplier,Model model,@AuthenticationPrincipal UserDetails userDetails) {
        try {
            
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            if(currentUser.getRoleId().equals(3L)){
                return "error/403";
            }

            supplier.getEmails().removeIf(email -> email.getSupplierEmail() == null || email.getSupplierEmail().isEmpty());
        
            supplierRepository.save(supplier);
            return "redirect:/suppliers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "createSupplier";
        }
    }

    @GetMapping("/view/supplier/{id}")
    public String getViewSupplier(@PathVariable Long id,Model model,@AuthenticationPrincipal UserDetails userDetails ) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            if(currentUser.getRoleId().equals(3L)){
                return "error/403";
            }
            
            Supplier supplier = supplierRepository.findById(id);
                if (supplier == null) {
                    model.addAttribute("errorMessage", "This supplier does not exist...");
                    model.addAttribute("supplier", supplier);
                    return "viewsupplier";
                }
                if(supplier.getEmails()==null){
                    supplier.setEmails(new ArrayList<>());
                }
                for (int i = supplier.getEmails().size(); i < 5; i++) {
                    supplier.getEmails().add(new SupplierMail());
                }
            model.addAttribute("supplier", supplier);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to fetch supplier details...");
            
            return "viewsupplier";
        }
        return "viewsupplier";
    }

    @PostMapping("/update/supplier/{id}")
    public String postUpdateSupplier(Model model,@ModelAttribute Supplier supplier,@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            if(currentUser.getRoleId().equals(  1L) || currentUser.getRoleId().equals(2L)){
                supplier.getEmails().removeIf(email -> email.getSupplierEmail() == null || email.getSupplierEmail().isEmpty());
        
                supplierRepository.save(supplier);
            }else{
                return "error/403";
            }
            
            return "redirect:/view/supplier/{id}";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to update supplier details.."+e.getMessage());
            model.addAttribute("supplier", supplier);
            return "viewsupplier";
        }
        
    }

    @PostMapping("/delete/supplier/{id}")
    public String deleteSupplier(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails,Model model) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
    try {
        if (!(currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("EMPLOYEE")))  ) {
                supplierRepository.deleteById(id);
                return "redirect:/suppliers";
        } else {
            return "error/403"; 
        }
        
        }catch(Exception e){
            model.addAttribute("errorMessage","Failed to delete the supplier."+e.getMessage());
            return "redirect:/suppliers";
        }
    
    }
    // @GetMapping("/search/supplier/{phoneNo}")
    // public String getMethodName(@PathVariable String firstName) {
    //     return new String();
    // }
    

    
}
