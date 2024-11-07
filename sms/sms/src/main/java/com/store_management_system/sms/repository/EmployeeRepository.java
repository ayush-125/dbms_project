package com.store_management_system.sms.repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Employee;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;

// import java.util.Collections;
import java.util.List;

import javax.management.RuntimeErrorException;

@Repository
public class EmployeeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    public List<Employee> findAll() {
        try {
            String sql = "SELECT * FROM employees";
            List<Employee> employees = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
            
            return employees;
        } catch (DataAccessException e) {
            System.err.println("Error querying employees: " + e.getMessage());
            throw new CustomDatabaseException("Error querying all the employees",e);
            // return Collections.emptyList();
        }
    }

    public Employee findById(Long id) {
        try {
            String sql = "SELECT * FROM employees WHERE id = ?";
            List<Employee> employees = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class), id);
            if (employees.isEmpty()) {
                return null;
            }
            Employee employee = employees.get(0);
            
            return employee;
        } catch (DataAccessException e) {
            System.err.println("Error querying employee by id: " + e.getMessage());
            throw new CustomDatabaseException("Error querying  employee with id:"+id,e);
            // return null;
        }
    }
    public Employee findByPhoneNo(Long id) {
        try {
            String sql = "SELECT * FROM employees WHERE phoneNo = ?";
            List<Employee> employees = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class), id);
            if (employees.isEmpty()) {
                return null;
            }
            Employee employee = employees.get(0);
            
            return employee;
        } catch (DataAccessException e) {
            System.err.println("Error querying employee by phone no: " + e.getMessage());
            throw new CustomDatabaseException("Error querying  employee with phone no:"+id,e);
            // return null;
        }
    }

    public void save(Employee employee) {
        try {
            if (employee.getId() == null) {
                String sql = "INSERT INTO employees (supervisor,salary,dob,sex,firstName,middleName,lastName,houseNo,city,state,pincode,designation,phoneNo,emailId,storeId) VALUES (?,?, ?,?, ?, ?,?,?,?,?,?,?,?,?,?)";
                jdbcTemplate.update(sql,employee.getSupervisor(),employee.getSalary(),employee.getDob(),employee.getSex(), employee.getFirstName(), employee.getMiddleName(), employee.getLastName(), employee.getHouseNo(),employee.getCity(),employee.getState(),employee.getPincode(),employee.getDesignation(),employee.getPhoneNo(),employee.getEmailId(),employee.getStoreId());
            } else {
                String sql = "UPDATE employees SET supervisor=?, salary=?,dob=?,sex=?,firstName = ?, middleName = ?,lastName=?,houseNo=?,city=?,state=?,pincode=?,designation=?,phoneNo=?,emailId=?,storeId=? WHERE id = ?";
                jdbcTemplate.update(sql,employee.getSupervisor(),employee.getSalary(),employee.getDob(),employee.getSex(), employee.getFirstName(), employee.getMiddleName(), employee.getLastName(), employee.getHouseNo(),employee.getCity(),employee.getState(),employee.getPincode(),employee.getDesignation(),employee.getPhoneNo(),employee.getEmailId(),employee.getStoreId(),employee.getId());
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving employee: " + e.getMessage());
            // throw new CustomDatabaseException("Failed to save employee"+employee.getSalary(),employee.getDob()+employee.getSex()+ employee.getFirstName()+ employee.getMiddleName()+ employee.getLastName()+employee.getHouseNo()+employee.getCity()+employee.getState()+employee.getPincode()+employee.getDesignation()+employee.getPhoneNo()+employee.getEmailId()+employee.getStoreId(),e);

        }
    }

    public void deleteById(Long id) {
        try {
            String sql = "DELETE FROM employees WHERE id = ?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            throw new CustomDatabaseException("Error deleting employee with id:"+id,e);
        }
    }
    public Long getStoreIdById(Long id){
        try {
            String sql="(select storeId from employees where  id=?)";
            
            List<Long> storeIds = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("storeId"), id);
            // Assuming you expect only one result
            Long storeId = storeIds.isEmpty() ? null : storeIds.get(0);
            return storeId;
        } catch (DataAccessException e) {
            // TODO: handle exception
            throw new CustomDatabaseException(e.getMessage(), e);
        }
    }

    public List<Employee> findByStoreId(Long storeId){
        try{String sql="Select * from employees where storeId =?";
        List<Employee> employees = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class), storeId);
        
        return employees;
    }catch(DataAccessException e){
        System.err.println("Error querying employees with given storeId: " + e.getMessage());
        throw new CustomDatabaseException("Error querying all the employees with storeId"+storeId,e);
        // return Collections.emptyList();
    }
    }
    public List<Employee> findEmployeesWithSameStoreById(Long id){
        try{String sql="Select * from employees where storeId in "+
        "(select storeId from employees where id=?)";
        List<Employee> employees = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class), id);
        
        return employees;
    }catch(DataAccessException e){
        System.err.println("Error querying employees with given id: " + e.getMessage());
        throw new CustomDatabaseException("Error querying all the employees in same store with employee id"+id,e);
        // return Collections.emptyList();
    }
    }

    public Long countEmployees(){
        try {
            String sql="select count(*) from employees";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            
            System.err.println("Error querying no. of employees: " + e.getMessage());
            throw new CustomDatabaseException("Error querying number of employees",e);
            // return null;
        }
    }
    public List<Employee> findAllManagers() {
        try {
            // SQL query with case-insensitivity and trimming of spaces for the designation field
            String sql = "SELECT * FROM employees WHERE TRIM(LOWER(designation)) = 'manager'";
            
            List<Employee> managers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
            
            return managers;
        } catch (DataAccessException e) {
            System.err.println("Error querying managers: " + e.getMessage());
            throw new CustomDatabaseException("Error querying all employees with designation 'manager'.", e);
        }
    }
    public List<Employee> getManagersByStoreId(Long storeId) {
        try {
            // Get the store ID for the provided username
            // Long storeId = getStoreIdByUsername(username);
            
            // if (storeId == null) {
            //     throw new CustomDatabaseException("Store ID not found for username: " + username);
            // }
            
            // SQL query to find managers for the given store ID, with case-insensitivity and trimming for the designation field
            String sql = "SELECT * FROM employees WHERE TRIM(LOWER(designation)) = 'manager' AND storeId = ?";
            
            List<Employee> managers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class), storeId);
            
            return managers;
        } catch (DataAccessException e) {
            System.err.println("Error querying managers by store ID: " + e.getMessage());
            throw new CustomDatabaseException("Error querying managers for store ID: " + storeId, e);
        }
    }

}
