package com.magi.imoocrestaurant.bean;

public class ProductModel extends Product {
    private int count;

    public ProductModel(Product product){
        count = 0;
        this.id = product.getId();
        this.name = product.getName();
        this.label = product.getLabel();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.icon = product.getIcon();
        this.restaurant = product.getRestaurant();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
