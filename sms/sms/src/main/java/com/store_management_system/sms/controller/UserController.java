package com.store_management_system.sms.controller;

import com.store_management_system.sms.model.Employee;
import com.store_management_system.sms.model.User;
import com.store_management_system.sms.service.EmployeeService;

import com.store_management_system.sms.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            model.addAttribute("user", currentUser);
            return "home";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "home";
        }
        
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        try {
            model.addAttribute("users", userService.getAllAdminUsers());
            
            return "users";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "users";
        }
        
    }

    @GetMapping("/manager/users")
    public String getUsersByStoreId(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        try{
            User user = userService.getUserByUsername(userDetails.getUsername());
            model.addAttribute("users", userService.getUsersWithSameStoreById(user.getId()));
        }catch(Exception e){
            model.addAttribute("errorMessage", e.getMessage());
        }
        
        return "users";
    }

    @GetMapping("/register")
    public String register(Model model,HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
        if (currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN") )) {
            
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("user", new User());
            model.addAttribute("check", false);
            return "register";
        }else{
            return "error/403"; 
        }
        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("check", false);
            return "register";
        }
    }
    
    @GetMapping("/register/{employeeId}")
    public String registerWithEmployeeId(Model model,@PathVariable Long employeeId, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
        if (currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN") || role.getName().equals("MANAGER"))) {
            
            model.addAttribute("currentUser", currentUser);
            User user=new User();
            user.setEmployeeId(employeeId);
            model.addAttribute("user", user);
            model.addAttribute("check", true);
            return "register";
        }else{
            return "error/403"; 
        }
        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("check", true);
            return "register";
        }
    }

    @PostMapping("/register")
    public String processRegistration(Model model,@PathVariable(required = false) Long employeeId,HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails,@ModelAttribute("user") User user) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        try {
            
            if (currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("MANAGER"))) {
                Long employeeId1=user.getEmployeeId();
                Long employeeId2=currentUser.getEmployeeId();
                model.addAttribute("check", true);
                if(!employeeService.checkBelongToSameStoreById(employeeId1,employeeId2)){
                    return "error/403"; 
                }
            }else if(currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("EMPLOYEE"))){
                return "error/403"; 
            }
            if(currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))){
                model.addAttribute("check", false);
            }
            userService.saveUser(user);
            
        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("user", user);
            model.addAttribute("check", true);
            if(currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))){
                model.addAttribute("check", false);
            }
            return "register";
        }
        
        return "redirect:/home";  
    }

    @GetMapping("/login")
    public String login() {
        
        return "login"; 
    }

    @GetMapping("/view/user/{id}")
    public String viewUserDetails(Model model,@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long id) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            User user =userService.getUserById(id);
                if(user==null){
                    model.addAttribute("errorMessage", "This user does not exist...");
                    return "viewuser";
                }
            if((currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) || ((currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("MANAGER"))) && userService.checkBelongToSameStore(id,currentUser))){
                
                model.addAttribute("user", user);
                model.addAttribute("currentUser", currentUser);
            }else{
                return "error/403"; 
            }
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to fetch user details...");
            return "viewuser";
            
        }
        return "viewuser";
    }
    

    @PostMapping("/update/user/{id}")   
    public String updateUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long id,@ModelAttribute User currentUser, @ModelAttribute User user, Model model) {
    try {
            
        if((currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) || ((currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("MANAGER"))) && userService.checkBelongToSameStore(id,currentUser))){
            userService.updateUser(user);
        }else{
            return "error/403"; 
        }
        
        return "redirect:/view/user/" + id;
    } catch (Exception e) {
        model.addAttribute("errorMessage", "Failed to update user. Please try again.");
        model.addAttribute("user", user);
        return "viewuser";  
    }
}

    @PostMapping("/delete/user/{id}")
    public String deleteUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long id, Model model){
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        try {
            
            if((currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) || ((currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("MANAGER"))) && userService.checkBelongToSameStore(id,currentUser))){
                userService.deleteUserById(id);
            }else{
                return "error/403"; 
            }
            if((currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("MANAGER"))) ){
                return "redirect:/employees";
            }else{
                return "redirect:/admin/stores";
            }

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to delete user. Please try again.");
            if((currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("MANAGER"))) ){
                return "redirect:/employees";
            }else{
                return "redirect:/admin/stores";
            }
        }
        
    }

    @PostMapping("/delete/admin/user/{id}")
    public String deleteAdminUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long id, Model model){
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            
            if((currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) ){
                userService.deleteUserById(id);
            }else{
                return "error/403"; 
            }

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to delete user. Please try again.");
            return "redirect:/admin/users";
        }
        return "redirect:/admin/users";
    }

}
