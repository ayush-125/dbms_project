package com.store_management_system.sms.model;

public class ProductDiscount {
    private Long productId;
    private Long discountId;

    public boolean isEmpty(){
        return (productId==null && discountId==null);
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    
}
