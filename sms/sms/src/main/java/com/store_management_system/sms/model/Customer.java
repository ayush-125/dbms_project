package com.store_management_system.sms.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
public class Customer {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Long phoneNo;
    private String houseNo;
    private String city;
    private String state;
    private Long pincode;
    private String sex;
    private LocalDate dob;

    private Double account;

    private List<Order>orders;
    private List<CustomerMail>emails;
    public boolean isEmpty(){
        return ((id==null && pincode==null && dob==null)
                &&(firstName==null || firstName.isEmpty())
                &&(middleName==null || middleName.isEmpty())
                &&(lastName==null || lastName.isEmpty())
                &&(phoneNo==null )
                &&(houseNo==null || houseNo.isEmpty())
                && (city==null || city.isEmpty())
                &&(state==null || state.isEmpty())&& account==null
                &&(sex==null || sex.isEmpty())           
        );
    }
    public Long getAge(){
        if(dob!=null){
           return (long)Period.between(dob, LocalDate.now()).getYears();
        }else{
            return (long)0;
        }
        
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Double getAccount() {
        return account;
    }
    public void setAccount(Double account) {
        this.account = account;
    }
    public List<CustomerMail> getEmails() {
        return emails;
    }
    public void setEmails(List<CustomerMail> emails) {
        this.emails = emails;
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

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    


}
