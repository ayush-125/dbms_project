package com.store_management_system.sms.model;

import java.time.LocalDate;

public class Order{
    private Long id;
    private Double price;
    private Long quantity;
    
    private Long inventoryId;
    
    private LocalDate odate;
    private String houseNo;
    private String street;
    private String city;
    private String state;
    private Long pincode;
    private String paymentMethod;
    private Double payment;
    private Long employeeId;
    private Long customerId;
    
    private Long returnId;
    private Long feedbackId;
    private Double totalAmount;
    public boolean isEmpty(){
        return ((id==null && price==null && quantity==null && inventoryId==null)
            &&(odate==null && (houseNo==null || houseNo.isEmpty()) )
            &&(street==null || street.isEmpty())
            &&(city==null || city.isEmpty())
            &&(state==null|| state.isEmpty())
            &&(pincode==null && payment==null && employeeId==null && customerId==null) 
            &&(paymentMethod==null || paymentMethod.isEmpty())
        );
    }
    
    public Long getReturnId() {
        return returnId;
    }

    public void setReturnId(Long returnId) {
        this.returnId = returnId;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public LocalDate getOdate() {
        return odate;
    }

    public void setOdate(LocalDate odate) {
        this.odate = odate;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Double getTotalAmount() {
        if(price !=null && quantity!=null){
            totalAmount=price*quantity;
        }
        totalAmount=(double)0;
        return totalAmount;
    }
    
}
