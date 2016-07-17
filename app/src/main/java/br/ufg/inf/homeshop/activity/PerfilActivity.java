package br.ufg.inf.homeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.User;
import br.ufg.inf.homeshop.services.WebTaskUser;

public class PerfilActivity extends AppCompatActivity {

    private User user;
    private View mPerfilForm;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        mPerfilForm = findViewById(R.id.perfil_form);
        mProgressView = findViewById(R.id.perfil_progress);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void updateUserInfo(View view) {
        User user = getUserInfo();

        showProgress(true);
        WebTaskUser webTaskUser = new WebTaskUser(this, "user", user, WebTaskUser.PUT_METHOD);
        webTaskUser.execute();
    }

    @Subscribe
    public void onEvent(JSONObject json) {
        showProgress(false);
        String message = "";
        try {
            if (json.get("status").toString().equals("Success")) {
                message = "Informações alteradas com sucesso";
            }
        } catch (JSONException e) {
            message = "Erro ao alterar informações";
            e.printStackTrace();
        }

        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();

        Intent activity = new Intent(this, SupermarketActivity.class);
        startActivity(activity);
    }

    @Subscribe
    public void onEvent(Error error) {
        showProgress(false);
    }

    private String getTextFromEditTextView(int viewId) {
        return ((EditText) findViewById(viewId)).getText().toString();
    }

    private User getUserInfo() {
        String nome = getTextFromEditTextView(R.id.user_full_name);
        String cpf = getTextFromEditTextView(R.id.user_cpf);
        String email = getTextFromEditTextView(R.id.user_email);
        String endereco = getTextFromEditTextView(R.id.user_address);
        String numeroCartao = getTextFromEditTextView(R.id.user_credit_card);

        User user = new User();
        user.setNome(nome);
        user.setCpf(cpf);
        user.setEmail(email);
        user.setEndereco(endereco);
        user.setNumeroCartao(numeroCartao);
        return user;
    }

    private void showProgress(boolean showProgress) {
        mProgressView.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        mPerfilForm.setVisibility(showProgress ? View.GONE : View.VISIBLE);
    }
}
