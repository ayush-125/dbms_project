package com.store_management_system.sms.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.exception.CustomServiceException;
import com.store_management_system.sms.model.Employee;
import com.store_management_system.sms.model.User;
import com.store_management_system.sms.repository.EmployeeRepository;
import com.store_management_system.sms.repository.UserRepository;


@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    
    public List<Employee> getAllEmployees() {
        try {
            List<Employee>employees= employeeRepository.findAll();
            for(Employee employee:employees){
                List<User>users=userRepository.findByEmployeeId(employee.getId());
                employee.setUsers(users);
            }
            return employees;
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
    }
    public Employee getEmployeeById(Long employeeId) {
        try {
            // return employeeRepository.findById(id);
            Employee employee = employeeRepository.findById(employeeId);
            if(employee==null){
                throw new CustomServiceException("Employee does not exist", null);
            }
            List<User> users = userRepository.findByEmployeeId(employeeId);
        employee.setUsers(users);
        return employee;
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }
    
    public void saveEmployee(Employee employee) {
        try {
            employeeRepository.save(employee);
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }

    public void deleteEmployeeById(Long id) {
        try {
            employeeRepository.deleteById(id);
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }
    public List<Employee> getByStoreId(Long StoreId) {
        try {
            List<Employee> employees =  employeeRepository.findByStoreId(StoreId);
            for (Employee employee : employees) {
                List<User> users = userRepository.findByEmployeeId(employee.getId());
                employee.setUsers(users);
            }
            return employees;
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
        
    }
    public boolean checkBelongToSameStoreById(Long id1,Long id2){
        try {
            Long storeId1=employeeRepository.getStoreIdById(id1);
            Long storeId2=employeeRepository.getStoreIdById(id2);
            if(storeId1!=null && storeId2!=null && storeId1.equals(storeId2)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw new CustomServiceException(e.getMessage(), e);
        }
    }
    public boolean checkBelongToSameStoreByStoreId(Long id1,Long id2){
        try {
            Long storeId1=id1;
            Long storeId2=employeeRepository.getStoreIdById(id2);
            if(storeId1!=null && storeId2!=null && storeId1.equals(storeId2)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw new CustomServiceException(e.getMessage(), e);
        }
    }
    

    public List<Employee> getEmployeesWithSameStoreById(Long id) {
        try {
            List<Employee>employees= employeeRepository.findEmployeesWithSameStoreById(id);
            for(Employee employee:employees){
                List<User>users=userRepository.findByEmployeeId(employee.getId());
                employee.setUsers(users);
            }
            return employees;
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
        
    }
    public Long getCountEmployees(){
        try {
            return employeeRepository.countEmployees();
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }
    public List<Employee> getAllManagers() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            List<Employee> managers = new ArrayList<>();
            
            for (Employee employee : employees) {
                // Convert designation to lowercase for case-insensitive comparison
                if ("manager".equals(employee.getDesignation().toLowerCase())) {
                    List<User> users = userRepository.findByEmployeeId(employee.getId());
                    employee.setUsers(users);
                    managers.add(employee);
                }
            }
            return managers;
        } catch (Exception e) {
            throw new CustomServiceException(e.getMessage(), e);
        }
    }
}
