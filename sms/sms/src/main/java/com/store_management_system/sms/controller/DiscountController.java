package com.store_management_system.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.store_management_system.sms.repository.DiscountRepository;
import com.store_management_system.sms.model.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class DiscountController {
    @Autowired
    private DiscountRepository discountRepository;

    @GetMapping("/discounts")
    public String getDiscounts(Model model) {
        try {
            List<Discount> discounts=discountRepository.findAll();
            model.addAttribute("discounts", discounts);
        
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to show discounts: " + e.getMessage());
        }
        return "discount";
    }

    @GetMapping("/create/discount")
    public String getCreateDiscount(Model model) {
        try {
            Discount discount=new Discount();
            if(discount.getProductIds()==null){
                discount.setProductIds(new ArrayList<>());
            }
            for (int i = discount.getProductIds().size(); i < 10; i++) {
                discount.getProductIds().add(null);
            }
            model.addAttribute("discount", discount);
            return "createDiscount";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Something went wrong. Please try again later: " + e.getMessage());
        
            return "redirect:/discounts";
        }
    }

    @PostMapping("/create/discount")
    public String postCreateDiscount(Model model,@ModelAttribute Discount discount) {
        try {
            if(discount.getProductIds()!=null){
                discount.getProductIds().removeIf(productId -> productId == null );
            }
            if(discount.getProductIds()==null){
                discount.setProductIds(new ArrayList<>());
            }
            discountRepository.save(discount);
            return "redirect:/discounts";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to create discount: " + e.getMessage());
            model.addAttribute("discount", discount);
            return "createDiscount";
        }
        
    }
    
    @GetMapping("/view/discount/{id}")
    public String viewDiscount(Model model,@PathVariable Long id) {
        try {
            Discount discount=discountRepository.findById(id);
            if(discount.getProductIds()==null){
                discount.setProductIds(new ArrayList<>());
            }
            for (int i = discount.getProductIds().size(); i < 10; i++) {
                discount.getProductIds().add(null);
            }
            model.addAttribute("discount", discount);
            return "viewdiscount";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to show discount details: " + e.getMessage());
        
            return "redirect:/discounts";
        }
    }
    
    @PostMapping("/update/discount/{id}")
    public String updateDiscount(Model model,@PathVariable Long id,@ModelAttribute Discount discount) {
        
        try {
            if(discount.getProductIds()!=null){
                discount.getProductIds().removeIf(productId -> productId == null );
            }
            if(discount.getProductIds()==null){
                discount.setProductIds(new ArrayList<>());
            }
            discountRepository.save(discount);

            return "redirect:/discounts";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Something Went wrong. Try agin later." + e.getMessage());
            model.addAttribute("discount", discount);
            return "viewdiscount";
        }
    }
    
    @PostMapping("/delete/discount/{id}")
    public String postMethodName(Model model,@PathVariable Long id) {
        try {
            discountRepository.delete(id);
        } catch (Exception e) {
            model.addAttribute("errorMessage","Failed to delete this discount" + e.getMessage());

        }
        return "redirect:/discounts";
    }
    
    
}
