package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Roupa;

/**
 * Created by João Paulo on 26/07/2016.
 */
public class ConsultaRoupaFuncActivity extends AppCompatActivity {

    private ListView lista;
    private BD_ControleDAO crud;
    private ArrayList<Roupa> itens = null;
    private ArrayList<String> roupas = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private EditText tBuscarRoupa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultaroupa);
        crud = new BD_ControleDAO(getBaseContext());
        itens = crud.carregaRoupas();
        tBuscarRoupa = (EditText) findViewById(R.id.tBuscarRoupa);
        Collections.sort(itens);
        for (Roupa roupa : itens) {
            roupas.add(roupa.getNome()+"  " + roupa.getCodigo());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roupas);
        lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(adapter);
        registerForContextMenu(lista);

        tBuscarRoupa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);           }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = lista.getItemAtPosition(position);
                String resultado = o.toString();
                Intent it = new Intent(ConsultaRoupaFuncActivity.this,ExibeDetalhesActivity.class);
                it.putExtra("detalhes", resultado);
                startActivity(it);
            }
        });
    }



}
