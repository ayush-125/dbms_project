package com.store_management_system.sms.repository;

import com.store_management_system.sms.model.User;
import com.store_management_system.sms.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import com.store_management_system.sms.exception.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> findAll() {
        try {
            String sql = "SELECT * FROM users";
            List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
            for (User user : users) {
                Role role = roleRepository.findById(user.getRoleId());
                user.setRoles(Collections.singletonList(role)); // Set the roles in the user object
            }
            return users;
        } catch (DataAccessException e) {
            System.err.println("Error querying users: " + e.getMessage());
            throw new CustomDatabaseException("Error querying all the users",e); 
            // return Collections.emptyList();
            
        }
    }
    public List<User> findAllAdmin() {
        try {
            String sql = "SELECT * FROM users where roleId=1";
            List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
            for (User user : users) {
                Role role = roleRepository.findById(user.getRoleId());
                user.setRoles(Collections.singletonList(role)); // Set the roles in the user object
            }
            return users;
        } catch (DataAccessException e) {
            System.err.println("Error querying users: " + e.getMessage());
            throw new CustomDatabaseException("Error querying all the users",e); 
            // return Collections.emptyList();
            
        }
    }

    public List<User> findByEmployeeId(Long employeeId) {
        try {
            String sql = "SELECT * FROM users WHERE employeeId = ?";
            List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), employeeId);
            for (User user : users) {
                Role role = roleRepository.findById(user.getRoleId());
                user.setRoles(Collections.singletonList(role)); // Set the roles in the user object
            }
            return users;
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("Error querying users with given Employee ID :"+employeeId + e.getMessage());
            throw new CustomDatabaseException("Error querying  users with given Employee ID:"+employeeId,e);

        }
        
    }

    public Long getStoreIdByUsername(String id){
        try {
            String sql="(select employees.storeId from users,employees where users.employeeId=employees.id and users.username=?)";
            
            List<Long> storeIds = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("storeId"), id);
            // Assuming you expect only one result
            Long storeId = storeIds.isEmpty() ? null : storeIds.get(0);
            return storeId;
        } catch (DataAccessException e) {
            // TODO: handle exception
            throw new CustomDatabaseException(e.getMessage(), e);
        }
    }

    public User findByUsername(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), username);
            if (users.isEmpty()) {
                return null;
            }
            User user = users.get(0);
            Role role = roleRepository.findById(user.getRoleId());
            user.setRoles(Collections.singletonList(role)); // Set the roles in the user object
            return user;
        } catch (DataAccessException e) {
            System.err.println("Error querying user by username: " + e.getMessage());
            throw new CustomDatabaseException("Error querying user by username:"+username,e); 
            // return null;
        }
    }
    // public User findById(Long id) {
    //     try {
    //         String sql = "SELECT * FROM users WHERE id = ?";
    //         List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), id);
    //         if (users.isEmpty()) {
    //             return null;
    //         }
    //         User user = users.get(0);
    //         Role role = roleRepository.findById(user.getRoleId());
    //         user.setRoles(Collections.singletonList(role)); // Set the roles in the user object
    //         return user;
    //     } catch (DataAccessException e) {
    //         System.err.println("Error querying user by id: " + e.getMessage());
    //         throw new CustomDatabaseException("Error querying user by id:"+id,e); 
            
    //         // return null;
    //     }
    // }
    public Long countUsers(){
        try {
            String sql="select count(*) from users";
            return jdbcTemplate.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            
            System.err.println("Error querying no. of users: " + e.getMessage());
            throw new CustomDatabaseException("Error querying number of users",e); 
            
            // return null;
        }
    }
    public void save(User user) {
        try {
            if(user.getRoleId()==null){
                user.setRoleId((long)3);
            }
            User chk=findByUsername(user.getUsername());
            if (chk == null) {
                String sql = "INSERT INTO users (username, password, employeeId, roleId) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmployeeId(), user.getRoleId());
            } else {
                String sql = "UPDATE users SET  password = ?, employeeId = ?, roleId = ? WHERE username = ?";
                jdbcTemplate.update(sql,  user.getPassword(), user.getEmployeeId(), user.getRoleId(), user.getUsername());
            }
        } catch (DataAccessException   e) {
            System.err.println("Error saving user: " + e.getMessage());
            throw new RuntimeException("Error saving or updating user...",e); 
            
        }
    }

    public void deleteByUsername(String id) {
        try {
            String sql = "DELETE FROM users WHERE username = ?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            throw new CustomDatabaseException("Error deleting user with id:"+id,e); 
            
        }
    }

    //find users belonging to the same store
    public List<User> findUsersWithSameStoreByUsername(String id){
        try{String sql="Select users.username as username,users.password as password,users.roleId as roleId,users.employeeId as employeeId"
        +" from users,employees where users.employeeId=employees.id and employees.storeId in("
        +" select employees.storeId from users,employees where users.employeeId=employees.id and users.username=?"
        +")";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), id);
        for (User user : users) {
            Role role = roleRepository.findById(user.getRoleId());
            user.setRoles(Collections.singletonList(role)); // Set the roles in the user object
        }
        
        return users;
    }catch(DataAccessException e){
        System.err.println("Error querying users: " + e.getMessage());
        throw new CustomDatabaseException("Error querying all the users in the same store with user id:"+id,e); 
        // return Collections.emptyList();
    }
    }
}
