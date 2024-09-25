package com.store_management_system.sms.model;

public class SupplierMail {
    private Long supplierId;
    private String supplierEmail;

    public boolean isEmpty(){
        return supplierId==null &&(supplierEmail==null || supplierEmail.isEmpty());
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }
    
}
