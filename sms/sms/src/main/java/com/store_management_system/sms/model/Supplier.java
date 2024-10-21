package com.store_management_system.sms.model;
import java.util.List;

public class Supplier {
    private Long id;
    private String name;
    private Long phoneNo;
    private String street;
    private String state;
    private String city;
    private Long pincode;

    private Double account;
    private List<Buy>buys;

    private List<SupplierMail>emails;
    private List<SupplierPayment> payments;

    public List<SupplierPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<SupplierPayment> payments) {
        this.payments = payments;
    }
    public List<SupplierMail> getEmails() {
        return emails;
    }
    public void setEmails(List<SupplierMail> emails) {
        this.emails = emails;
    }
    public List<Buy> getBuys() {
        return buys;
    }
    public void setBuys(List<Buy> buys) {
        this.buys = buys;
    }
    public boolean isEmpty(){
        return ((name==null || name.isEmpty())
            &&(phoneNo==null )
            && (street==null || street.isEmpty())
            &&(state==null || state.isEmpty())
            &&(city==null || city.isEmpty())
            &&(pincode==null && id==null) && account==null
        );
    }
    public Double getAccount() {
        return account;
    }
    public void setAccount(Double account) {
        this.account = account;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }
    
}
