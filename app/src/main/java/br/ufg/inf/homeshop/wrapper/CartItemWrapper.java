package br.ufg.inf.homeshop.wrapper;

import java.util.List;

import br.ufg.inf.homeshop.model.CartItem;

public class CartItemWrapper {

    private List<CartItem> cartItemList;

    public CartItemWrapper(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

}
