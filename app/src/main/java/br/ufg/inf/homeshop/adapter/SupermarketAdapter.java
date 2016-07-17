package br.ufg.inf.homeshop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.Market;

public class SupermarketAdapter extends BaseAdapter {

    private Context mContext;

    private List<Market> superMarkets;

    // Constructor
    public SupermarketAdapter(Context c, List<Market> superMarkets) {
        this.superMarkets = superMarkets;
        mContext = c;
    }

    @Override
    public int getCount() {
        return superMarkets.size();
    }

    @Override
    public Object getItem(int position) {
        return superMarkets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Market currentMarket = superMarkets.get(position);
        ImageView imageView = new ImageView(mContext);
        Picasso.with(mContext)
            .load(currentMarket.getImage())
            .placeholder(R.drawable.icon_forward)
            .error(R.drawable.icon_forward)
            .resize(400, 400)
            .centerCrop()
            .into(imageView);
        //Width, Height
        GridView.LayoutParams params = new GridView
            .LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView view = (ImageView) v;
                //Pega qual supermercado clicou, e cria intent pro menu principal passando o id dele.
                System.out.println("Clicando no item: " + position);
            }
        });
        return imageView;
    }

}
