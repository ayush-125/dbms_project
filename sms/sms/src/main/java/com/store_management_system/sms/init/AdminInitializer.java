package com.store_management_system.sms.init;

import com.store_management_system.sms.model.User;
import com.store_management_system.sms.model.Role;

import com.store_management_system.sms.service.UserService;

import com.store_management_system.sms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
   

    @Override
    public void run(String... args) throws Exception {
        if (userService.getCountUsers().equals((long)0)) {

            Role adminRole = roleRepository.findByName("ADMIN");
            Role managerRole = roleRepository.findByName("MANAGER");
            Role employeeRole = roleRepository.findByName("EMPLOYEE");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ADMIN");
                adminRole.setId((long)1);
                roleRepository.save(adminRole);
            }
            if(managerRole==null){
                managerRole=new Role();
                managerRole.setId((long)2);
                managerRole.setName("MANAGER");
                roleRepository.save(managerRole);
            }
            if(employeeRole==null){
                employeeRole=new Role();
                employeeRole.setId((long)3);
                employeeRole.setName("EMPLOYEE");
                roleRepository.save(employeeRole);
            }


            // Create admin user with encoded password
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123");  // Encoding the password
            adminUser.setRoles(Collections.singletonList(adminRole));
            adminUser.setRoleId((long)1);
            // adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
            userService.saveUser(adminUser);

            System.out.println("Admin user created with username: admin and password: admin123");
        }
        
    }
}

