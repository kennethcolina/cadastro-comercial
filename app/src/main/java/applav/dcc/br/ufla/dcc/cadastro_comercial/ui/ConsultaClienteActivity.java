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
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Pessoa;

/**
 * Created by João Paulo on 26/07/2016.
 */
public class ConsultaClienteActivity extends AppCompatActivity {

    private ListView listaCli;
    private ArrayList<String> clientes = null;
    private ArrayList<Pessoa> itens = null;
    private BD_ControleDAO crud;
    private EditText tBusca;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultacliente);
        crud = new BD_ControleDAO(getBaseContext());
        clientes = new ArrayList<String>();
        itens = crud.carregaCli();
        tBusca = (EditText) findViewById(R.id.tBuscarCliente);

        Collections.sort(itens);
        for(Pessoa cliente : itens){
            clientes.add(cliente.getNome()+" "+cliente.getSobrenome() +"  "+ cliente.getCPF());
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,clientes);
        listaCli = (ListView) findViewById(R.id.listViewCli);
        listaCli.setAdapter(adapter);
        registerForContextMenu(listaCli);


        tBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);}

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listaCli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listaCli.getItemAtPosition(position);
                String resultado = o.toString();
                Intent it = new Intent(ConsultaClienteActivity.this, ExibeDetalhesClienteActivity.class);
                it.putExtra("detalhesCliente", resultado);
                startActivity(it);
            }
        });

    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Selecione uma ação:");
        menu.add(0,v.getId(),0,"Editar");
        menu.add(0,v.getId(),0,"Excluir");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle() == "Editar"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int indice = info.position;
            Object o = listaCli.getItemAtPosition(indice);
            String result = o.toString();
            String[] divideResultado = result.split(Pattern.quote(" "));
            Intent it2 = new Intent(ConsultaClienteActivity.this, AlteraClienteActivity.class);
            it2.putExtra("CPFcliente", divideResultado[divideResultado.length-1]);
            startActivity(it2);
            finish();

        } else if(item.getTitle() == "Excluir"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int indice = info.position;
            Object o = listaCli.getItemAtPosition(indice);
            String result = o.toString();
            String[] divideResultado = result.split(Pattern.quote(" "));
            crud.deletaCliente(divideResultado[divideResultado.length-1]);
            Intent at = new Intent(ConsultaClienteActivity.this, ConsultaClienteActivity.class);
            startActivity(at);
            finish();
            Toast.makeText(getApplicationContext(),"Excluido",Toast.LENGTH_LONG).show();
        }else{
            return false;
        }
        return true;
    }

}
