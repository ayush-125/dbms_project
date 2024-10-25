package com.store_management_system.sms.model;


import java.util.List;

public class User {
    // private Long id;
    private String username;
    private String password;
    private Long roleId;
    private Long employeeId;

    private transient List<Role>roles;
    public boolean isEmpty(){
        return (  roleId==null && employeeId==null &&(username==null || username.isEmpty()) &&( password==null || password.isEmpty()));
    }
    // public Long getId(){
    //     return id;
    // }
    // public void setId(Long id){
    //     this.id=id;
    // }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public Long getRoleId(){
        return roleId;
    }
    public void setRoleId(Long roleId){
        this.roleId=roleId;
    }
    public Long getEmployeeId(){
        return employeeId;
    }
    public void setEmployeeId(Long employeeId){
        this.employeeId=employeeId;
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
}
