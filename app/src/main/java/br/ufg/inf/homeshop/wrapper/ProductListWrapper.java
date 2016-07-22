package br.ufg.inf.homeshop.wrapper;

import java.util.List;

import br.ufg.inf.homeshop.model.Product;

public class ProductListWrapper {

    public List<Product> products;

    public ProductListWrapper(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
