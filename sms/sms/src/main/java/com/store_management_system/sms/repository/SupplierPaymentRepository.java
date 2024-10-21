package com.store_management_system.sms.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Customer;
import com.store_management_system.sms.model.Supplier;
import com.store_management_system.sms.model.SupplierPayment;

import com.store_management_system.sms.model.CustomerMail;

@Repository
public class SupplierPaymentRepository {
     @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CustomerMailRepository customerMailRepository;
    @Autowired
    private OrderRepository orderRepository;

    public List<SupplierPayment> findAll(){
        try {
            String sql="select * from supplier_payment";
            List<SupplierPayment> supplierpayments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SupplierPayment.class));
            return supplierpayments;
        } catch (DataAccessException  e) {
            System.err.println("Error querying Supplier Payments  " + e.getMessage());
            throw new CustomDatabaseException("Error querying  Supplier Payments"+e.getMessage(),e);
        }   
    }
    public SupplierPayment findById(Long id){
        try {
            String sql="select * from supplier_payment where id=? ";
            List<SupplierPayment> supplierpayments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SupplierPayment.class),id);
            if(supplierpayments.isEmpty()){
                return null;
            }
            SupplierPayment supplierpayment = supplierpayments.get(0);
            return supplierpayment;
        } catch (DataAccessException  e) {
            System.err.println("Error querying Supplier Payments " + e.getMessage());
            throw new CustomDatabaseException("Error querying Supplier Payments "+e.getMessage(),e);
        }   
    }
    public void save(SupplierPayment supplierpayment) {
        try {
            if (supplierpayment.getId() == null) {
                String sql = "insert into supplier_payment(supplier_id,payment_amount,payment_date) values(?,?,?)";
                jdbcTemplate.update(sql, supplierpayment.getSupplierId(),supplierpayment.getPaymentAmount(),supplierpayment.getPaymentDate());  
            } else {
                String sql = "UPDATE supplier_payment_payment set supplier_id=?,payment_amount=?,payment_date=? where id=? ";
                jdbcTemplate.update(sql, supplierpayment.getSupplierId(),supplierpayment.getPaymentAmount(),supplierpayment.getPaymentDate(),supplierpayment.getId());
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving or updating Supplier Payments  "+ e.getMessage());
            throw new CustomDatabaseException("Error saving or updating Supplier Payments  "+e.getMessage(),e);
        }
    }

    public Long count(){
        try {
            String sql="select count(*) from supplier_payment";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            System.err.println("Error finding count of Supplier Payments "+e.getMessage() );
            throw new CustomDatabaseException("Error finding count of Supplier Payments  "+e.getMessage(),e);
        }
    }
    public void deleteById(Long id){
        try {
            String sql="delete from supplier_payment where id=?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting Supplier Payment  " + e.getMessage());
            throw new CustomDatabaseException("Error deleting Supplier Payment "+e.getMessage(),e);
        }
    }
    public List<SupplierPayment> findPaymentsBySupplierId(Long supplierId) {
        try {
            // SQL query to fetch payments for a specific supplier by their ID
            String sql = "SELECT * FROM supplier_payment WHERE supplier_id = ?";
            
            // Execute the query and map the result to SupplierPayment objects
            List<SupplierPayment> supplierPayments = jdbcTemplate.query(
                    sql, 
                    new BeanPropertyRowMapper<>(SupplierPayment.class), 
                    supplierId // Bind the supplier ID to the query
            );
            return supplierPayments;
        } catch (DataAccessException e) {
            System.err.println("Error querying Supplier payments: " + e.getMessage());
            throw new CustomDatabaseException("Error querying Supplier payments: " + e.getMessage(), e);
        }
    }
}
