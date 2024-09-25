package com.store_management_system.sms.repository;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.Role;
// import com.store_management_system.sms.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;

// import java.util.Collections;
import java.util.List;

@Repository
public class RoleRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Role findById(Long id) {
        try {
            String sql = "SELECT * FROM roles WHERE id = ?";
            List<Role> roles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Role.class), id);
            return roles.isEmpty() ? null : roles.get(0);
        } catch (DataAccessException e) {
            System.err.println("Error querying role by id: " + e.getMessage());
            throw new CustomDatabaseException("Error querying role by id:"+id,e); 
            // return null;
        }
    }

    public Role findByName(String name) {
        try {
            String sql = "SELECT * FROM roles WHERE name = ?";
            List<Role> roles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Role.class), name);
            return roles.isEmpty() ? null : roles.get(0);
        } catch (DataAccessException e) {
            System.err.println("Error querying role by name: " + e.getMessage());
            throw new CustomDatabaseException("Error querying role by name:"+name,e); 
            // return null;
        }
    }

    public void save(Role role){
        try {
            if(role.getId()==null){
                String sql="insert into roles where name=?";
                jdbcTemplate.update(sql, role.getName());
            }else{
                String sql="update roles set name=? where id=?";
                jdbcTemplate.update(sql, role.getName(),role.getId());
            }
            
        } catch (DataAccessException e) {
            System.err.println("Error saving role: " + e.getMessage());
            throw new CustomDatabaseException("Error saving or updating role...",e); 
            
        }
    }

    
}
