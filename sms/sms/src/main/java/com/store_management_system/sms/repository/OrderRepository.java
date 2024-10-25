package com.store_management_system.sms.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.ReturnedType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.*;
@Repository
public class OrderRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReturnRepository returnRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Order> findAll(){
        try {
            String sql="select * from orders";
            List<Order> orders = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class));
            for(Order order:orders){
                Return return1=returnRepository.findByOrderId(order.getId());
                Feedback feedback=feedbackRepository.findByOrderId(order.getId());
                if(return1!=null){
                    order.setReturn(return1);
                }
                if(feedback!=null){
                    order.setFeedback(feedback);
                
                }
            }
            return orders;
        } catch (DataAccessException  e) {
            System.err.println("Error querying orders: " + e.getMessage());
            throw new CustomDatabaseException("Error querying orders "+e.getMessage(),e);
        }   
    }

    public Order findById(Long id){
        try {
            String sql="select * from orders where id=? ";
            List<Order> orders = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class),id);
            if(orders.isEmpty()){
                return null;
            }
            Order order=orders.get(0);
            Return return1=returnRepository.findByOrderId(order.getId());
                Feedback feedback=feedbackRepository.findByOrderId(order.getId());
                if(return1!=null){
                    order.setReturn(return1);
                }
                if(feedback!=null){
                    order.setFeedback(feedback);
                
                }
                
            return order;
        } catch (DataAccessException  e) {
            System.err.println("Error querying order " + e.getMessage());
            throw new CustomDatabaseException("Error querying order "+e.getMessage(),e);
        }
    }
    public List<Order> findByCustomerId(Long id){
        try {
            String sql="select * from orders where customerId=? ";
            List<Order> orders = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class),id);
            // for(Order order:orders){
            //     order.setFeedbackId(feedbackRepository.findByOrderId(order.getId()).getId());
            //     order.setReturnId(returnRepository.findByOrderId(order.getId()).getId());
            // }
            for(Order order:orders){
                Return return1=returnRepository.findByOrderId(order.getId());
                Feedback feedback=feedbackRepository.findByOrderId(order.getId());
                if(return1!=null && !return1.isEmpty()){
                    order.setReturn(return1);
                }
                if(feedback!=null && !feedback.isEmpty()){
                    order.setFeedback(feedback);
                
                }
                
            }
            return orders;
        } catch (DataAccessException  e) {
            System.err.println("Error querying order " + e.getMessage());
            throw new CustomDatabaseException("Error querying order "+e.getMessage(),e);
        }
    }
    

    public void save(Order order) {
        try {
            if (order.getId() == null) {
                String sql = "insert into orders(price, quantity,  productId,storeId, odate, houseNo, street, city, state, pincode, paymentMethod, payment, employeeId, customerId) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                jdbcTemplate.update(sql,order.getPrice(),order.getQuantity(),order.getProductId(),order.getStoreId(), order.getOdate(), order.getHouseNo(), order.getStreet(), order.getCity(), order.getState(), order.getPincode(), order.getPaymentMethod(), order.getPayment(), order.getEmployeeId(), order.getCustomerId());
            } else {
                String sql = "UPDATE orders set  productId=?,storeId=?, quantity=?, price=?, odate=?, houseNo=?, street=?, city=?, state=?, pincode=?, paymentMethod=?, payment=?, employeeId=?, customerId=? where id=? ";
                jdbcTemplate.update(sql, order.getProductId(),order.getStoreId(),order.getQuantity(),order.getPrice(),  order.getOdate(), order.getHouseNo(), order.getStreet(), order.getCity(), order.getState(), order.getPincode(), order.getPaymentMethod(), order.getPayment(), order.getEmployeeId(), order.getCustomerId(), order.getId());
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving or updating order "+ e.getMessage());
            throw new CustomDatabaseException("Error saving or updating order  "+e.getMessage(),e);
        }
    }

    public Long count(Order order){
        try {
            String sql="select count(*) from orders";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            System.err.println("Error count of orders "+e.getMessage() );
            throw new CustomDatabaseException("Error count of orders  "+e.getMessage(),e);
        }
    }

    public void deleteById(Long id){
        try {
            String sql="delete from orders where id=?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting order: " + e.getMessage());
            throw new CustomDatabaseException("Error deleting order "+e.getMessage(),e);
        }
    }

}
