package br.ufg.inf.homeshop.adapter;

import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.holder.MyCartProductHolder;
import br.ufg.inf.homeshop.holder.ProductListHolder;
import br.ufg.inf.homeshop.model.CartItem;
import br.ufg.inf.homeshop.model.Product;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartProductHolder> {

    List<CartItem> myProductList;

    public MyCartAdapter(List<CartItem> myProductList) {
        this.myProductList = myProductList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyCartProductHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
            .item_cart, viewGroup, false);
        MyCartProductHolder myCartProductHolder = new MyCartProductHolder(view);
        return myCartProductHolder;
    }

    @Override
    public void onBindViewHolder(MyCartProductHolder myCartProductHolder, int i) {
        myCartProductHolder.name.setText(myProductList.get(i).getProduct().getDescription());
        myCartProductHolder.price.setText("R$" + myProductList.get(i).getProduct().getPrice());
        myCartProductHolder.photo.setImageResource(R.drawable.jesus);
        myCartProductHolder.quantity.setValue(myProductList.get(i).getQuantity());

        myCartProductHolder.quantity.setMaxValue(100);
        myCartProductHolder.quantity.setMinValue(0);
        myCartProductHolder.quantity.setWrapSelectorWheel(false);
        myCartProductHolder.quantity.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
    }

    @Override
    public int getItemCount() {
        return myProductList.size();
    }

}
