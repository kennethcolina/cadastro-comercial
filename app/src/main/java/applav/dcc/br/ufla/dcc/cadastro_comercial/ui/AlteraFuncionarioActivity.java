package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Funcionario;

/**
 * Created by João Paulo on 26/07/2016.
 */
public class AlteraFuncionarioActivity extends AppCompatActivity {
    private String vendedor = "Vendedor(0)";
    private String gerente = "Gerente(1)";
    private String cpfFunc;
    private EditText tNomeFunc;
    private EditText tSobrenomeFunc;
    private EditText tTelefoneFunc;
    private EditText tEmailFunc;
    private EditText tDataNascFunc;
    private EditText tSalarioFunc;
    private EditText tComissao;
    private Spinner spinnerNivel;
    private BD_ControleDAO crud;
    private Funcionario funcionario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterafuncionario);
        crud = new BD_ControleDAO(getBaseContext());
        cpfFunc = this.getIntent().getStringExtra("CPFfunc");
        funcionario = crud.procuraFunc(cpfFunc);
        tNomeFunc = (EditText) findViewById(R.id.tnomeFunc1);
        tNomeFunc.setText(funcionario.getNome());
        tSobrenomeFunc = (EditText) findViewById(R.id.tSobrenomeFunc);
        tSobrenomeFunc.setText(funcionario.getSobrenome());
        tTelefoneFunc = (EditText) findViewById(R.id.tTelefoneFunc);
        tTelefoneFunc.setText(funcionario.getTelefone());
        tEmailFunc = (EditText) findViewById(R.id.tEmailFunc);
        tEmailFunc.setText(funcionario.getEmail());
        tDataNascFunc = (EditText) findViewById(R.id.tDataNascFunc);
        tDataNascFunc.setText(funcionario.getDataNascimento());
        tSalarioFunc = (EditText) findViewById(R.id.tSalarioFunc);
        tSalarioFunc.setText(Float.toString(funcionario.getSalario()));
        tComissao = (EditText) findViewById(R.id.tComissao);
        tComissao.setText(Float.toString(funcionario.getComissao()));
        spinnerNivel = (Spinner) findViewById(R.id.spinnerNivel);

        Button bAlterarFunc = (Button) findViewById(R.id.bAlterarFunc);
        Button bCancelarFunc = (Button) findViewById(R.id.bCancelarFunc);
        List<String> niveis = new ArrayList<String>();
        niveis.add(vendedor);
        niveis.add(gerente);



        ArrayAdapter<String> arrayAdapterNivel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, niveis);
        final ArrayAdapter<String> spinnerArrayAdapterNivel = arrayAdapterNivel;
        spinnerArrayAdapterNivel.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerNivel.setAdapter(spinnerArrayAdapterNivel);
        if (funcionario.getNivel() == 0) {
            spinnerNivel.setSelection(0);
        } else{
            spinnerNivel.setSelection(1);
        }

        bAlterarFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NivelStr = spinnerNivel.getSelectedItem().toString();
                int nivel;
                if (NivelStr == vendedor) {
                    nivel = 0;
                } else {
                    nivel = 1;
                }

                String nome = tNomeFunc.getText().toString();
                String sobrenome = tSobrenomeFunc.getText().toString();
                String telefone = tTelefoneFunc.getText().toString();
                String email = tEmailFunc.getText().toString();
                String dataNasc = tDataNascFunc.getText().toString();
                float salario = Float.valueOf(tSalarioFunc.getText().toString());
                float comissao = Float.valueOf(tComissao.getText().toString());

                funcionario.setNome(nome);
                funcionario.setSobrenome(sobrenome);
                funcionario.setTelefone(telefone);
                funcionario.setEmail(email);
                funcionario.setDataNascimento(dataNasc);
                funcionario.setSalario(salario);
                funcionario.setComissao(comissao);
                funcionario.setNivel(nivel);

                String resultado = crud.alteraFunc(funcionario);

                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                Intent at = new Intent(AlteraFuncionarioActivity.this, ConsultaFuncActivity.class);
                startActivity(at);
                finish();


            }
        });

        bCancelarFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
