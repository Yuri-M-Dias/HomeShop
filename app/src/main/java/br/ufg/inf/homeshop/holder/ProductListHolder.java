package br.ufg.inf.homeshop.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.activity.DetailActivity;

public class ProductListHolder extends RecyclerView.ViewHolder {
    public CardView cv;
    public ImageView photo;
    public TextView name;
    public TextView price;
    public ImageButton imageButton;

    public ProductListHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardview);
        photo = (ImageView) itemView.findViewById(R.id.product_img);
        imageButton = (ImageButton) itemView.findViewById(R.id.product_add);
        name = (TextView) itemView.findViewById(R.id.product_name);
        price = (TextView) itemView.findViewById(R.id.product_price);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
