package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.Mascara;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Roupa;

/**
 * Created by João Paulo on 19/07/2016.
 */
public class RoupaActivity extends AppCompatActivity {
    private EditText tPreco;
    private EditText tCor;
    private EditText tMaterial;
    private EditText tDetalhes;
    private EditText tNomeRoupa;
    private EditText tCodigoRoupa;
    private Spinner sTamanho;
    private Spinner sReferencia;
    private ArrayAdapter<String> spinnerArrayAdapterNUM;
    private ArrayAdapter<String> spinnerArrayAdapterLET;
    private ArrayAdapter<String> spinnerArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roupa);
        tPreco = (EditText) findViewById(R.id.tPreco);
        tPreco.setText("0.00");
        tCor = (EditText) findViewById(R.id.tCor);
        tMaterial = (EditText) findViewById(R.id.tMaterial);
        tDetalhes = (EditText) findViewById(R.id.tDetalhes);
        tNomeRoupa = (EditText) findViewById(R.id.tNomeRoupa);
        tCodigoRoupa = (EditText) findViewById(R.id.tCodigoRoupa);
        tCodigoRoupa.addTextChangedListener(Mascara.insert("###-##", tCodigoRoupa));

        sTamanho = (Spinner) findViewById(R.id.spinner);
        sReferencia = (Spinner) findViewById(R.id.spinnerTipo);

        List<String> tipos = new ArrayList<String>();
        tipos.add("Camisa");
        tipos.add("Calça");
        tipos.add("Sapato");

        List<String> tamanhos = new ArrayList<String>();
        tamanhos.add("34");
        tamanhos.add("36");
        tamanhos.add("38");
        tamanhos.add("40");
        tamanhos.add("42");
        tamanhos.add("44");

        List<String> tamanhos2 = new ArrayList<String>();
        tamanhos2.add("PP");
        tamanhos2.add("P");
        tamanhos2.add("M");
        tamanhos2.add("G");
        tamanhos2.add("GG");

        ArrayAdapter<String> arrayAdapterNUM = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tamanhos);
        spinnerArrayAdapterNUM = arrayAdapterNUM;
        spinnerArrayAdapterNUM.setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayAdapter<String> arrayAdapterLET = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tamanhos2);
        spinnerArrayAdapterLET = arrayAdapterLET;
        spinnerArrayAdapterLET.setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
        spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sReferencia.setAdapter(spinnerArrayAdapter);


        sReferencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String testeTamanho = sReferencia.getSelectedItem().toString();
                if (testeTamanho == "Camisa") {
                    sTamanho.setAdapter(spinnerArrayAdapterLET);
                }
                if (testeTamanho == "Calça" || testeTamanho == "Sapato"){
                    sTamanho.setAdapter(spinnerArrayAdapterNUM);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        Button bCadastrar = (Button) findViewById(R.id.bCadastrar);
        Button bCancelar = (Button) findViewById(R.id.bCancelar);

        final Resources resources = getResources();

        bCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BD_ControleDAO crud = new BD_ControleDAO(getBaseContext());
                String referencia = sReferencia.getSelectedItem().toString();
                String nome = tNomeRoupa.getText().toString();
                String codigo = tCodigoRoupa.getText().toString().toUpperCase();
                double preco = Double.parseDouble(tPreco.getText().toString());
                String cor = tCor.getText().toString();
                String material = tMaterial.getText().toString();
                String detalhes = tDetalhes.getText().toString();
                String tamanho = sTamanho.getSelectedItem().toString();
                Roupa roupa = new Roupa(referencia, nome, codigo, preco, cor, tamanho, material, detalhes);

                if (validarCampos(nome, codigo, preco, cor, material)) {
                        String resultado = crud.insereRoupa(roupa);
                        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                        finish();
                }
             }

            public boolean validarCampos(String nome, String codigo, double preco, String cor, String material) {
                return !isEmptyFields(nome, codigo, cor, material, preco) && maiorQueZero(preco);
            }

            private boolean maiorQueZero (double preco) {
                if (preco >= 0) {
                    return true;
                }
                tPreco.requestFocus();
                tPreco.setError("O valor deve ser maior que 0.");
                return false;
            }

            private boolean isEmptyFields(String nome, String codigo, String cor, String material, double preco) {
                if (TextUtils.isEmpty(nome)) {
                    tNomeRoupa.requestFocus();
                    tNomeRoupa.setError(resources.getString(R.string.value_required));
                    return true;
                } else if (TextUtils.isEmpty(codigo)) {
                    tCodigoRoupa.requestFocus();
                    tCodigoRoupa.setError(resources.getString(R.string.value_required));
                    return true;
                } else if (TextUtils.isEmpty(cor)) {
                    tCor.requestFocus();
                    tCor.setError(resources.getString(R.string.value_required));
                    return true;
                } else if (TextUtils.isEmpty(material)) {
                    tMaterial.requestFocus();
                    tMaterial.setError(resources.getString(R.string.value_required));
                    return true;
                }
                return false;
            }
        });

        bCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}
