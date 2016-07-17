package br.ufg.inf.homeshop.services;

import android.content.Context;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import br.ufg.inf.homeshop.model.User;

public class WebTaskUser extends WebTaskBase {

    private User user;

    public WebTaskUser(Context context, String serviceURL, User user, String method) {
        super(context, serviceURL, method);
        this.user = user;
    }

    @Override
    public void handleResponse(String response) {
        if ("".equals(response) || response == null) {
            EventBus.getDefault().post(true);
        } else {
            Gson gson = new Gson();
            User user = gson.fromJson(response, User.class);
            EventBus.getDefault().post(user);
        }
    }

    @Override
    public String getRequestBody() {
        if (user != null) {
            Gson gson = new Gson();
            return gson.toJson(user);
        } else {
            return "";
        }
    }
}
