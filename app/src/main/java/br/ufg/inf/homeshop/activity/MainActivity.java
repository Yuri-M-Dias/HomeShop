package br.ufg.inf.homeshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.adapter.ViewPagerAdapter;
import br.ufg.inf.homeshop.model.Market;
import br.ufg.inf.homeshop.services.WebTaskMarketDetail;

public class MainActivity extends AppCompatActivity {

    public static final String PREFERENCES_NAME = "HomeShopPrefs";

    private View progressView;
    private View appBar;
    private View viewPager;

    private long marketId;
    private Market market;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressView = findViewById(R.id.main_progress);
        appBar = findViewById(R.id.appbar);
        viewPager = findViewById(R.id.viewpager);
        SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
        marketId = settings.getLong("marketId", -1);
        if (marketId == -1) {
            //Não tem mercado setado, volta para a tela de seleção.
            Intent intent = new Intent(this, SupermarketActivity.class);
            startActivity(intent);
        }
        showProgress(true);
        WebTaskMarketDetail taskSupermarket = new WebTaskMarketDetail(this, String.valueOf(marketId));
        taskSupermarket.execute();
    }

    private void populateView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(market.getName());
        setSupportActionBar(toolbar);
        final ViewPager viewPagerFinal = (ViewPager) viewPager;
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 2);
        viewPagerFinal.setAdapter(adapter);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.setupWithViewPager(viewPagerFinal);
        //TODO: i18n
        tabs.getTabAt(0).setText("Produtos");
        tabs.getTabAt(1).setText("Meu Carrinho");
        viewPagerFinal.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerFinal.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ImageButton imageButton = (ImageButton) findViewById(R.id.market_image_button);
        Picasso.with(this)
            .load(market.getImage())
            .placeholder(R.drawable.icon_loading)
            .error(R.drawable.icon_error)
            .into(imageButton);
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
    public void onEvent(Market market) {
        this.market = market;
        showProgress(false);
        populateView();
    }

    @Subscribe
    public void onEvent(Error error) {
        showProgress(false);
        Log.e("mainActivity", "Error while fetching results.", error);
        Toast.makeText(this, "Erro ocorreu. Verifique internet.", Toast.LENGTH_LONG).show();
        //Flashes error
    }

    private void showProgress(boolean showProgress) {
        progressView.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        appBar.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        viewPager.setVisibility(showProgress ? View.GONE : View.VISIBLE);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
