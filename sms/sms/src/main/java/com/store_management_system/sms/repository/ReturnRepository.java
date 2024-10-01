package com.store_management_system.sms.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Return;
@Repository
public class ReturnRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Return> findAll(){
        try {
            String sql="select * from returnproducts";
            List<Return> returns = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Return.class));
            
            return returns;
        } catch (DataAccessException  e) {
            System.err.println("Error querying returns: " + e.getMessage());
            throw new CustomDatabaseException("Error querying returns "+e.getMessage(),e);
        }   
    }

    public Return findById(Long id){
        try {
            String sql="select * from returnproducts where id=? ";
            List<Return> returns = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Return.class),id);
            if(returns.isEmpty()){
                return null;
            }
            Return return1=returns.get(0);
            return return1;
        } catch (DataAccessException  e) {
            System.err.println("Error querying return " + e.getMessage());
            throw new CustomDatabaseException("Error querying return "+e.getMessage(),e);
        }
    }
    public Return findByOrderId(Long id){
        try {
            String sql="select * from returnproducts where orderId=? ";
            List<Return> returns = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Return.class),id);
            if(returns.isEmpty()){
                return null;
            }
            Return return1=returns.get(0);
            return return1;
        } catch (DataAccessException  e) {
            System.err.println("Error querying return " + e.getMessage());
            throw new CustomDatabaseException("Error querying return "+e.getMessage(),e);
        }
    }

    public void save(Return return1) {
        try {
            if (return1.getId() == null) {
                String sql = "insert into returnproducts(rdate,reason,quantity,price,orderId) values(?,?,?,?,?)";
                jdbcTemplate.update(sql,return1.getRdate(),return1.getReason(), return1.getQuantity(), return1.getPrice(), return1.getOrderId());
            } else {
                String sql = "UPDATE returnproducts set rdate=?,reason=?,quantity=?,price=?,orderId=? where id=? ";
                jdbcTemplate.update(sql,return1.getRdate(),return1.getReason(), return1.getQuantity(), return1.getPrice(), return1.getOrderId(),return1.getId());
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving or updating return "+ e.getMessage());
            throw new CustomDatabaseException("Error saving or updating return  "+e.getMessage(),e);
        }
    }

    public Long count(){
        try {
            String sql="select count(*) from returnproducts";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            System.err.println("Error count of returns "+e.getMessage() );
            throw new CustomDatabaseException("Error count of returns  "+e.getMessage(),e);
        }
    }

    public void deleteById(Long id){
        try {
            String sql="delete from returnproducts where id=?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting return: " + e.getMessage());
            throw new CustomDatabaseException("Error deleting return "+e.getMessage(),e);
        }
    }
}
