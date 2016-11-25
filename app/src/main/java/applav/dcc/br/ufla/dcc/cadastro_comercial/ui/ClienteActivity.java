package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.Mascara;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Cliente;

/**
 * Created by João Paulo on 26/07/2016.
 */
public class ClienteActivity extends AppCompatActivity {
    private EditText tNomeCliente;
    private EditText tSobrenomeCliente;
    private EditText tCPFcliente;
    private EditText tTelefoneCliente;
    private EditText tEmailCliente;
    private EditText tDataNascCliente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        tNomeCliente = (EditText) findViewById(R.id.tNomeCliente);
        tSobrenomeCliente = (EditText) findViewById(R.id.tSobrenomeCliente);
        tCPFcliente = (EditText) findViewById(R.id.tCPFcliente);
        tCPFcliente.addTextChangedListener(Mascara.insert("###.###.###-##", tCPFcliente));
        tTelefoneCliente = (EditText) findViewById(R.id.tTelefoneCliente);
        tTelefoneCliente.addTextChangedListener(Mascara.insert("(##)#####-####", tTelefoneCliente));
        tEmailCliente = (EditText) findViewById(R.id.tEmailCliente);
        tDataNascCliente = (EditText) findViewById(R.id.tDataNascCliente);
        tDataNascCliente.addTextChangedListener(Mascara.insert("##/##/####", tDataNascCliente));

        Button bCadastrarCli = (Button) findViewById(R.id.bCadastrarCli);
        Button bCancelarCli = (Button) findViewById(R.id.bCancelarCli);

        final Resources resources = getResources();

        bCadastrarCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BD_ControleDAO crud = new BD_ControleDAO(getBaseContext());
                String nome = tNomeCliente.getText().toString();
                String sobrenome = tSobrenomeCliente.getText().toString();
                String CPF = tCPFcliente.getText().toString();
                String telefone = tTelefoneCliente.getText().toString();
                String email = tEmailCliente.getText().toString();
                String dataNasc = tDataNascCliente.getText().toString();
                Cliente cliente = new Cliente(nome, sobrenome, CPF, telefone, email, dataNasc);

                if (validarCampos(nome, sobrenome, CPF, dataNasc)) {
                    String resultado = crud.insereCli(cliente);
                    Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            public boolean validarCampos(String nome, String sobrenome, String CPF, String dataNasc) {
                return (!isEmptyFields(nome, sobrenome, CPF, dataNasc));
            }

            private boolean isEmptyFields(String nome, String sobrenome, String CPF, String dataNasc) {
                if (TextUtils.isEmpty(nome)) {
                    tNomeCliente.requestFocus();
                    tNomeCliente.setError(resources.getString(R.string.value_required));
                    return true;
                } else if (TextUtils.isEmpty(sobrenome)) {
                    tSobrenomeCliente.requestFocus();
                    tSobrenomeCliente.setError(resources.getString(R.string.value_required));
                    return true;
                } else if (TextUtils.isEmpty(CPF)) {
                    tCPFcliente.requestFocus();
                    tCPFcliente.setError(resources.getString(R.string.value_required));
                    return true;
                } else if (TextUtils.isEmpty(dataNasc)) {
                    tDataNascCliente.requestFocus();
                    tDataNascCliente.setError(resources.getString(R.string.value_required));
                    return true;
                }
                return false;
            }
        });

        bCancelarCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
