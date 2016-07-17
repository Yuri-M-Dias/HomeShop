package br.ufg.inf.homeshop.services;

import android.content.Context;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.ufg.inf.homeshop.R;

public class WebTaskSupermarket extends WebTaskBase {

    private static String SERVICE_URL = "markets";

    public WebTaskSupermarket(Context context) {
        super(context, SERVICE_URL, WebTaskBase.GET_METHOD);
    }

    @Override
    public void handleResponse(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            Gson gson = new Gson();
            gson.fromJson(jsonObject.toString(), ArrayList.class);
            EventBus.getDefault().post(jsonObject);
        } catch (JSONException e) {
            EventBus.getDefault().post(new Error(getContext().getString(R.string.invalid_server_response)));
            e.printStackTrace();
        }
    }

    @Override
    public String getRequestBody() {
        return "";
    }

}
