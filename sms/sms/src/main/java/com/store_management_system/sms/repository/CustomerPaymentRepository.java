package com.store_management_system.sms.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Customer;
import com.store_management_system.sms.model.CustomerPayment;

import com.store_management_system.sms.model.CustomerMail;

@Repository
public class CustomerPaymentRepository {
     @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CustomerMailRepository customerMailRepository;
    @Autowired
    private OrderRepository orderRepository;

    public List<CustomerPayment> findAll(){
        try {
            String sql="select * from customer_payment";
            List<CustomerPayment> customerpayments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CustomerPayment.class));
            return customerpayments;
        } catch (DataAccessException  e) {
            System.err.println("Error querying Customer  " + e.getMessage());
            throw new CustomDatabaseException("Error querying  customer"+e.getMessage(),e);
        }   
    }
    public CustomerPayment findById(Long id){
        try {
            String sql="select * from customer_payment where id=? ";
            List<CustomerPayment> customerpayments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CustomerPayment.class),id);
            if(customerpayments.isEmpty()){
                return null;
            }
            CustomerPayment customerpayment = customerpayments.get(0);
            return customerpayment;
        } catch (DataAccessException  e) {
            System.err.println("Error querying customer " + e.getMessage());
            throw new CustomDatabaseException("Error querying customer "+e.getMessage(),e);
        }   
    }
    public void save(CustomerPayment customerpayment) {
        try {
            if (customerpayment.getId() == null) {
                String sql = "insert into customer_payment(customer_id,payment_amount,payment_date) values(?,?,?)";
                jdbcTemplate.update(sql, customerpayment.getCustomerId(),customerpayment.getPaymentAmount(),customerpayment.getPaymentDate());  
            } else {
                String sql = "UPDATE customer_payment set customer_id=?,payment_amount=?,payment_date=? where id=? ";
                jdbcTemplate.update(sql, customerpayment.getCustomerId(),customerpayment.getPaymentAmount(),customerpayment.getPaymentDate(),customerpayment.getId());
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving or updating customer  "+ e.getMessage());
            throw new CustomDatabaseException("Error saving or updating Customer  "+e.getMessage(),e);
        }
    }

    public Long count(){
        try {
            String sql="select count(*) from customer_payment";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            System.err.println("Error finding count of customers "+e.getMessage() );
            throw new CustomDatabaseException("Error finding count of Customers  "+e.getMessage(),e);
        }
    }
    public void deleteById(Long id){
        try {
            String sql="delete from customer_payment where id=?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting customer payment  " + e.getMessage());
            throw new CustomDatabaseException("Error deleting customer payment "+e.getMessage(),e);
        }
    }
    public List<CustomerPayment> findPaymentsByCustomerId(Long customerId) {
        try {
            // SQL query to fetch payments for a specific customer by their ID
            String sql = "SELECT * FROM customer_payment WHERE customer_id = ?";
            
            // Execute the query and map the result to CustomerPayment objects
            List<CustomerPayment> customerPayments = jdbcTemplate.query(
                    sql, 
                    new BeanPropertyRowMapper<>(CustomerPayment.class), 
                    customerId  // Bind the customer ID to the query
            );
            return customerPayments;
        } catch (DataAccessException e) {
            System.err.println("Error querying Customer payments: " + e.getMessage());
            throw new CustomDatabaseException("Error querying customer payments: " + e.getMessage(), e);
        }
    }
}
