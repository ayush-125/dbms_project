package com.store_management_system.sms.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;

import java.util.List;

@Repository
public class ProductDiscountRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Long> findByDiscountId(Long discountId){
        try {
            String sql="select productId from productDiscount where discountId=?";
            List<Long> productIds=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Long.class),discountId);
            return productIds;
        } catch (DataAccessException e) {
            System.err.println("Error querying productIds for a particular discount"+e.getMessage());
            throw new CustomDatabaseException("Error querying products for a discount"+e.getMessage(), e);
        }
    }

    public void save(Long discountId,Long productId){
        try {
            String sql="insert into productDiscount(discountId,productId) values(?,?)";
            jdbcTemplate.update(sql, discountId,productId);
        } catch (DataAccessException e) {
            System.err.println("Error saving productIds for a particular discount"+e.getMessage());
            throw new CustomDatabaseException("Error saving products for a discount"+e.getMessage(), e);
        }
    }

    public void deleteByDiscountId(Long discountId){
        try {
            String sql="delete from productDiscount where discountId=?";
            jdbcTemplate.update(sql,discountId);
        } catch (DataAccessException e) {
            System.err.println("Error deleting productIds for a particular discount"+e.getMessage());
            throw new CustomDatabaseException("Error deleting products for a discount"+e.getMessage(), e);
        }
    }
}
