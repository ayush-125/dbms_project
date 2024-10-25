package com.store_management_system.sms.model;
import java.time.LocalDate;
public class Return {
    // private Long id;
    private LocalDate rdate;
    private String reason;
    private Long quantity;
    private Double price;
    private Long orderId;

    private Double totalAmount;
    public Double getTotalAmount(){
        if(price!=null && quantity!=null){
            totalAmount=price*quantity;
        }else{
            totalAmount=(double)0;
        }
        return totalAmount;
    }
    public boolean isEmpty(){
        return ( rdate==null && quantity==null && price==null && orderId==null &&(reason==null || reason.isEmpty()));
    }

    // public Long getId() {
    //     return id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    public LocalDate getRdate() {
        return rdate;
    }

    public void setRdate(LocalDate rdate) {
        this.rdate = rdate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
}
