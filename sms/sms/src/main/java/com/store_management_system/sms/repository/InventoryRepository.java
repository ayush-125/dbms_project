package com.store_management_system.sms.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Inventory;
@Repository
public class InventoryRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Inventory> findAll(){
        try {
            String sql="select * from inventory";
            List<Inventory> inventorys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Inventory.class));
            
            return inventorys;
        } catch (DataAccessException  e) {
            System.err.println("Error querying inventorys: " + e.getMessage());
            throw new CustomDatabaseException("Error querying inventorys "+e.getMessage(),e);
        }   
    }

    public Inventory findById(Long id){
        try {
            String sql="select * from inventory where id=? ";
            List<Inventory> inventorys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Inventory.class),id);
            if(inventorys.isEmpty()){
                return null;
            }
            Inventory inventory=inventorys.get(0);
            return inventory;
        } catch (DataAccessException  e) {
            System.err.println("Error querying inventory " + e.getMessage());
            throw new CustomDatabaseException("Error querying inventory "+e.getMessage(),e);
        }
    }
    public Double getPriceById(Long id){
        try {
            String sql="select price from productD where id=any(select productId from inventory where id=?) ";
            Double price = jdbcTemplate.queryForObject(sql, Double.class,id);
            
            return price;
        } catch (DataAccessException  e) {
            System.err.println("Error querying inventory product price " + e.getMessage());
            throw new CustomDatabaseException("Error querying inventory product price"+e.getMessage(),e);
        }
    }
    public List<Inventory> findByProductAndStoreId(Long productId,Long storeId){
        try {
            String sql="select * from inventory where productId=? and storeId=?";
            List<Inventory> inventories=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Inventory.class),productId,storeId);
            return inventories;
        } catch (Exception e) {
            System.err.println("Error querying inventory " + e.getMessage());
            throw new CustomDatabaseException("Error querying inventory "+e.getMessage(),e);
        }
    }

    public void save(Inventory inventory) {
        try {
            if (inventory.getId() == null) {
                String sql = "insert into inventory(productId,storeId,quantity) values(?,?,?)";
                jdbcTemplate.update(sql,inventory.getProductId(),inventory.getStoreId(), inventory.getQuantity());
            } else {
                String sql = "UPDATE inventory set productId=?,storeId=?,quantity=? where id=? ";
                jdbcTemplate.update(sql,inventory.getProductId(),inventory.getStoreId(), inventory.getQuantity(),inventory.getId());
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving or updating inventory "+ e.getMessage());
            throw new CustomDatabaseException("Error saving or updating inventory  "+e.getMessage(),e);
        }
    }

    public Long count(){
        try {
            String sql="select count(*) from inventory";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            System.err.println("Error count of inventory "+e.getMessage() );
            throw new CustomDatabaseException("Error count of inventory  "+e.getMessage(),e);
        }
    }

    public void deleteById(Long id){
        try {
            String sql="delete from inventory where id=?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting inventory: " + e.getMessage());
            throw new CustomDatabaseException("Error deleting inventory "+e.getMessage(),e);
        }
    }
}
