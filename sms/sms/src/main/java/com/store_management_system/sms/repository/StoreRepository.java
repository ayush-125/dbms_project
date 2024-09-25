package com.store_management_system.sms.repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;

// import java.util.Collections;
import java.util.List;

import javax.management.RuntimeErrorException;

@Repository
public class StoreRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    public List<Store> findAll() {
        try {
            String sql = "SELECT * FROM stores";
            List<Store> stores = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Store.class));
            
            return stores;
        } catch (DataAccessException e) {
            System.err.println("Error querying stores: " + e.getMessage());
            throw new CustomDatabaseException("Error querying all the stores",e);
            // return Collections.emptyList();
        }
    }

    public Store findById(Long id) {
        try {
            String sql = "SELECT * FROM stores WHERE id = ?";
            List<Store> stores = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Store.class), id);
            if (stores.isEmpty()) {
                return null;
            }
            Store store = stores.get(0);
            
            return store;
        } catch (DataAccessException e) {
            System.err.println("Error querying store by id: " + e.getMessage());
            throw new CustomDatabaseException("Error querying store with id:"+id,e);
            // return null;
        }
    }

    public void save(Store store) {
        try {
            if (store.getId() == null) {
                String sql = "INSERT INTO stores (name,street,city,state,pincode,phoneNo,emailId,managerId) VALUES (?, ?, ?, ?,?,?,?,?)";
                jdbcTemplate.update(sql, store.getName(),store.getStreet(),store.getCity(),store.getState(),store.getPincode(),store.getPhoneNo(),store.getEmailId(),store.getManagerId());
            } else {
                String sql = "UPDATE stores SET name = ?,street=?,city=?,state=?,pincode=?,phoneNo=?,emailId=?,managerId=? WHERE id = ?";
                jdbcTemplate.update(sql, store.getName(),store.getStreet(),store.getCity(),store.getState(),store.getPincode(),store.getPhoneNo(),store.getEmailId(),store.getManagerId(),store.getId());
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving store: " + e.getMessage());
            throw new CustomDatabaseException("Error saving or updating store...",e);
        }
    }
    public Long countStores(){
        try {
            String sql="select count(*) from stores";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            
            System.err.println("Error querying no. of stores: " + e.getMessage());
            
            throw new CustomDatabaseException("Error querying number of stores",e);
        
        }
    }
    public void deleteById(Long id) {
        try {
            String sql = "DELETE FROM stores WHERE id = ?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting store: " + e.getMessage());
            throw new CustomDatabaseException("Error deleting store with id:"+id,e);
        }
    }
}
