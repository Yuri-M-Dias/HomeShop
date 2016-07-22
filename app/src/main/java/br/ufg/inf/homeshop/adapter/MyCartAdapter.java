package br.ufg.inf.homeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.holder.MyCartProductHolder;
import br.ufg.inf.homeshop.model.CartItem;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartProductHolder> {

    private List<CartItem> myProductList;
    private Context mContext;

    public MyCartAdapter(List<CartItem> myProductList, Context mContext) {
        this.myProductList = myProductList;
        this.mContext = mContext;
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
        CartItem cartItem = myProductList.get(i);
        myCartProductHolder.name.setText(cartItem.getProduct().getDescription());
        myCartProductHolder.price.setText(String.format("R$%s", cartItem.getProduct().getPrice()));
        Picasso.with(mContext)
            .load(cartItem.getProduct().getImage())
            .placeholder(R.drawable.icon_loading)
            .error(R.drawable.icon_error)
            .into(myCartProductHolder.photo);
        myCartProductHolder.quantity.setValue(cartItem.getQuantity());
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
