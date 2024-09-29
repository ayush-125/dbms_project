package com.store_management_system.sms.repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;

// import java.util.Collections;
import java.util.List;

import javax.management.RuntimeErrorException;

@Repository
public class SupplierRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SupplierMailRepository supplierMailRepository;
    @Autowired
    private BuyRepository buyRepository;
    public List<Supplier> findAll() {
        try {
            String sql = "SELECT * FROM suppliers";
            List<Supplier> suppliers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Supplier.class));
            for (Supplier supplier :suppliers){
                supplier.setEmails(supplierMailRepository.findBySupplierId(supplier.getId()));
                supplier.setBuys(buyRepository.findBySupplierId((long)supplier.getId()));
            }
            return suppliers;
        } catch (DataAccessException e) {
            System.err.println("Error querying suppliers: " + e.getMessage());
            throw new CustomDatabaseException("Error querying all the suppliers",e);
            
        }
    }

    public Supplier findById(Long id) {
        try {
            String sql = "SELECT * FROM suppliers WHERE id = ?";
            List<Supplier> suppliers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Supplier.class), id);
            if (suppliers.isEmpty()) {
                return null;
            }
            Supplier supplier = suppliers.get(0);
            supplier.setEmails(supplierMailRepository.findBySupplierId(supplier.getId()));
            
            return supplier;
        } catch (DataAccessException e) {
            System.err.println("Error querying supplier by id: " + e.getMessage());
            throw new CustomDatabaseException("Error querying supplier with id:"+id,e);
            // return null;
        }
    }
    public Supplier findByPhoneNo(Long phoneNo) {
        try {
            String sql = "SELECT * FROM suppliers WHERE phoneNo = ?";
            List<Supplier> suppliers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Supplier.class), phoneNo);
            if (suppliers.isEmpty()) {
                return null;
            }
            Supplier supplier = suppliers.get(0);
            supplier.setEmails(supplierMailRepository.findBySupplierId(supplier.getId()));
            
            return supplier;
        } catch (DataAccessException e) {
            System.err.println("Error querying supplier  " + e.getMessage());
            throw new CustomDatabaseException("Error querying supplier ",e);
            // return null;
        }
    }

    public void save(Supplier supplier) {
        try {
            if (supplier.getId() == null) {
                String sql = "INSERT INTO suppliers (name,phoneNo,street,state,city,pincode,account) VALUES (?, ?, ?, ?,?,?,?)";
                jdbcTemplate.update(sql, supplier.getName(), supplier.getPhoneNo(), supplier.getStreet(), supplier.getState(), supplier.getCity(), supplier.getPincode(),supplier.getAccount());
                Long id=findByPhoneNo(supplier.getPhoneNo()).getId();
                for(SupplierMail supplierMail: supplier.getEmails()){
                    supplierMail.setSupplierId(id);
                    supplierMailRepository.save(supplierMail);
                    
                }
                
            } else {
                String sql = "UPDATE suppliers SET name = ?,phoneNo=?,street=?,state=?,city=?,pincode=?,account=? WHERE id = ?";
                jdbcTemplate.update(sql, supplier.getName(), supplier.getPhoneNo(), supplier.getStreet(), supplier.getState(), supplier.getCity(), supplier.getPincode(),supplier.getAccount(), supplier.getId());
                supplierMailRepository.deleteBySupplierId(supplier.getId());
                for(SupplierMail supplierMail: supplier.getEmails()){
                    supplierMail.setSupplierId(supplier.getId());
                    supplierMailRepository.save(supplierMail);
                }
                
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving supplier: " + e.getMessage());
            throw new CustomDatabaseException("Error saving or updating supplier..."+e.getMessage(),e);
        }
    }
    public Long count(){
        try {
            String sql="select count(*) from suppliers";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            
            System.err.println("Error querying no. of suppliers: " + e.getMessage());
            
            throw new CustomDatabaseException("Error querying number of suppliers",e);
        
        }
    }
    public void deleteById(Long id) {
        try {
            String sql = "DELETE FROM suppliers WHERE id = ?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting supplier: " + e.getMessage());
            throw new CustomDatabaseException("Error deleting supplier with id:"+id,e);
        }
    }
}
