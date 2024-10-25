package com.store_management_system.sms.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Feedback;

@Repository
public class FeedbackRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Feedback> findAll() {
        try {
            String sql = "SELECT * FROM feedbacks";
            List<Feedback> feedbacks = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Feedback.class));
            
            return feedbacks;
        } catch (DataAccessException e) {
            System.err.println("Error querying feedbacks: " + e.getMessage());
            throw new CustomDatabaseException("Error querying all the employees",e);
        }
    }

    // public Feedback findById(Long id) {
    //     try {
    //         String sql = "SELECT * FROM feedbacks WHERE orderId = ?";
    //         List<Feedback> feedbacks = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Feedback.class), id);
    //         if (feedbacks.isEmpty()) {
    //             return null;
    //         }
    //         Feedback feedback = feedbacks.get(0);
            
    //         return feedback;
    //     } catch (DataAccessException e) {
    //         System.err.println("Error querying feedback : " + e.getMessage());
    //         throw new CustomDatabaseException("Error querying feedback: ",e);
    //     }
    // }

    public Feedback findByOrderId(Long id) {
        try {
            String sql = "SELECT * FROM feedbacks WHERE orderId = ?";
            List<Feedback> feedbacks = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Feedback.class), id);
            if (feedbacks.isEmpty()) {
                return null;
            }
            Feedback feedback = feedbacks.get(0);
            
            return feedback;
        } catch (DataAccessException e) {
            System.err.println("Error querying feedback : " + e.getMessage());
            throw new CustomDatabaseException("Error querying feedback: ",e);
        }
    }

    public void save(Feedback feedback) {
        try {
            Feedback chk=findByOrderId(feedback.getOrderId());
            if (chk == null) {
                String sql = "INSERT INTO feedbacks (fdate, rating, comments, orderId) VALUES (?,?, ?, ?)";
                jdbcTemplate.update(sql,feedback.getFdate(),feedback.getRating(),feedback.getComments(), feedback.getOrderId());
            } else {
                String sql = "UPDATE feedbacks SET fdate=?,rating=?,comments=? WHERE orderId = ?";
                jdbcTemplate.update(sql,feedback.getFdate(),feedback.getRating(),feedback.getComments(), feedback.getOrderId());
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving or updating feedback: " + e.getMessage());
            throw new CustomDatabaseException("Erro saving or updating feedback "+e.getMessage(), e);
        }
    }

    public void deleteByOrderId(Long id) {
        try {
            String sql = "DELETE FROM feedbacks WHERE orderId = ?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting feedback: " + e.getMessage());
            throw new CustomDatabaseException("Error deleting feedback: ",e);
        }
    }
    
    public Long count(){
        try {
            String sql="select count(*) from feedbacks";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            
            System.err.println("Error finding count of feedbacks: " + e.getMessage());
            throw new CustomDatabaseException("Error finding count of feedbacks",e);
            
        }
    }
}
