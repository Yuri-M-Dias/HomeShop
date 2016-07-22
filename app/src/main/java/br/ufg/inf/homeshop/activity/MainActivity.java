package br.ufg.inf.homeshop.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
    private boolean openCart;

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
            //Não tem supermercado setado, volta para a tela de seleção.
            finish();
        }
        Intent intent = getIntent();
        openCart = intent.getBooleanExtra("openCart", false);
        showProgress(true);
        WebTaskMarketDetail taskSupermarket = new WebTaskMarketDetail(this, String.valueOf(marketId));
        taskSupermarket.execute();
    }

    private void populateView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(market.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ViewPager viewPagerFinal = (ViewPager) viewPager;
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 2);
        viewPagerFinal.setAdapter(adapter);
        TabLayout tabs = createTabs(viewPagerFinal);
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
        if (openCart) {
            tabs.setScrollPosition(1, 0F, true);
            viewPagerFinal.setCurrentItem(1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
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
    }

    private void showProgress(boolean showProgress) {
        progressView.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        appBar.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        viewPager.setVisibility(showProgress ? View.GONE : View.VISIBLE);
    }

    public void readCode(View view) {
        Intent intent = new Intent(this, QRCodeActivity.class);
        startActivity(intent);
    }

    public void abreMaps(View view) {
        market.getLocation();
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitude", market.getLocation().latitude);
        intent.putExtra("longitude", market.getLocation().longitude);
        intent.putExtra("nome", market.getName());
        startActivity(intent);
    }

    private TabLayout createTabs(ViewPager viewPager) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Produtos");
        tabLayout.getTabAt(1).setText("Meu Carrinho");
        return tabLayout;
    }

    public void checkout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Você deseja finalizar a compra?")
            .setPositiveButton("Comprar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getBaseContext(), "Obrigado por comprar!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
