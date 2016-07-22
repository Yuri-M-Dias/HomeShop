package br.ufg.inf.homeshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.Product;
import br.ufg.inf.homeshop.services.WebTaskProductDetail;

public class DetailActivity extends AppCompatActivity {

    private Long productId = null;
    private Long marketId = null;
    private Product product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        productId = intent.getLongExtra("productId", -1);
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
        marketId = settings.getLong("marketId", -1);
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
        TextView productDetail = (TextView) findViewById(R.id.product_detail);
        productDetail.setText(product.getDescription());
        TextView productPrice = (TextView) findViewById(R.id.product_price);
        productPrice.setText(String.valueOf(product.getPrice()));
        ImageView productPicture = (ImageView) findViewById(R.id.image);
        Picasso.with(this)
            .load(product.getImage())
            .placeholder(R.drawable.icon_loading)
            .error(R.drawable.icon_error)
            .into(productPicture);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void showProgress(boolean showProgress) {
        /*
        progressView.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        appBar.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        viewPager.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        */
    }

    public void adicionaCarrinho(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("openCart", true);
        finish();
        startActivity(intent);
    }
}
