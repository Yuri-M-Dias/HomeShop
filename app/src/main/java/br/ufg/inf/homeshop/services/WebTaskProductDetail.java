package br.ufg.inf.homeshop.services;

import android.content.Context;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.Product;

public class WebTaskProductDetail extends WebTaskBase {
    private static final String SERVICE_URL = "markets/";

    public WebTaskProductDetail(Context context, String marketId, String productId) {
        super(context, SERVICE_URL + marketId + "/products/" + productId, WebTaskBase.GET_METHOD);
    }

    @Override
    public void handleResponse(String response) {
        if (response == null || response.isEmpty()){
            EventBus.getDefault().post(new Error(getContext()
                .getString(R.string.invalid_server_response)));
        }
        Gson gson = new Gson();
        try {
            Product product = gson.fromJson(response, Product.class);
            EventBus.getDefault().post(product);
        } catch (Exception e) {//TODO: REMOVE ME
            //TODO: erros no Gson?
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
