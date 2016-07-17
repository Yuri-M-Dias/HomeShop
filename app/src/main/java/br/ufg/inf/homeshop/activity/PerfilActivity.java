package br.ufg.inf.homeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.ufg.inf.homeshop.R;
import br.ufg.inf.homeshop.model.User;
import br.ufg.inf.homeshop.services.WebTaskBase;
import br.ufg.inf.homeshop.services.WebTaskUser;

public class PerfilActivity extends AppCompatActivity {

    private User user;
    private String userId;
    private View mPerfilForm;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        mPerfilForm = findViewById(R.id.perfil_form);
        mProgressView = findViewById(R.id.perfil_progress);
        userId = getIntent().getStringExtra("userId");
        //TODO: remover após teste
        userId = "1";
        getUserInformationFromServer();
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
        User user = getUserInfoFromView();
        showProgress(true);
        String userURL = "users/" + userId;
        WebTaskUser webTaskUser = new WebTaskUser(this, userURL, user, WebTaskUser.POST_METHOD);
        webTaskUser.execute();
    }

    private void getUserInformationFromServer() {
        showProgress(true);
        String userURL = "users/" + userId;
        WebTaskUser webTaskUser = new WebTaskUser(this, userURL, null, WebTaskBase.GET_METHOD);
        webTaskUser.execute();
    }

    @Subscribe
    public void onEvent(User user) {
        showProgress(false);
        this.user = user;
        setTextIntoEditTextView(R.id.user_full_name, user.getNome());
        setTextIntoEditTextView(R.id.user_email, user.getEmail());
        setTextIntoEditTextView(R.id.user_cpf, user.getCpf());
        setTextIntoEditTextView(R.id.user_address, user.getEndereco());
        setTextIntoEditTextView(R.id.user_credit_card, user.getNumeroCartao());
    }

    @Subscribe
    public void onEvent(Boolean resultado) {
        showProgress(false);
        String message = "Informações alteradas com sucesso";
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

    private User getUserInfoFromView() {
        String nome = getTextFromEditTextView(R.id.user_full_name);
        String cpf = getTextFromEditTextView(R.id.user_cpf);
        String email = getTextFromEditTextView(R.id.user_email);
        String endereco = getTextFromEditTextView(R.id.user_address);
        String numeroCartao = getTextFromEditTextView(R.id.user_credit_card);

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

    private void setTextIntoEditTextView(int viewId, String text){
        ((EditText) findViewById(viewId)).setText(text);
    }
}
