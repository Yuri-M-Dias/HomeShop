package br.ufg.inf.homeshop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import br.ufg.inf.homeshop.R;

/**
 * Created by gabri_000 on 07/07/2016.
 */
public class SupermarketAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
        R.drawable.extralogo, R.drawable.carrefourlogo,
        R.drawable.bretaslogo, R.drawable.liderlogo,
        R.drawable.wallmartlogo, R.drawable.maxxilogo,
        R.drawable.moreriralogo, R.drawable.novosucessologo,
        R.drawable.paodeacucarlogo, R.drawable.povologo,
        R.drawable.taticologo, R.drawable.tododialogo
    };

    // Constructor
    public SupermarketAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(550, 550));
        //ViewGroup.LayoutParams source = new ViewGroup.LayoutParams();

        return imageView;
    }


}
