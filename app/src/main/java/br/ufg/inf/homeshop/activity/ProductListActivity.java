package br.ufg.inf.homeshop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.adapter.ProductListAdapter;
import br.ufg.inf.homeshop.model.Product;

/**
 * Created by yuri on 07/07/16.
 */
public class ProductListActivity extends AppCompatActivity {

    private List<Product> productList;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        initializeData();
        initilizeAdapter();

    }

    /**
     * Initilizes the adapter, effectively putting the elements on the screen.
     */
    private void initilizeAdapter() {
        ProductListAdapter adapter = new ProductListAdapter(this.productList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Initializes the data that is being used, making the web request.
     */
    private void initializeData() {
        this.productList = new ArrayList<>();
        productList.add(new Product("Sucrilhos", "Yummy", 45D, "nothing"));
        productList.add(new Product("Sucrilhos", "Yummy", 45D, "nothing"));
        productList.add(new Product("Sucrilhos", "Yummy", 45D, "nothing"));
        productList.add(new Product("Sucrilhos", "Yummy", 45D, "nothing"));
    }

    public void detailItem(View view) {
        ImageButton button = (ImageButton) view;
        //Pega o item que foi clicado e cria a intent para o detail... mas como?
    }

}
