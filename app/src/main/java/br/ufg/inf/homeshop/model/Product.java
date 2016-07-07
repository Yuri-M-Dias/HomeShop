package br.ufg.inf.homeshop.model;

public class Product {

    private String description;
    private Double price;
    private String imageUrl;

    public Product(String description, Double price, String imageUrl) {
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
