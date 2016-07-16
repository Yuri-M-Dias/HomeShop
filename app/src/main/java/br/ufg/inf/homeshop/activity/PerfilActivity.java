package br.ufg.inf.homeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.User;
import br.ufg.inf.homeshop.services.WebTaskUser;

public class PerfilActivity extends AppCompatActivity {

    private EditText passwordView;
    private EditText confirmationPasswordView;
    private View mRegisterForm;
    private View mProgressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.cadastro);
        setContentView(R.layout.activity_perfil);
        mRegisterForm = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
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

    public void cadastrarUsuario(View view) {
        confirmationPasswordView = getConfirmationPasswordView();

        String email = getEmailView().getText().toString();
        String senha = getPasswordView().getText().toString();
        String confirmacaoSenha = getConfirmationPasswordView().getText().toString();

        if (!senha.equals(confirmacaoSenha)) {
            confirmationPasswordView.setError("As senhas devems ser iguais !");
        } else {
            User user = new User();
            user.setEmail(email);
            user.setSenha(senha);
            showProgress(true);
            WebTaskUser userTask = new WebTaskUser(this, "login", user);
            userTask.execute();
        }
    }

    @Subscribe
    public void onEvent(JSONObject json) {
        showProgress(false);
        Intent activity = new Intent(this, SupermarketActivity.class);
        activity.putExtra("message", "olá");
        startActivity(activity);
    }

    private void showProgress(boolean showProgress) {
        mProgressView.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        mRegisterForm.setVisibility(showProgress ? View.GONE : View.VISIBLE);
    }

    private EditText getEditText(int viewId) {
        return (EditText) findViewById(viewId);
    }

    private EditText getConfirmationPasswordView() {
        return getEditText(R.id.confirmarSenha);
    }

    private EditText getPasswordView() {
        return getEditText(R.id.senha);
    }

    private EditText getEmailView() {
        return getEditText(R.id.email);
    }

}
