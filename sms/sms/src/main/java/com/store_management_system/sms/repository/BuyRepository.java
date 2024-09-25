package com.store_management_system.sms.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Buy;
@Repository
public class BuyRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Buy> findAll(){
        try {
            String sql="select * from buy";
            List<Buy> buys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Buy.class));
            
            return buys;
        } catch (DataAccessException  e) {
            System.err.println("Error querying Buy : " + e.getMessage());
            throw new CustomDatabaseException("Error querying  Buy"+e.getMessage(),e);
        }   
    }
    public Buy findById(Long id){
        try {
            String sql="select * from buy where id=? ";
            List<Buy> buys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Buy.class),id);
            if(buys.isEmpty()){
                return null;
            }
            Buy buy=buys.get(0);
            return buy;
        } catch (DataAccessException  e) {
            System.err.println("Error querying Buy  " + e.getMessage());
            throw new CustomDatabaseException("Error querying Buy "+e.getMessage(),e);
        }   
    }
    
    public void save(Buy buy) {
        try {
            if (buy.getId() == null) {
                String sql = "insert into buy(dop,price,quantity,paymentMethod,payment,supplierId,inventoryId) values(?,?,?,?,?,?,?) ";
                jdbcTemplate.update(sql,buy.getDop(),buy.getPrice(),buy.getQuantity(),buy.getPaymentMethod(),buy.getPayment(),buy.getSupplierId(),buy.getInventoryId());
            } else {
                String sql = "UPDATE buy set dop=?,price=?,quantity=?,paymentMethod=?,payment=?,supplierId=?,inventoryId=? where id=?";
                jdbcTemplate.update(sql,buy.getDop(),buy.getPrice(),buy.getQuantity(),buy.getPaymentMethod(),buy.getPayment(),buy.getSupplierId(),buy.getInventoryId(),buy.getId());
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving or updating Buy  "+ e.getMessage());
            throw new CustomDatabaseException("Error saving or updating Buy  "+e.getMessage(),e);
        }
    }

    public Long count(){
        try {
            String sql="select count(*) from buy";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            System.err.println("Error finding count of Buy "+e.getMessage() );
            throw new CustomDatabaseException("Error finding count of  Buy  "+e.getMessage(),e);
        }
    }
    public void deleteById(Long id){
        try {
            String sql="delete from buy where id=?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting Buy : " + e.getMessage());
            throw new CustomDatabaseException("Error deleting Buy "+e.getMessage(),e);
        }
    }
}
