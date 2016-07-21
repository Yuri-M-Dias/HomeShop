package br.ufg.inf.homeshop.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.Product;
import br.ufg.inf.homeshop.services.WebTaskProductDetail;

public class DetailActivity extends AppCompatActivity {

    private Long productId = null;
    private Long marketId = null;
    private Product product;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        productId = intent.getLongExtra("productId", -1);
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
        marketId = settings.getLong("marketId", -1);
        Toast.makeText(this, "Produto: " + productId, Toast.LENGTH_LONG).show();
        showProgress(true);
        WebTaskProductDetail taskProductDetail = new WebTaskProductDetail(this,
            String.valueOf(marketId), String.valueOf(productId));
        taskProductDetail.execute();
    }

    private void initView(){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_product_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
            (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(Color.BLACK);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbar.setTitle(product.getName());
        TextView placeDetail = (TextView) findViewById(R.id.product_detail);
        placeDetail.setText(product.getDescription());
        TextView placeLocation = (TextView) findViewById(R.id.product_price);
        placeLocation.setText(product.getDescription());
        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        //TODO: pegar a imagem!
        placePicutre.setImageResource(R.drawable.jesus);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(Product product) {
        this.product = product;
        showProgress(false);
        initView();
    }

    @Subscribe
    public void onEvent(Error error) {
        showProgress(false);
        Log.e("detailActivity", "Error while fetching results.", error);
        Toast.makeText(this, "Erro ocorreu. Verifique internet.", Toast.LENGTH_LONG).show();
    }

    private void showProgress(boolean showProgress) {
        /*
        progressView.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        appBar.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        viewPager.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        */
    }

}
