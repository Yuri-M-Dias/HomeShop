package br.ufg.inf.homeshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

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
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
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

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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

    public void openUserMenu(View view) {
        final Context context = this;
        ImageButton userMenuButton = (ImageButton) findViewById(R.id.user_menu_button);
        PopupMenu popup = new PopupMenu(this, userMenuButton);
        popup.getMenuInflater().inflate(R.menu.user_menu, popup.getMenu());
        hideMenu(popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                final String perfil = getString(R.string.menu_item_perfil);
                final String cadastrar = getString(R.string.menu_item_cadastrar);
                final String login = getString(R.string.menu_item_login);
                String clickedItem = item.getTitle().toString();

                Intent intent = null;
                if (perfil.equals(clickedItem)) {
                    intent = new Intent(context, PerfilActivity.class);
                } else if (cadastrar.equals(clickedItem)) {
                    intent = new Intent(context, RegisterUserActivity.class);
                } else if (login.equals(clickedItem)) {
                    intent = new Intent(context, LoginActivity.class);
                }

                startActivity(intent);
                return true;
            }
        });

        popup.show();
    }

    private void hideMenu(Menu menu) {

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
        long userId = settings.getLong("userId", -1);

        if (userId == -1) {
            MenuItem perfilItem = menu.findItem(R.id.perfil_menu_item);
            perfilItem.setVisible(false);
            this.invalidateOptionsMenu();
        } else {
            MenuItem loginItem = menu.findItem(R.id.login_menu_item);
            MenuItem registerItem = menu.findItem(R.id.cadastrar_menu_item);
            loginItem.setVisible(false);
            registerItem.setVisible(false);
            this.invalidateOptionsMenu();
        }
    }

    private void showProgress(boolean showProgress) {
        progressView.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        gridView.setVisibility(showProgress ? View.GONE : View.VISIBLE);
    }

}
