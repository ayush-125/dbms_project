package com.store_management_system.sms.model;

import java.time.LocalDate;
import java.util.List;
public class Discount {
    private Long id;
    private String description;
    private LocalDate dos;
    private LocalDate doe;
    private Double rate;


    private List<Long> productIds;
    
    public boolean isEmpty(){
        return (id==null && (description==null || description.isEmpty())
            && dos==null && doe==null && rate==null
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDos() {
        return dos;
    }

    public void setDos(LocalDate dos) {
        this.dos = dos;
    }

    public LocalDate getDoe() {
        return doe;
    }

    public void setDoe(LocalDate doe) {
        this.doe = doe;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    
}
