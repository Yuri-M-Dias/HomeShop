package br.ufg.inf.homeshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.adapter.MyCartAdapter;
import br.ufg.inf.homeshop.model.CartItem;
import br.ufg.inf.homeshop.model.Product;

public class MyCartFragment extends Fragment {
    private List<CartItem> cartItemList;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mycart_list, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.mycart_list_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        initializeData();
        initilizeAdapter();

    }

    //Initilizes the adapter, effectively putting the elements on the screen.

    private void initilizeAdapter() {
        MyCartAdapter adapter = new MyCartAdapter(this.cartItemList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Initializes the data that is being used, making the web request.
     */
    public void initializeData() {
        this.cartItemList = new ArrayList<>();
        Product product = new Product("Leite", "Branquinho", 45D, "nothing");
        cartItemList.add(new CartItem(product, 10));
    }

    public void detailItem(View view) {
        ImageButton button = (ImageButton) view;
        //Pega o item que foi clicado e cria a intent para o detail... mas como?
    }


}
