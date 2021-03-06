package br.ufg.inf.homeshop.services;

import android.content.Context;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.Market;

public class WebTaskMarketDetail extends WebTaskBase {

    private static final String SERVICE_URL = "markets/";

    public WebTaskMarketDetail(Context context, String marketId) {
        super(context, SERVICE_URL + marketId, WebTaskBase.GET_METHOD);
    }

    @Override
    public void handleResponse(String response) {
        if (response == null || response.isEmpty()){
            EventBus.getDefault().post(new Error(getContext()
                .getString(R.string.invalid_server_response)));
        }
        Gson gson = new Gson();
        try {
            Market market = gson.fromJson(response, Market.class);
            EventBus.getDefault().post(market);
        } catch (Exception e) {
            EventBus.getDefault().post(new Error(getContext()
                .getString(R.string.invalid_server_response)));
        }
    }

    @Override
    public String getRequestBody() {
        return null;
    }
}
