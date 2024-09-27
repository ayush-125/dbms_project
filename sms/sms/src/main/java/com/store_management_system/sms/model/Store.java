package com.store_management_system.sms.model;

import java.util.List;

public class Store {
    private Long id;
    private String name;
    private String street;
    private String city;
    private String state;
    private Long pincode;
    private String emailId;
    private Long phoneNo;
    private Long managerId ;
    private List<Employee> employees;
    private List<Product> products;

    public List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    public boolean isEmpty(){
        return (id==null && managerId==null && (name==null || name.isEmpty())
         && (street==null || street.isEmpty()) && (city==null || city.isEmpty())
         && (state==null || state.isEmpty()) && (pincode==null )
         && (emailId==null || emailId.isEmpty()) && (phoneNo==null )
         );
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getStreet(){
        return street;
    }
    public void setStreet(String street){
        this.street=street;
    }
    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city=city;
    }
    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state=state;
    }
    public Long getPincode(){
        return pincode;
    }
    public void setPincode(Long pincode){
        this.pincode=pincode;
    }
    public String getEmailId(){
        return emailId;
    }
    public void setEmailId(String emailId){
        this.emailId=emailId;
    }
    public Long getPhoneNo(){
        return phoneNo;
    }
    public void setPhoneNo(Long phoneNo){
        this.phoneNo=phoneNo;
    }
    public Long getManagerId(){
        return managerId;
    }
    public void setManagerId(Long managerId){
        this.managerId=managerId;
    }
}
