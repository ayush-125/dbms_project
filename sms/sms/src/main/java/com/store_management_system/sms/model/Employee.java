package com.store_management_system.sms.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


public class Employee {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String houseNo;
    private String city;
    private String state;
    private Long pincode;
    private String designation;
    private Long phoneNo;
    private String emailId;
    private Long storeId;
    private LocalDate dob;
    private String sex;
    private Double salary;
    private List<User>users;



    
    public Double getSalary() {
        return salary;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    public boolean isEmpty(){
        return (id==null && sex==null && storeId==null
            && (firstName==null || firstName.isEmpty()) && (middleName==null || middleName.isEmpty())
            && (lastName==null || lastName.isEmpty()) && (houseNo==null || houseNo.isEmpty()) && (city==null || city.isEmpty())
            && (state==null || state.isEmpty()) && (pincode==null ) && (designation==null || designation.isEmpty())
            && (phoneNo==null ) && (emailId==null || emailId.isEmpty()) && (dob==null )
            &&(salary==null)
        );
    }
    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    public Long getAge() {
        if(this.dob!=null){
            return (long)Period.between(this.dob,LocalDate.now()).getYears();
        }
        return (long)0;
    }
    
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getHouseNo() {
        return houseNo;
    }
    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Long getPincode() {
        return pincode;
    }
    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public Long getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }
    public Long getStoreId() {
        return storeId;
    }
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
