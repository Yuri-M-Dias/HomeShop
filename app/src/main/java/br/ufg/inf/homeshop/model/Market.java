package br.ufg.inf.homeshop.model;

import android.location.Location;

public class Market {

    private Long id;
    private String name;
    private String image;
    private Location location;
    private String products;

    public Market() {
    }

    public Market(Long id, String image) {
        this.id = id;
        this.image = image;
    }

    public Market(Long id, String name, String image, Location location) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.location = location;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

}
