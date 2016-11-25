package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Cliente;

/**
 * Created by João Paulo on 26/07/2016.
 */
public class AlteraClienteActivity extends AppCompatActivity {
    private EditText tNomeCliente;
    private EditText tSobrenomeCliente;
    private EditText tTelefoneCliente;
    private EditText tEmailCliente;
    private EditText tDataNascCliente;
    private String CPFcliente;
    private Cliente cliente;
    private BD_ControleDAO crud;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alteracliente);
        crud = new BD_ControleDAO(getBaseContext());
        CPFcliente = this.getIntent().getStringExtra("CPFcliente");
        cliente = crud.procuraCliente(CPFcliente);
        tNomeCliente = (EditText) findViewById(R.id.tNomeCliente);
        tNomeCliente.setText(cliente.getNome());
        tSobrenomeCliente = (EditText) findViewById(R.id.tSobrenomeCliente);
        tSobrenomeCliente.setText(cliente.getSobrenome());
        tTelefoneCliente = (EditText) findViewById(R.id.tTelefoneCliente);
        tTelefoneCliente.setText(cliente.getTelefone());
        tEmailCliente = (EditText) findViewById(R.id.tEmailCliente);
        tEmailCliente.setText(cliente.getEmail());
        tDataNascCliente = (EditText) findViewById(R.id.tDataNascCliente);
        tDataNascCliente.setText(cliente.getDataNascimento());



        Button bAlterarCli = (Button) findViewById(R.id.bAlterarCli);
        Button bCancelarCli = (Button) findViewById(R.id.bCancelarCli);

        bAlterarCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = tNomeCliente.getText().toString();
                String sobrenome = tSobrenomeCliente.getText().toString();
                String telefone = tTelefoneCliente.getText().toString();
                String email = tEmailCliente.getText().toString();
                String dataNasc = tDataNascCliente.getText().toString();
                cliente.setNome(nome);
                cliente.setSobrenome(sobrenome);
                cliente.setTelefone(telefone);
                cliente.setEmail(email);
                cliente.setDataNascimento(dataNasc);
                String resultado = crud.alteraCli(cliente);
                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                Intent at = new Intent(AlteraClienteActivity.this, ConsultaClienteActivity.class);
                startActivity(at);
                finish();
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
