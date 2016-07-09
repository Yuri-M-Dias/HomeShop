package br.ufg.inf.homeshop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import br.ufg.inf.homeshop.R;

public class SupermarketActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarkets);

        GridView gridView = (GridView) findViewById(R.id.grid_view);

        gridView.setAdapter(new SupermarketAdapter(this));
    }
}
