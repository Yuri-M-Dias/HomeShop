package br.ufg.inf.homeshop.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.activity.DetailActivity;

public class ProductDetailHolder extends RecyclerView.ViewHolder {

    public ProductDetailHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.activity_product_detail, parent, false));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
