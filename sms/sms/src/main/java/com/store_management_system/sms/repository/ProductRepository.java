package com.store_management_system.sms.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Product;
@Repository
public class ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> findAll(){
        try {
            String sql="select * from products";
            List<Product> products = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
            
            return products;
        } catch (DataAccessException  e) {
            System.err.println("Error querying products: " + e.getMessage());
            throw new CustomDatabaseException("Error querying products "+e.getMessage(),e);
        }   
    }

    public Product findById(Long id){
        try {
            String sql="select * from products where id=? ";
            List<Product> products = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class),id);
            if(products.isEmpty()){
                return null;
            }
            Product product=products.get(0);
            return product;
        } catch (DataAccessException  e) {
            System.err.println("Error querying product " + e.getMessage());
            throw new CustomDatabaseException("Error querying product "+e.getMessage(),e);
        }
    }

    public void save(Product product) {
        try {
            if (product.getId() == null) {
                String sql = "insert into products(name,category,subcategory,brand,size,color,price) values(?,?,?,?,?,?,?)";
                jdbcTemplate.update(sql,product.getName(),product.getCategory(), product.getSubcategory(), product.getBrand(), product.getSize(), product.getColor(), product.getPrice());
            } else {
                String sql = "UPDATE products set name=?,category=?,subcategory=?,brand=?,size=?,color=?,price=? where id=? ";
                jdbcTemplate.update(sql,product.getName(),product.getCategory(), product.getSubcategory(), product.getBrand(), product.getSize(), product.getColor(), product.getPrice(),product.getId());
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving or updating product "+ e.getMessage());
            throw new CustomDatabaseException("Error saving or updating product  "+e.getMessage(),e);
        }
    }

    public Long count(){
        try {
            String sql="select count(*) from products";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            System.err.println("Error count of products "+e.getMessage() );
            throw new CustomDatabaseException("Error count of products  "+e.getMessage(),e);
        }
    }

    public void deleteById(Long id){
        try {
            String sql="delete from products where id=?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            throw new CustomDatabaseException("Error deleting product "+e.getMessage(),e);
        }
    }
    

}
