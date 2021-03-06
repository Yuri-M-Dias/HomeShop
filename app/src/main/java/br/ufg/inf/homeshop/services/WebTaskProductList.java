package br.ufg.inf.homeshop.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.Product;
import br.ufg.inf.homeshop.wrapper.ProductListWrapper;

public class WebTaskProductList extends WebTaskBase {
    private static final String SERVICE_URL = "markets/";

    public WebTaskProductList(Context context, String marketId) {
        super(context, SERVICE_URL + marketId + "/products", WebTaskBase.GET_METHOD);
    }

    @Override
    public void handleResponse(String response) {
        Type typeToken = new TypeToken<List<Product>>(){}.getType();
        Gson gson = new Gson();
        try {
            List<Product> productList = gson.fromJson(response, typeToken);
            ProductListWrapper productListWrapper = new ProductListWrapper(productList);
            EventBus.getDefault().post(productListWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            EventBus.getDefault().post(new Error(getContext()
                .getString(R.string.invalid_server_response)));
        }
    }

    @Override
    public String getRequestBody() {
        return null;
    }

}
