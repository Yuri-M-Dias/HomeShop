package br.ufg.inf.homeshop.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.CartItem;
import br.ufg.inf.homeshop.wrapper.CartItemWrapper;

public class WebTaskCartList extends WebTaskBase {

    private static final String SERVICE_URL = "users/";

    public WebTaskCartList(Context context, String userId) {
        super(context, SERVICE_URL + userId + "/cart", WebTaskBase.GET_METHOD);
    }

    @Override
    public void handleResponse(String response) {
        Type typeToken = new TypeToken<List<CartItem>>(){}.getType();
        Gson gson = new Gson();
        try {
            List<CartItem> productList = gson.fromJson(response, typeToken);
            CartItemWrapper cartItemWrapper = new CartItemWrapper(productList);
            EventBus.getDefault().post(cartItemWrapper);
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
