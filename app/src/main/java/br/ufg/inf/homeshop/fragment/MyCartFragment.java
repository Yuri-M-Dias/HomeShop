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
import br.ufg.inf.homeshop.adapter.MyCartAdapter;
import br.ufg.inf.homeshop.model.CartItem;
import br.ufg.inf.homeshop.services.WebTaskCartList;
import br.ufg.inf.homeshop.wrapper.CartItemWrapper;

public class MyCartFragment extends Fragment {

    private List<CartItem> cartItemList;
    private RecyclerView recyclerView;
    private Long userId;

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
        SharedPreferences settings = getContext().getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
        userId = settings.getLong("userId", 1);
        WebTaskCartList taskProductList = new WebTaskCartList(getContext(), String.valueOf(userId));
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
    public void onEvent(CartItemWrapper cartItemWrapper) {
        this.cartItemList = cartItemWrapper.getCartItemList();
        initilizeAdapter();
    }

    @Subscribe
    public void onEvent(Error error) {
        Log.e("productListFragment", "Error while fetching results.", error);
        Toast.makeText(getContext(), "Erro ocorreu. Verifique sua internet.", Toast.LENGTH_LONG).show();
    }

    private void initilizeAdapter() {
        MyCartAdapter adapter = new MyCartAdapter(this.cartItemList);
        recyclerView.setAdapter(adapter);
    }

}
