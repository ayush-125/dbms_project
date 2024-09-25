package com.store_management_system.sms.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.SupplierMail;


@Repository
public class SupplierMailRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SupplierMailRepository> findAll(){
        try {
            String sql="select * from supplierMails";
            List<SupplierMailRepository> supplierMails = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SupplierMailRepository.class));
            
            return supplierMails;
        } catch (DataAccessException  e) {
            System.err.println("Error querying Supplier emails  " + e.getMessage());
            throw new CustomDatabaseException("Error querying  Supplier emails"+e.getMessage(),e);
        }   
    }
    public List<SupplierMailRepository> findBySupplierId(Long supplierId){
        try {
            String sql="select * from supplierMails where supplierId=?  ";
            List<SupplierMailRepository> supplierMails = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SupplierMailRepository.class),supplierId);
            
            return supplierMails;
        } catch (DataAccessException  e) {
            System.err.println("Error querying Supplier email " + e.getMessage());
            throw new CustomDatabaseException("Error querying Supplier email "+e.getMessage(),e);
        }   
    }
    
    public void save(SupplierMail supplierMail) {
        try {
            
                String sql = "insert into supplierMails(supplierId,supplierEmail) values(?,?)";
                jdbcTemplate.update(sql,supplierMail.getSupplierId(),supplierMail.getSupplierEmail());
            
        } catch (DataAccessException e) {
            System.err.println("Error saving  Supplier email "+ e.getMessage());
            throw new CustomDatabaseException("Error saving Supplier email "+e.getMessage(),e);
        }
    }
    

    
    public Long count(){
        try {
            String sql="select count(*) from supplierMails";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            System.err.println("Error finding count of Supplier emails "+e.getMessage() );
            throw new CustomDatabaseException("Error finding count of Supplier emails  "+e.getMessage(),e);
        }
    }
    public void deleteById(Long supplierId,String supplierEmail){
        try {
            String sql="delete from supplierMails where supplierId=? and supplierEmail=?";
            jdbcTemplate.update(sql,supplierId,supplierEmail );
        } catch (DataAccessException e) {
            System.err.println("Error deleting Supplier email  " + e.getMessage());
            throw new CustomDatabaseException("Error deleting Supplier email"+e.getMessage(),e);
        }
    }
    public void deleteBySupplierId(Long supplierId){
        try {
            String sql="delete from supplierMails where supplierId=? ";
            jdbcTemplate.update(sql,supplierId );
        } catch (DataAccessException e) {
            System.err.println("Error deleting Supplier email  " + e.getMessage());
            throw new CustomDatabaseException("Error deleting Supplier email"+e.getMessage(),e);
        }
    }
}
