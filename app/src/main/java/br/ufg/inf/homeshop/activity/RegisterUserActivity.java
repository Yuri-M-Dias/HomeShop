package br.ufg.inf.homeshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.User;
import br.ufg.inf.homeshop.services.WebTaskBase;
import br.ufg.inf.homeshop.services.WebTaskUser;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText passwordView;
    private EditText confirmationPasswordView;
    private View mRegisterForm;
    private View mProgressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.cadastro);
        setContentView(R.layout.activity_register_user);
        mRegisterForm = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
        createToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_register_user);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
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
            WebTaskUser userTask = new WebTaskUser(this, "user", user, WebTaskBase.PUT_METHOD);
            userTask.execute();
        }
    }

    @Subscribe
    public void onEvent(User user) {
        showProgress(false);
        Intent activity = new Intent(this, SupermarketActivity.class);
        SharedPreferences settings = this.getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("userId", Long.valueOf(user.getId()));
        editor.apply();
        showToast("Usuário cadastrado com sucesso.");
        finish();
    }

    @Subscribe
    public void onEvent(Error error) {
        showProgress(false);
        String message = "Ocorreu uma falha no cadastro";
        showToast(message);
        Intent activity = new Intent(this, SupermarketActivity.class);
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

    private void showToast(String message){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }

}
