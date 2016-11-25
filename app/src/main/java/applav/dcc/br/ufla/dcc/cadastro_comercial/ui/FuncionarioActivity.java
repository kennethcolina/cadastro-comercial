package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.Mascara;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Funcionario;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Login;

/**
 * Created by João Paulo on 26/07/2016.
 */
public class FuncionarioActivity extends AppCompatActivity {
    private String vendedor = "Vendedor(0)";
    private String gerente = "Gerente(1)";
    private EditText tNomeFunc;
    private EditText tSobrenomeFunc;
    private EditText tCPFfunc;
    private EditText tTelefoneFunc;
    private EditText tEmailFunc;
    private EditText tDataNascFunc;
    private EditText tSalarioFunc;
    private EditText tComissao;

    private Spinner spinnerNivel;

    private EditText tLoginFunc;
    private EditText tSenhaFunc;
    private EditText tConfirmaSenha;
    private ArrayAdapter<String> spinnerArrayAdapterNivel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);
        tNomeFunc = (EditText) findViewById(R.id.tnomeFunc1);
        tSobrenomeFunc = (EditText) findViewById(R.id.tSobrenomeFunc);
        tCPFfunc = (EditText) findViewById(R.id.tCPFfunc);
        tCPFfunc.addTextChangedListener(Mascara.insert("###.###.###-##", tCPFfunc));
        tTelefoneFunc = (EditText) findViewById(R.id.tTelefoneFunc);
        tTelefoneFunc.addTextChangedListener(Mascara.insert("(##)#####-####", tTelefoneFunc));
        tEmailFunc = (EditText) findViewById(R.id.tEmailFunc);
        tDataNascFunc = (EditText) findViewById(R.id.tDataNascFunc);
        tDataNascFunc.addTextChangedListener(Mascara.insert("##/##/####", tDataNascFunc));
        tSalarioFunc = (EditText) findViewById(R.id.tSalarioFunc);
        tSalarioFunc.setText("0.00");
        tComissao = (EditText) findViewById(R.id.tComissao);
        tComissao.setText("0.00");

        spinnerNivel = (Spinner) findViewById(R.id.spinnerNivel);

        tLoginFunc = (EditText) findViewById(R.id.tLoginFunc);
        tSenhaFunc = (EditText) findViewById(R.id.tSenhaFunc);
        tConfirmaSenha = (EditText) findViewById(R.id.tConfirmaSenha);

        Button bCadastrarFunc = (Button) findViewById(R.id.bCadastrarFunc);
        Button bCancelarFunc = (Button) findViewById(R.id.bCancelarFunc);

        List<String> niveis = new ArrayList<String>();
        niveis.add(vendedor);
        niveis.add(gerente);
 ;
        ArrayAdapter<String> arrayAdapterNivel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, niveis);
        spinnerArrayAdapterNivel = arrayAdapterNivel;
        spinnerArrayAdapterNivel.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerNivel.setAdapter(spinnerArrayAdapterNivel);

        final Resources resources = getResources();

        bCadastrarFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BD_ControleDAO crud = new BD_ControleDAO(getBaseContext());
                String nome = tNomeFunc.getText().toString();
                String sobrenome = tSobrenomeFunc.getText().toString();
                String CPF = tCPFfunc.getText().toString();
                String telefone = tTelefoneFunc.getText().toString();
                String email = tEmailFunc.getText().toString();
                String dataNasc = tDataNascFunc.getText().toString();
                float salario = Float.valueOf(tSalarioFunc.getText().toString());
                float comissao = Float.valueOf(tComissao.getText().toString());
                int nivel;
                String NivelStr = spinnerNivel.getSelectedItem().toString();

                if(NivelStr == vendedor)
                    nivel = 0;
                else
                    nivel = 1;

                String dataInicio = DateFormat.getDateInstance().format(new Date());
                String usuario = tLoginFunc.getText().toString();
                String senha = tSenhaFunc.getText().toString();
                if(ConfirmaSenha(senha,tConfirmaSenha.getText().toString())) {

                    Login login = new Login(usuario, senha);

                    Funcionario funcionario = new Funcionario(nome, sobrenome, CPF, telefone, salario, comissao, email, dataNasc, dataInicio, login, nivel);

                    if (validarCampos(nome, sobrenome, CPF, dataNasc, salario, comissao, login.getUsuario(), login.getSenha())) {
                        String resultado = crud.insereFunc(funcionario);
                        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }

            public boolean validarCampos(String nome, String sobrenome, String CPF, String dataNasc,
                                         float salario, float comissao, String usu, String sen) {
                return (!isEmptyFields(nome, sobrenome, CPF, dataNasc)) && maiorQueZero(salario, comissao) && hasSizeValid(usu, sen);
            }

            private boolean maiorQueZero (float salario, float comissao) {
                if (salario < 0) {
                    tSalarioFunc.requestFocus();
                    tSalarioFunc.setError("O valor não pode ser menor que 0.");
                    return false;
                } else if(comissao < 0) {
                    tComissao.requestFocus();
                    tComissao.setError("O valor não pode ser menor que 0.");
                    return false;
                }
                return true;
            }

            private boolean hasSizeValid(String usu, String sen) {
                if (!(usu.length() > 3 && !(usu.length() > 10))) {
                    tLoginFunc.requestFocus();
                    tLoginFunc.setError(resources.getString(R.string.login_user_size_invalid));
                    return false;
                } else if (!(sen.length() > 3 && !(sen.length() > 10))) {
                    tSenhaFunc.requestFocus();
                    tSenhaFunc.setError(resources.getString(R.string.login_pass_size_invalid));
                    return false;
                }
                return true;
            }

            private boolean isEmptyFields(String nome, String sobrenome, String CPF, String dataNasc) {
                if (TextUtils.isEmpty(nome)) {
                    tNomeFunc.requestFocus();
                    tNomeFunc.setError(resources.getString(R.string.value_required));
                    return true;
                } else if (TextUtils.isEmpty(sobrenome)) {
                    tSobrenomeFunc.requestFocus();
                    tSobrenomeFunc.setError(resources.getString(R.string.value_required));
                    return true;
                } else if (TextUtils.isEmpty(CPF)) {
                    tCPFfunc.requestFocus();
                    tCPFfunc.setError(resources.getString(R.string.value_required));
                    return true;
                } else if (TextUtils.isEmpty(dataNasc)) {
                    tDataNascFunc.requestFocus();
                    tDataNascFunc.setError(resources.getString(R.string.value_required));
                    return true;
                }
                return false;
            }
        });

        bCancelarFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    public boolean ConfirmaSenha (String senha1,String senha2){
        if(senha1.equals(senha2)){
            return true;
        }else{
            tSenhaFunc.setError("As senhas não conferem.");
            tSenhaFunc.requestFocus();
            tConfirmaSenha.setError("As senhas não conferem.");
            return false;
        }
    }
}
