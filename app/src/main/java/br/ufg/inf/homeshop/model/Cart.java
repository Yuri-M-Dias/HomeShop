package br.ufg.inf.homeshop.model;

import java.util.List;

public class Cart {

    private List<CartItem> cartItens;
    private Double total;

    public Cart(List<CartItem> cartItens, Double total) {
        this.cartItens = cartItens;
        this.total = total;
    }

    public List<CartItem> getCartItens() {
        return cartItens;
    }

    public void setCartItens(List<CartItem> cartItens) {
        this.cartItens = cartItens;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
