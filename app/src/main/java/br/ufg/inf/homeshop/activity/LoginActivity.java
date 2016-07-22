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

public class LoginActivity extends AppCompatActivity {

    private View mProgressView;
    private View mLoginForm;
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mProgressView = findViewById(R.id.login_progress);
        mLoginForm = findViewById(R.id.login_form);
        mEmailView = (EditText) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        toolbar.setTitle("Sign in");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
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

    public void login(View view) {

        String email = getTextFromEditTextView(R.id.login_email);
        String password = getTextFromEditTextView(R.id.login_password);

        if ("".equals(email)) {
            mEmailView.setError("Campo obrigatório.");
        } else if ("".equals(password)) {
            mPasswordView.setError("Campo obrigatório.");
        } else {
            User user = new User();
            user.setEmail(email);
            user.setSenha(password);
            String userURL = "login";
            WebTaskUser webTaskUser = new WebTaskUser(this, userURL, user, WebTaskBase.POST_METHOD);
            showProgress(true);
            webTaskUser.execute();
        }
    }

    @Subscribe
    public void onEvent(User user) {
        showProgress(false);
        Intent intent = new Intent(this, SupermarketActivity.class);
        SharedPreferences settings = this.getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("userId", Long.valueOf(user.getId()));
        editor.apply();

        showToast("Sucesso");
        finish();
    }

    private String getTextFromEditTextView(int viewId) {
        return ((EditText) findViewById(viewId)).getText().toString();
    }

    private void showProgress(boolean showProgress) {
        mProgressView.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        mLoginForm.setVisibility(showProgress ? View.GONE : View.VISIBLE);
    }

    private void showToast(String message) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }
}
