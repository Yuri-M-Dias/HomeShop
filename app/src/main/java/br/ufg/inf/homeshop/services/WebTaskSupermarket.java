package br.ufg.inf.homeshop.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.Market;

public class WebTaskSupermarket extends WebTaskBase {

    private static String SERVICE_URL = "markets";

    public WebTaskSupermarket(Context context) {
        super(context, SERVICE_URL, WebTaskBase.GET_METHOD);
    }

    @Override
    public void handleResponse(String response) {
        if (response == null || response.isEmpty()){
            EventBus.getDefault().post(new Error(getContext()
                .getString(R.string.invalid_server_response)));
        }
        Type typeToken = new TypeToken<List<Market>>(){}.getType();
        Gson gson = new Gson();
        ArrayList<Market> markets = gson.fromJson(response, typeToken);
        EventBus.getDefault().post(markets);
    }

    @Override
    public String getRequestBody() {
        return "";
    }

}
