package br.ufg.inf.homeshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.activity.MainActivity;
import br.ufg.inf.homeshop.holder.MarketHolder;
import br.ufg.inf.homeshop.model.Market;

public class SupermarketAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<Market> superMarkets;

    // Constructor
    public SupermarketAdapter(Context c, List<Market> superMarkets) {
        this.superMarkets = superMarkets;
        mContext = c;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        convertView = inflater.inflate(R.layout.market_item_tile, parent, false);
        MarketHolder marketHolder = new MarketHolder(convertView);
        marketHolder.picture = (ImageView) convertView.findViewById(R.id.tile_picture);
        final Market currentMarket = superMarkets.get(position);
        ImageView imageView = marketHolder.picture;
        Picasso.with(mContext)
            .load(currentMarket.getImage())
            .placeholder(R.drawable.icon_loading)
            .error(R.drawable.icon_error)
            .resize(400, 400)
            .centerInside()
            .into(imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "Clicando no item: " + position, Toast.LENGTH_LONG).show();
                SharedPreferences settings = mContext.getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putLong("marketId", currentMarket.getId());
                editor.apply();
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

}
