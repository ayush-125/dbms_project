package com.store_management_system.sms.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDate;


@Repository
public class ProductDiscountRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Discount> findByProductId(Long productId) {
        try {
            String sql = "SELECT * FROM discount WHERE productId = ? ";
            LocalDate currentDate = LocalDate.now();

            // Query for discounts that are valid for the current date
            List<Discount> discounts = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Discount.class));
            return discounts;
        } catch (DataAccessException e) {
            System.err.println("Error querying discounts for product ID: " + e.getMessage());
            throw new CustomDatabaseException("Error querying discounts for product", e);
        }
    }
    public List<Long> findByDiscountId(Long discountId){
        try {
            String sql="select productId from productDiscount where discountId=?";
            List<Long> productIds=jdbcTemplate.queryForList(sql,Long.class,discountId);
            
            return productIds;
        } catch (DataAccessException e) {
            System.err.println("Error querying productIds for a particular discount"+e.getMessage());
            throw new CustomDatabaseException("Error querying products for a discount"+e.getMessage(), e);
        }
    }
    public List<Product> findProductByDiscountID(Long discountId) {
    // Get the list of product IDs for the given discount ID
    List<Long> productIds = findByDiscountId(discountId);

    if (productIds.isEmpty()) {
        System.out.println("No products found for the given discount.");
        return Collections.emptyList();
    }

    try {
        // Query to fetch product details
        String sql = "SELECT * FROM products WHERE id IN (:productIds)";
        
        // Bind the list of product IDs to the SQL query
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("productIds", productIds);

        // Execute the query with the bound parameters
        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(Product.class));
    } catch (DataAccessException e) {
        System.err.println("Error querying products for a particular discount: " + e.getMessage());
        throw new CustomDatabaseException("Error querying products for a discount: " + e.getMessage(), e);
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
