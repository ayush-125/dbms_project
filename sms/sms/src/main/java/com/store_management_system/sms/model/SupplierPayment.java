package com.store_management_system.sms.model;

import java.time.LocalDate;

public class SupplierPayment {
    private Long id;
    
    // This represents the foreign key relationship to Supplier
    private Long supplierId; // foreign key reference to supplier

    private Double paymentAmount;
    private LocalDate paymentDate;

    // Constructor
    public SupplierPayment(Long id, Long supplierId, Double paymentAmount, LocalDate paymentDate) {
        this.id = id;
        this.supplierId = supplierId;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
    }

    // Default Constructor
    public SupplierPayment() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Represent the foreign key relation to Supplier
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
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
        return "Payment ID: " + id + ", Supplier ID: " + supplierId + ", Amount: " + paymentAmount + ", Date: " + paymentDate;
    }
}
