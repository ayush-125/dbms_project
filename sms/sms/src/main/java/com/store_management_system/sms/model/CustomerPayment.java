package com.store_management_system.sms.model;

import java.time.LocalDate;

public class CustomerPayment {
    private Long id;
    
    // This represents the foreign key relationship to Customer
    private Long customerId; // foreign key reference to Customer

    private Double paymentAmount;
    private LocalDate paymentDate;

    // Constructor
    public CustomerPayment(Long id, Long customerId, Double paymentAmount, LocalDate paymentDate) {
        this.id = id;
        this.customerId = customerId;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
    }

    // Default Constructor
    public CustomerPayment() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Represent the foreign key relation to Customer
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    // Optional: You can add a method to format payment details for easy access
    public String getFormattedPaymentDetails() {
        return "Payment ID: " + id + ", Customer ID: " + customerId + ", Amount: " + paymentAmount + ", Date: " + paymentDate;
    }
}
