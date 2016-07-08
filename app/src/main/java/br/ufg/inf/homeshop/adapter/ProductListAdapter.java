package br.ufg.inf.homeshop.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.Product;

/**
 * Adapter to use the RecyclerView and be able to dynamically use a number of
 * cardviews.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductHolder> {

    public static class ProductHolder extends RecyclerView.ViewHolder {

        CardView cv;
        ImageView photo;
        TextView name;
        TextView price;
        ImageButton imageButton;

        public ProductHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardview);
            photo = (ImageView) itemView.findViewById(R.id.product_img);
            imageButton = (ImageButton) itemView.findViewById(R.id.product_add);
            name = (TextView) itemView.findViewById(R.id.product_name);
            price = (TextView) itemView.findViewById(R.id.product_price);
        }

    }

    List<Product> products;

    public ProductListAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
            .item_product, viewGroup, false);
        ProductHolder pvh = new ProductHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductHolder productsHolder, int i) {
        productsHolder.name.setText(products.get(i).getDescription());
        productsHolder.price.setText("R$"+ products.get(i).getPrice());
        productsHolder.photo.setImageResource(R.drawable.jesus);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

