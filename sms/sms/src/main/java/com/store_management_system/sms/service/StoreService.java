package com.store_management_system.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import com.store_management_system.sms.exception.CustomServiceException;
import com.store_management_system.sms.model.Employee;
import com.store_management_system.sms.repository.*;
import com.store_management_system.sms.repository.EmployeeRepository;
import java.util.List;
import java.util.ArrayList;
import com.store_management_system.sms.model.*;

@Service
public class StoreService {
    @Autowired
    public StoreRepository storeRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public InventoryRepository inventoryRepository;
    public Long getCountStores(){
        try {
            return storeRepository.countStores();
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }

    public List<Store> findAllStores() {
        try {
            List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            List<Employee> employees = employeeRepository.findByStoreId(store.getId());
            List <Product> products= productRepository.findAll();
            for(Employee employee:employees){
                List<User> users=userRepository.findByEmployeeId(employee.getId());
                
                employee.setUsers(users);
            }
            for(Product product:products){
                List<Inventory> inventories=inventoryRepository.findByProductAndStoreId(product.getId(),store.getId());
                product.setInventories(inventories);
            }
            store.setEmployees(employees);
            store.setProducts(products);
        }
        return stores;
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }
    public List<Store> findById(Long id) {
        try {
            List<Store> stores =new ArrayList<>();
            stores.add(storeRepository.findById(id)) ; 
        for (Store store : stores) {
            List<Employee> employees = employeeRepository.findByStoreId(store.getId());
            List <Product> products= productRepository.findAll();
            for(Employee employee:employees){
                List<User> users=userRepository.findByEmployeeId(employee.getId());
                
                employee.setUsers(users);
            }
            for(Product product:products){
                List<Inventory> inventories=inventoryRepository.findByProductAndStoreId(product.getId(),store.getId());
                product.setInventories(inventories);
            }
            store.setEmployees(employees);
            store.setProducts(products);
        }
        return stores;
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }

    public Store getById(Long storeId) {
        try {
            Store store = storeRepository.findById(storeId);
        List<Employee> employees = employeeRepository.findByStoreId(storeId);
        store.setEmployees(employees);
        return store;
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }

    public void createStore(Store store) {
        try {
            storeRepository.save(store);
        } catch (Exception e) {
            // TODO: handle exception
            throw new CustomServiceException(e.getMessage(), e);
        }
    }

    public void updateStore(Store store) {
        try {
            storeRepository.save(store);
        } catch (Exception e) {
            // TODO: handle exception
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }

    public void deleteStoreById(Long id){
        try {
            storeRepository.deleteById(id);
        } catch (Exception e) {
            // TODO: handle exception
            throw new CustomServiceException(e.getMessage(),e);
        }
    }
}
