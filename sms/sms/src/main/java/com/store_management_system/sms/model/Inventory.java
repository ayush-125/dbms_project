package com.store_management_system.sms.model;



public class Inventory {
    // private Long id;
    private Long productId;
    private Long storeId;
    private Long quantity;

    public boolean isEmpty(){
        return( productId==null && storeId==null && storeId==null && quantity==null);

    }

    // // public Long getId() {
    // //     return id;
    // // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    
}
