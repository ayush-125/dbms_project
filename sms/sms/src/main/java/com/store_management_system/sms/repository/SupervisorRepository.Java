package com.store_management_system.sms.repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Supervisor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;

import java.util.List;

import javax.management.RuntimeErrorException
@Repository
public class SupervisorRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    public List<Supervisor> findAll() {
        try {
            String sql = "SELECT * FROM supervisors";
            List<Supervisor> supervisors = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Supervisor.class));
            
            return supervisors;
        } catch (DataAccessException e) {
            System.err.println("Error querying supervisors: " + e.getMessage());
            throw new CustomDatabaseException("Error querying all the supervisors"+ e.getMessage(),e);
            
        }
    }

    public List<Supervisor> findBySupervisor(Long supervisor) {
        try {
            String sql = "SELECT * FROM supervisors WHERE supervisor = ?";
            List<Supervisor> workers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Supervisor.class), supervisor);
            
            return workers;
        } catch (DataAccessException e) {
            System.err.println("Error querying supervisor : " + e.getMessage());
            throw new CustomDatabaseException("Error querying supervisor :"+ e.getMessage(),e);
            
        }
    }

    public List<Supervisor> findByWorker(Long worker) {
        try {
            String sql = "SELECT * FROM supervisors WHERE worker = ?";
            List<Supervisor> supervisors = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Supervisor.class), worker);
           
            return supervisors;
        } catch (DataAccessException e) {
            System.err.println("Error querying worker : " + e.getMessage());
            throw new CustomDatabaseException("Error querying worker :"+ e.getMessage(),e);
           
        }
    }

    public void save(Supervisor supervisor) {
        try {
                String sql = "INSERT INTO supervisors (worker,supervisor) VALUES (?, ?)";
                jdbcTemplate.update(sql, supervisor.getWorker(),supervisor.getSupervisor());
            
        } catch (DataAccessException e) {
            System.err.println("Error saving Supervisor: " + e.getMessage());
            throw new CustomDatabaseException("Error saving Supervisor...",e);
        }
    }



    public Long count(){
        try {
            String sql="select count(*) from supervisors";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            
            System.err.println("Error querying no. of worker-supervisors: " + e.getMessage());
            
            throw new CustomDatabaseException("Error querying number of worker-supervisors",e);
        
        }
    }
    public void deleteById(Long worker, Long supervisor) {
        try {
            String sql = "DELETE FROM supervisors WHERE worker = ? and supervisor=?";
            jdbcTemplate.update(sql, worker, supervisor);
        } catch (DataAccessException e) {
            System.err.println("Error deleting Supervisor: " + e.getMessage());
            throw new CustomDatabaseException("Error deleting Supervisor ",e);
        }
    }
}
