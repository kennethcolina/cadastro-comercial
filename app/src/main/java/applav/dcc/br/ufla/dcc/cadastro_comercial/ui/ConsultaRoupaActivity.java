package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Roupa;

/**
 * Created by João Paulo on 26/07/2016.
 */
public class ConsultaRoupaActivity extends AppCompatActivity {

    private ListView lista;
    private BD_ControleDAO crud;
    private ArrayList<Roupa> itens = new ArrayList<Roupa>();
    private ArrayList<String> roupas = new ArrayList<String>();
    private EditText tBuscarRoupa;
    private ArrayAdapter<String> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultaroupa);
        crud = new BD_ControleDAO(getBaseContext());
        tBuscarRoupa = (EditText) findViewById(R.id.tBuscarRoupa);
        itens = crud.carregaRoupas();
        Collections.sort(itens);
        for (Roupa roupa : itens) {
            roupas.add(roupa.getNome()+"  "+roupa.getCodigo());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roupas);
        lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(adapter);
        registerForContextMenu(lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = lista.getItemAtPosition(position);
                String resultado = o.toString();
                Intent it = new Intent(ConsultaRoupaActivity.this, ExibeDetalhesActivity.class);
                it.putExtra("detalhes", resultado);
                startActivity(it);
            }
        });

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
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Selecione uma ação:");
        menu.add(0, v.getId(), 0, "Editar");
        menu.add(0, v.getId(), 0, "Excluir");


    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle() == "Editar"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int indice = info.position;
            Object o = lista.getItemAtPosition(indice);
            String result = o.toString();
            String[] divideResultado = result.split(Pattern.quote(" "));
            Intent it2 = new Intent(ConsultaRoupaActivity.this, AlteraRoupaActivity.class);
            it2.putExtra("codigoRoupa", divideResultado[divideResultado.length-1]);
            startActivity(it2);
            finish();
        } else if(item.getTitle() == "Excluir"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int indice = info.position;
            Object o = lista.getItemAtPosition(indice);
            String result = o.toString();
            String[] divideResultado = result.split(Pattern.quote(" "));
            crud.deletaRoupa(divideResultado[divideResultado.length-1]);
            Toast.makeText(getApplicationContext(),"Excluido",Toast.LENGTH_LONG).show();
            Intent at = new Intent(ConsultaRoupaActivity.this, ConsultaRoupaActivity.class);
            startActivity(at);
            finish();
        }else{
            return false;
        }
        return true;
    }

}
