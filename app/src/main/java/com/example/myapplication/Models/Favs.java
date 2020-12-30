package com.example.myapplication.Models;

public class Favs {
    int id;
    int item_id;
    int products;
    int shops;

    String product_name;
    String shop_name;
    String price;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Favs(int id,int item_id, int products, int shops, String product_name, String shop_name, String price) {
        this.id = id;
        this.item_id = item_id;
        this.products = products;
        this.shops = shops;
        this.product_name = product_name;
        this.shop_name = shop_name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProducts() {
        return products;
    }

    public void setProducts(int products) {
        this.products = products;
    }

    public int getShops() {
        return shops;
    }

    public void setShops(int shops) {
        this.shops = shops;
    }
}
