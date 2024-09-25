package com.store_management_system.sms.model;

import java.time.LocalDate;


public class Buy {
    private Long id;
    private LocalDate dop;
    private  Double price;
    private Long quantity;
    private Long supplierId;
    private Long inventoryId;
    private String paymentMethod;
    private Double payment;

    private Double totalAmount;

    public boolean isEmpty(){
        return ((dop==null && id==null && price==null && quantity==null)
                &&(supplierId==null && inventoryId==null)
        );
    }
    public Double getPayment() {
        return payment;
    }
    public void setPayment(Double payment) {
        this.payment = payment;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDop() {
        return dop;
    }

    public void setDop(LocalDate dop) {
        this.dop = dop;
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

    

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Double getTotalAmount() {
        if(this.price!=null && this.quantity!=null){
            this.totalAmount= this.price*this.quantity;
        }else{
            this.totalAmount=(double)0;
        }
        
        return this.totalAmount;
    }

    // public void setTotalAmount(Double totalAmount) {
    //     this.totalAmount = totalAmount;
    // }

    
}
