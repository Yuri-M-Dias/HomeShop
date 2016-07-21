package br.ufg.inf.homeshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.activity.DetailActivity;
import br.ufg.inf.homeshop.holder.ProductListHolder;
import br.ufg.inf.homeshop.model.Product;

/**
 * Adapter to use the RecyclerView and be able to dynamically use a number of
 * cardviews.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListHolder>{

    private Context mContext;
    private List<Product> products;

    public ProductListAdapter(List<Product> products, Context mContext) {
        this.products = products;
        this.mContext = mContext;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ProductListHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("homeshop", "Create View Holder");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
            .item_product, viewGroup, false);
        ProductListHolder productListHolder = new ProductListHolder(view);
        return productListHolder;
    }

    @Override
    public void onBindViewHolder(ProductListHolder productsHolder, final int i) {
        final Product product = products.get(i);
        productsHolder.name.setText(product.getDescription());
        productsHolder.price.setText("R$"+ product.getPrice());
        //TODO: imagem do mock
        productsHolder.photo.setImageResource(R.drawable.jesus);
        productsHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("productId", product.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}

