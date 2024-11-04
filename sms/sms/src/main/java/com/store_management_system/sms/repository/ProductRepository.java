package com.store_management_system.sms.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Discount;
import com.store_management_system.sms.model.Product;

import java.time.LocalDate;
@Repository
public class ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> findAll() {
        try {
            String sql = "SELECT * FROM products";
            List<Product> products = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    
            // Current date to match discounts
            LocalDate currentDate = LocalDate.now();
    
            // Loop through products to set the discount attribute
            for (Product product : products) {
                // SQL to join productDiscount and discount tables to get active discounts
                String discountSql = "SELECT d.* FROM productdiscount pd JOIN discount d ON pd.discountid = d.id " +
                                     "WHERE pd.productid = ? AND d.dos <= ? AND d.doe >= ?";
                
                List<Discount> activeDiscounts = jdbcTemplate.query(
                    discountSql,
                    new BeanPropertyRowMapper<>(Discount.class),
                    product.getId(), currentDate, currentDate
                );
    
                // Set the discount attribute if there's an active discount
                // if (!activeDiscounts.isEmpty()) {
                    product.setDiscount(activeDiscounts); // Assuming one active discount at a time
                // }
            }
            return products;
        } catch (DataAccessException e) {
            System.err.println("Error querying products: " + e.getMessage());
            throw new CustomDatabaseException("Error querying products " + e.getMessage(), e);
        }
    }

    public List<Product> findByStoreId(Long storeId){
        try {
            String sql="select * from productD where id in (select distinct productId from inventory where storeId=? )";
            List<Product> products=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Product.class),storeId);
            return products;
        } catch (Exception e) {
            System.err.println("Error querying products for the store: " + e.getMessage());
            throw new CustomDatabaseException("Error querying products for the store"+e.getMessage(),e);
        }
    }

    public Product findById(Long id){
        try {
            String sql="select * from productD where id=? ";
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
