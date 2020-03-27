package br.com.uflaniano.ioasysdea.usuario.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.uflaniano.ioasysdea.R;
import br.com.uflaniano.ioasysdea.rede.RetrofitConfig;
import br.com.uflaniano.ioasysdea.rede.model.Header;
import br.com.uflaniano.ioasysdea.usuario.home.HomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static br.com.uflaniano.ioasysdea.usuario.home.HomeActivity.EXTRA_HEADER;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText editEmail;
    EditText editPassword;
    TextView tv_erro;
    Drawable img;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        tv_erro = findViewById(R.id.textErro);
//        img = getContext().getResources().getDrawable(R.drawable.ic_interrogacao);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatorLogin()) {
                    login();
                } else {
                    Toast.makeText(LoginActivity.this, "Preencha os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login() {
        Call call = new RetrofitConfig().service().login(email, password);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    String uid = response.headers().get("uid");
                    String client = response.headers().get("client");
                    String accessToken = response.headers().get("access-token");
                    Header header = new Header(uid, accessToken, client);

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra(EXTRA_HEADER, header);

                    startActivity(intent);
                    finish();
                    tv_erro.setVisibility(INVISIBLE);

                    buttonLogin.setBackgroundResource(R.color.btn_desabilidato);

                    img.setBounds(0, 0, 16, 16);
                    editEmail.setCompoundDrawables(null, null, null, null);
                } else {
                    //Toast.makeText(LoginActivity.this, "Erro ao se conectar", Toast.LENGTH_SHORT).show();
                    tv_erro.setVisibility(VISIBLE);
                    buttonLogin.setBackgroundResource(R.color.btn_desabilidato);
//                    img.setBounds(0, 0, 20, 20);
//                    editEmail.setCompoundDrawables(null, null, img, null);
                    editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_email, 0, R.drawable.ic_interrogacao, 0);
                    editPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_cadeado, 0, R.drawable.ic_interrogacao, 0);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Erro inesperado.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validatorLogin() {
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        editEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_email, 0, 0, 0);
        editPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_cadeado, 0, 0, 0);
        return !email.isEmpty() || !password.isEmpty();

    }


}


