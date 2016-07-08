package br.ufg.inf.homeshop.model;

import android.location.Location;

public class Market {

    private Long id;
    private String name;
    private String header;
    private Location location;

    public Market(Long id, String name, String header, Location location) {
        this.id = id;
        this.name = name;
        this.header = header;
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
