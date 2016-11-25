package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Funcionario;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Login;

public class LoginActivity extends AppCompatActivity {
    private BD_ControleDAO crud;
    private EditText tUsuario;
    private EditText tSenha;
    private Funcionario funcionario;
    private Button bLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bLogar = (Button) findViewById(R.id.bLogar);
        tUsuario = (EditText) findViewById(R.id.tUsuario);
        tSenha = (EditText) findViewById(R.id.tSenha);
        final Resources resources = getResources(); //eu coloquei
        crud = new BD_ControleDAO(getBaseContext());
        crud.loginPadrao();
        bLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usu = tUsuario.getText().toString().trim(); //trim = tirar espaços em branco
                String sen = tSenha.getText().toString().trim();
                Login login = new Login(usu, sen);

                funcionario = crud.procuraFuncLogin(login.getUsuario());

                if (validarCampos(usu, sen)) {
                    int teste = crud.tentaLogar(login);
                    if (teste == -1) {
                        Toast.makeText(getApplicationContext(), "Senha ou usuário inválido!", Toast.LENGTH_LONG).show();
                    } else if (teste == 1) {
                        Toast.makeText(getApplicationContext(), "Bem vindo!", Toast.LENGTH_LONG).show();
                        Intent it = new Intent(LoginActivity.this, MenuGerenteActivity.class);
                        funcionario = crud.procuraFuncLogin(login.getUsuario());
                        it.putExtra("funcionarioCPF", funcionario.getCPF());
                        startActivity(it);
                    } else {
                        Toast.makeText(getApplicationContext(), "Bem vindo!", Toast.LENGTH_LONG).show();
                        Intent it = new Intent(LoginActivity.this, MenuFuncActivity.class);
                        it.putExtra("funcionarioCPF", funcionario.getCPF());
                        startActivity(it);
                    }
                }
            }

            public boolean validarCampos(String usu, String sen) {

                return (!isEmptyFields(usu, sen) );
            }

            private boolean isEmptyFields(String usu, String sen) {
                if (TextUtils.isEmpty(usu)) {
                    tUsuario.requestFocus();
                    tUsuario.setError(resources.getString(R.string.login_user_required));
                    return true;
                } else if (TextUtils.isEmpty(sen)) {
                    tSenha.requestFocus();
                    tSenha.setError(resources.getString(R.string.login_password_required));
                    return true;
                }
                return false;
            }
        });
    }

}
