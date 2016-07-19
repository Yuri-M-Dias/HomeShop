package br.ufg.inf.homeshop.activity;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.Product;

public class DetailActivity extends AppCompatActivity {

    public String productId = "";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_product_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
            (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("JESUS");

        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText("");

        TextView placeLocation = (TextView) findViewById(R.id.place_location);
        placeLocation.setText("");

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        placePicutre.setImageDrawable(getDrawable(R.drawable.jesus));


    }
}
