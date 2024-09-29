package com.store_management_system.sms.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Customer;
import com.store_management_system.sms.model.CustomerMail;

@Repository
public class CustomerRepository {
     @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CustomerMailRepository customerMailRepository;
    @Autowired
    private OrderRepository orderRepository;

    public List<Customer> findAll(){
        try {
            String sql="select * from customers";
            List<Customer> customers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class));
            for (Customer customer :customers){
                customer.setEmails(customerMailRepository.findByCustomerId(customer.getId()));
                customer.setOrders(orderRepository.findByCustomerId((long)customer.getId()));
                
            }
            return customers;
        } catch (DataAccessException  e) {
            System.err.println("Error querying Customer  " + e.getMessage());
            throw new CustomDatabaseException("Error querying  customer"+e.getMessage(),e);
        }   
    }
    public Customer findById(Long id){
        try {
            String sql="select * from customers where id=? ";
            List<Customer> customers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class),id);
            if(customers.isEmpty()){
                return null;
            }
            Customer customer=customers.get(0);
            customer.setEmails(customerMailRepository.findByCustomerId(customer.getId()));
            
            return customer;
        } catch (DataAccessException  e) {
            System.err.println("Error querying customer " + e.getMessage());
            throw new CustomDatabaseException("Error querying customer "+e.getMessage(),e);
        }   
    }
    public Customer findByPhoneNo(Long phoneNo){
        try {
            String sql="select * from customers where phoneNo=? ";
            List<Customer> customers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class),phoneNo);
            if(customers.isEmpty()){
                return null;
            }
            Customer customer=customers.get(0);
            customer.setEmails(customerMailRepository.findByCustomerId(customer.getId()));
            
            return customer;
        } catch (DataAccessException  e) {
            System.err.println("Error querying customer " + e.getMessage());
            throw new CustomDatabaseException("Error querying customer "+e.getMessage(),e);
        }   
    }
    
    public void save(Customer customer) {
        try {
            if (customer.getId() == null) {
                String sql = "insert into customers(account,firstName,middleName,lastName,phoneNo,houseNo,city,state,pincode,sex,dob) values(?,?,?,?,?,?,?,?,?,?,?)";
                jdbcTemplate.update(sql,customer.getAccount(),customer.getFirstName(),customer.getMiddleName(),customer.getLastName(),customer.getPhoneNo(),customer.getHouseNo(),customer.getCity(),customer.getState(),customer.getPincode(),customer.getSex(),customer.getDob());
                Long id=findByPhoneNo(customer.getPhoneNo()).getId();
                for(CustomerMail customerMail: customer.getEmails()){
                    customerMail.setCustomerId(id);
                    customerMailRepository.save(customerMail);
                }
                
            } else {
                String sql = "UPDATE customers set account=?,firstName=?,middleName=?,lastName=?,phoneNo=?,houseNo=?,city=?,state=?,pincode=?,sex=?,dob=? where id=? ";
                jdbcTemplate.update(sql,customer.getAccount(),customer.getFirstName(),customer.getMiddleName(),customer.getLastName(),customer.getPhoneNo(),customer.getHouseNo(),customer.getCity(),customer.getState(),customer.getPincode(),customer.getSex(),customer.getDob(),customer.getId());
                    customerMailRepository.deleteByCustomerId(customer.getId());
                for(CustomerMail customerMail: customer.getEmails()){
                    customerMail.setCustomerId(customer.getId());
                    customerMailRepository.save(customerMail);
                }
                
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving or updating customer  "+ e.getMessage());
            throw new CustomDatabaseException("Error saving or updating Customer  "+e.getMessage(),e);
        }
    }

    public Long count(){
        try {
            String sql="select count(*) from customers";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            System.err.println("Error finding count of customers "+e.getMessage() );
            throw new CustomDatabaseException("Error finding count of Customers  "+e.getMessage(),e);
        }
    }
    public void deleteById(Long id){
        try {
            String sql="delete from customers where id=?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting customer  " + e.getMessage());
            throw new CustomDatabaseException("Error deleting customer"+e.getMessage(),e);
        }
    }

}
