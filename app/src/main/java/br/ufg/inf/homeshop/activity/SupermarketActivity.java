package br.ufg.inf.homeshop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.adapter.SupermarketAdapter;
import br.ufg.inf.homeshop.model.Market;
import br.ufg.inf.homeshop.services.WebTaskSupermarketList;
import br.ufg.inf.homeshop.wrapper.SupermarketsWrapper;

public class SupermarketActivity extends AppCompatActivity {

    private View progressView;
    private GridView gridView;
    private List<Market> markets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarkets);
        progressView = findViewById(R.id.supermarkets_progress);
        gridView = (GridView) findViewById(R.id.grid_view);
        showProgress(true);
        WebTaskSupermarketList taskSupermarket = new WebTaskSupermarketList(this);
        taskSupermarket.execute();
        createToolbar();
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_supermarkets);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    private void attachAdapterToGrid() {
        SupermarketAdapter adapter = new SupermarketAdapter(this, markets);
        gridView.setAdapter(adapter);
        showProgress(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe()
    public void onEvent(SupermarketsWrapper supermarketsWrapper) {
        this.markets = supermarketsWrapper.getMarketList();
        attachAdapterToGrid();
    }

    @Subscribe
    public void onEvent(Error error) {
        showProgress(false);
        Log.e("supermarketActivity", "Error while fetching results.", error);
    }

    private void showProgress(boolean showProgress) {
        progressView.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        gridView.setVisibility(showProgress ? View.GONE : View.VISIBLE);
    }

}
