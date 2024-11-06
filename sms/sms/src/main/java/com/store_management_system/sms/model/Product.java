package com.store_management_system.sms.model;
import java.util.List;
public class Product {
    private Long id;
    private String name;
    private String category;
    private String subcategory;
    private String brand;
    private String size;
    private String color;
    private Double price;

    private List<Inventory> inventories;
    private List<Discount> discount;
    public List<Discount> getDiscount() {
        return discount;
    }
    public void setDiscount(List<Discount> discount) {
        this.discount = discount;
    }
    public boolean isEmpty(){
       return( (id==null && (name==null || name.isEmpty())
                &&(category==null || category.isEmpty())
                &&(subcategory==null || subcategory.isEmpty())
                &&(brand==null || brand.isEmpty())
                &&(size==null || size.isEmpty())
                &&(color==null || color.isEmpty())
                &&(price==null)
                ));
                

    }
    public List<Inventory> getInventories() {
        return inventories;
    }
    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
