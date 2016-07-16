package br.ufg.inf.homeshop.services;

import android.content.Context;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufg.inf.homeshop.model.User;

public class WebTaskUser extends WebTaskBase {

    private User user;

    public WebTaskUser(Context context, String serviceURL, User user) {
        super(context, serviceURL);
        this.user = user;
    }

    @Override
    public void handleResponse(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(jsonObject);
        System.out.println(jsonObject);
    }

    @Override
    public String getRequestBody() {
        Gson gson = new Gson();
        return gson.toJson(user);
    }
}
