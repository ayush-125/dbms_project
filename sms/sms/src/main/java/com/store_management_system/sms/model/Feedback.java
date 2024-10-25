package com.store_management_system.sms.model;

import java.time.LocalDate;

public class Feedback {
    // private Long id;
    private LocalDate fdate;
    private Double rating;
    private String comments;
    private Long orderId;

    public boolean isEmpty(){
        return ( fdate==null && rating==null && orderId==null&&(comments==null ||comments.isEmpty()));
    }

    // public Long getId() {
    //     return id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    public LocalDate getFdate() {
        return fdate;
    }

    public void setFdate(LocalDate fdate) {
        this.fdate = fdate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    
}
