package br.ufg.inf.homeshop.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.activity.MainActivity;
import br.ufg.inf.homeshop.adapter.ProductListAdapter;
import br.ufg.inf.homeshop.model.Product;
import br.ufg.inf.homeshop.services.WebTaskProductList;

public class ProductListFragment extends Fragment {

    private List<Product> productList;
    private RecyclerView recyclerView;
    private Long marketId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.product_list_recyclerview);
        SharedPreferences settings = getContext().getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
        marketId = settings.getLong("marketId", -1);
        if (marketId == -1) {
            //Não tem supermercado setado, volta para a tela de seleção.
            getActivity().finish();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        WebTaskProductList taskProductList = new WebTaskProductList(getContext(), String.valueOf(marketId));
        taskProductList.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(List<Product> products) {
        this.productList = products;
        initilizeAdapter();
        Toast.makeText(getContext(), "Produtos carregados com sucesso!", Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onEvent(Error error) {
        Log.e("productListFragment", "Error while fetching results.", error);
        Toast.makeText(getContext(), "Erro ocorreu. Verifique sua internet.", Toast.LENGTH_LONG).show();
        //Flashes error
    }

    //Initilizes the adapter, effectively putting the elements on the screen.
    private void initilizeAdapter() {
        ProductListAdapter adapter = new ProductListAdapter(this.productList, getContext());
        recyclerView.setAdapter(adapter);
    }

}
