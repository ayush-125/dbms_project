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
public class CustomerMailRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CustomerMail> findAll(){
        try {
            String sql="select * from customeremails";
            List<CustomerMail> customerMails = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CustomerMail.class));
            
            return customerMails;
        } catch (DataAccessException  e) {
            System.err.println("Error querying Customer emails  " + e.getMessage());
            throw new CustomDatabaseException("Error querying  customer emails"+e.getMessage(),e);
        }   
    }
    public List<CustomerMail> findByCustomerId(Long customerId){
        try {
            String sql="select * from customeremails where customerId=?  ";
            List<CustomerMail> customerMails = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CustomerMail.class),customerId);
            
            return customerMails;
        } catch (DataAccessException  e) {
            System.err.println("Error querying customer email " + e.getMessage());
            throw new CustomDatabaseException("Error querying customer email "+e.getMessage(),e);
        }   
    }
    
    public void save(CustomerMail customerMail) {
        try {
            
                String sql = "insert into customeremails(customerId,customerEmail) values(?,?)";
                jdbcTemplate.update(sql,customerMail.getCustomerId(),customerMail.getCustomerEmail());
            
        } catch (DataAccessException e) {
            System.err.println("Error saving  customer email "+ e.getMessage());
            throw new CustomDatabaseException("Error saving Customer email "+e.getMessage(),e);
        }
    }
    

    public Long count(){
        try {
            String sql="select count(*) from customeremails";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            System.err.println("Error finding count of customer emails "+e.getMessage() );
            throw new CustomDatabaseException("Error finding count of Customer emails  "+e.getMessage(),e);
        }
    }
    public void deleteById(Long customerId,String customerEmail){
        try {
            String sql="delete from customeremails where customerId=? and customerEmail=?";
            jdbcTemplate.update(sql,customerId,customerEmail );
        } catch (DataAccessException e) {
            System.err.println("Error deleting customer email  " + e.getMessage());
            throw new CustomDatabaseException("Error deleting customer email"+e.getMessage(),e);
        }
    }

    public void deleteByCustomerId(Long customerId){
        try {
            String sql="delete from customeremails where customerId=? ";
            jdbcTemplate.update(sql,customerId );
        } catch (DataAccessException e) {
            System.err.println("Error deleting Customer email  " + e.getMessage());
            throw new CustomDatabaseException("Error deleting Customer email"+e.getMessage(),e);
        }
    }
}
