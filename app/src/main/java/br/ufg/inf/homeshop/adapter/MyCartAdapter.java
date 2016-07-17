package br.ufg.inf.homeshop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.holder.ProductHolder;
import br.ufg.inf.homeshop.model.Product;

/**
 * Created by gabri_000 on 17/07/2016.
 */
public class MyCartAdapter extends RecyclerView.Adapter<ProductHolder>{

    List<Product> products;

    public MyCartAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
            .item_product, viewGroup, false);
        ProductHolder productHolder = new ProductHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(ProductHolder productsHolder, int i) {
        productsHolder.name.setText(products.get(i).getDescription());
        productsHolder.price.setText("R$"+ products.get(i).getPrice());
        productsHolder.photo.setImageResource(R.drawable.jesus);
        productsHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
