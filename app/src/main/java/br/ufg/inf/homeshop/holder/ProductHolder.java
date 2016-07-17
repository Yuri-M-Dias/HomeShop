package br.ufg.inf.homeshop.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufg.inf.homeshop.R;

/**
 * Created by gabri_000 on 16/07/2016.
 */
public class ProductHolder extends RecyclerView.ViewHolder{
    public CardView cv;
    public ImageView photo;
    public TextView name;
    public TextView price;
    public ImageButton imageButton;

    public ProductHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardview);
        photo = (ImageView) itemView.findViewById(R.id.product_img);
        imageButton = (ImageButton) itemView.findViewById(R.id.product_add);
        name = (TextView) itemView.findViewById(R.id.product_name);
        price = (TextView) itemView.findViewById(R.id.product_price);
    }
}
