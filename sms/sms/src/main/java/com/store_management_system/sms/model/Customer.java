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

    // List of Orders
    private List<Order> orders;

    // List of Customer Emails
    private List<CustomerMail> emails;

    // List of Customer Payments
    private List<CustomerPayment> payments;

    // Check if the customer object is empty
    public boolean isEmpty() {
        return (id == null && pincode == null && dob == null)
                && (firstName == null || firstName.isEmpty())
                && (middleName == null || middleName.isEmpty())
                && (lastName == null || lastName.isEmpty())
                && (phoneNo == null)
                && (houseNo == null || houseNo.isEmpty())
                && (city == null || city.isEmpty())
                && (state == null || state.isEmpty())
                && account == null
                && (sex == null || sex.isEmpty());
    }

    // Calculate and return age based on the date of birth
    public Long getAge() {
        if (dob != null) {
            return (long) Period.between(dob, LocalDate.now()).getYears();
        } else {
            return (long) 0;
        }
    }

    // Getter and Setter for Orders
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // Getter and Setter for Account Balance
    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    // Getter and Setter for Emails
    public List<CustomerMail> getEmails() {
        return emails;
    }

    public void setEmails(List<CustomerMail> emails) {
        this.emails = emails;
    }

    // Getter and Setter for Payments
    public List<CustomerPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<CustomerPayment> payments) {
        this.payments = payments;
    }

    // Getter and Setter for ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for First Name
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for Middle Name
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    // Getter and Setter for Last Name
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter for Phone Number
    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    // Getter and Setter for House Number
    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    // Getter and Setter for City
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // Getter and Setter for State
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // Getter and Setter for Pincode
    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    // Getter and Setter for Sex
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    // Getter and Setter for Date of Birth
    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
