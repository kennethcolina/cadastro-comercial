package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Roupa;

/**
 * Created by João Paulo on 19/07/2016.
 */
public class AlteraRoupaActivity extends AppCompatActivity {
    private EditText tPreco;
    private EditText tCor;
    private EditText tMaterial;
    private EditText tDetalhes;
    private EditText tNomeRoupa;
    private Spinner sTamanho;
    private Spinner sReferencia;
    private String codigoRoupa;
    private BD_ControleDAO crud;
    private Roupa roupa;
    private ArrayAdapter<String> spinnerArrayAdapterLET;
    private ArrayAdapter<String> spinnerArrayAdapterNUM;
    private List<Roupa> itens = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alteraroupa);
        crud = new BD_ControleDAO(getBaseContext());
        codigoRoupa = this.getIntent().getStringExtra("codigoRoupa");
        roupa = crud.procuraRoupa(codigoRoupa);
        tPreco = (EditText) findViewById(R.id.tPreco);
        tPreco.setText(Double.toString(roupa.getPreco()));
        tCor = (EditText) findViewById(R.id.tCor);
        tCor.setText(roupa.getCor());
        tMaterial = (EditText) findViewById(R.id.tMaterial);
        tMaterial.setText(roupa.getMaterial());
        tDetalhes = (EditText) findViewById(R.id.tDetalhes);
        tDetalhes.setText(roupa.getDetalhes());
        tNomeRoupa = (EditText) findViewById(R.id.tNomeRoupa);
        tNomeRoupa.setText(roupa.getNome());
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
        final ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sReferencia.setAdapter(spinnerArrayAdapter);
        if(roupa.getReferencia() == "Camisa") {
            sReferencia.setSelection(0);
        }else if(roupa.getReferencia() == "Calça"){
            sReferencia.setSelection(1);
        }else if(roupa.getReferencia() == "Sapato") {
            sReferencia.setSelection(2);
        }

            sReferencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String testeTamanho = sReferencia.getSelectedItem().toString();
                    if (testeTamanho == "Camisa") {
                        sTamanho.setAdapter(spinnerArrayAdapterLET);
                        if(roupa.getTamanho() == "PP") {
                            sTamanho.setSelection(0);
                        }else if(roupa.getTamanho() == "P"){
                            sTamanho.setSelection(1);
                        }else if(roupa.getTamanho() == "M") {
                            sTamanho.setSelection(2);
                        }else if(roupa.getTamanho() == "G") {
                            sTamanho.setSelection(3);
                        }else if(roupa.getTamanho() == "GG") {
                            sTamanho.setSelection(4);
                        }
                    }
                    if (testeTamanho == "Calça" || testeTamanho == "Sapato") {
                        sTamanho.setAdapter(spinnerArrayAdapterNUM);
                        if(roupa.getTamanho() == "34") {
                            sTamanho.setSelection(0);
                        }else if(roupa.getTamanho() == "36"){
                            sTamanho.setSelection(1);
                        }else if(roupa.getTamanho() == "38") {
                            sTamanho.setSelection(2);
                        }else if(roupa.getTamanho() == "40") {
                            sTamanho.setSelection(3);
                        }else if(roupa.getTamanho() == "42") {
                            sTamanho.setSelection(4);
                        }else if(roupa.getTamanho() == "44") {
                            sTamanho.setSelection(5);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });


        Button bAlterar = (Button) findViewById(R.id.bAlterarRoupa);
        Button bCancelar = (Button) findViewById(R.id.bCancelar);

            bAlterar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String referencia = sReferencia.getSelectedItem().toString();
                    String nome = tNomeRoupa.getText().toString();
                    float preco = Float.valueOf(tPreco.getText().toString());
                    String cor = tCor.getText().toString();
                    String material = tMaterial.getText().toString();
                    String detalhes = tDetalhes.getText().toString();
                    String tamanho = sTamanho.getSelectedItem().toString();
                    roupa.setReferencia(referencia);
                    roupa.setNome(nome);
                    roupa.setPreco(preco);
                    roupa.setCor(cor);
                    roupa.setMaterial(material);
                    roupa.setDetalhes(detalhes);
                    roupa.setTamanho(tamanho);

                    String resultado = crud.alteraRoupa(roupa);
                    Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                    Intent at = new Intent(AlteraRoupaActivity.this, ConsultaRoupaActivity.class);
                    startActivity(at);
                    finish();


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
