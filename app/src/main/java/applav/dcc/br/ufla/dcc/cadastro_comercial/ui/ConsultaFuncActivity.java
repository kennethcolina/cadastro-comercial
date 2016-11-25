package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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
 * Created by aluno on 29/07/16.
 */
public class  ConsultaFuncActivity extends AppCompatActivity {
    private ListView listaFunc;
    private BD_ControleDAO crud;
    private ArrayList<Pessoa> itens = null;
    private ArrayList<String> funcionarios = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ArrayList<String> selecao;
    private EditText tBuscarFunc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultafunc);
        crud = new BD_ControleDAO(getBaseContext());
        tBuscarFunc = (EditText) findViewById(R.id.tBuscarFunc);
        itens = crud.carregaFunc();
        Collections.sort(itens);
        for(Pessoa func : itens){
            funcionarios.add(func.getNome()+" "+func.getSobrenome()+"  "+func.getCPF());
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,funcionarios);
        listaFunc = (ListView) findViewById(R.id.listViewFunc);
        listaFunc.setAdapter(adapter);
        registerForContextMenu(listaFunc);
        listaFunc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listaFunc.getItemAtPosition(position);
                String resultado = o.toString();
                Intent it = new Intent(ConsultaFuncActivity.this,ExibeDetalhesFuncActivity.class);
                it.putExtra("detalhesFunc", resultado);
                startActivity(it);
            }
        });

        tBuscarFunc.addTextChangedListener(new TextWatcher() {
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
                                    ContextMenuInfo menuInfo) {
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
            Object o = listaFunc.getItemAtPosition(indice);
            String result = o.toString();
            String[] divideResultado = result.split(Pattern.quote(" "));
            Intent it2 = new Intent(ConsultaFuncActivity.this, AlteraFuncionarioActivity.class);
            it2.putExtra("CPFfunc", divideResultado[divideResultado.length-1]);
            startActivity(it2);
            finish();

            Toast.makeText(getApplicationContext(),"Editado",Toast.LENGTH_LONG).show();
        } else if(item.getTitle() == "Excluir"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int indice = info.position;
            Object o = listaFunc.getItemAtPosition(indice);
            String result = o.toString();
            String[] divideResultado = result.split(Pattern.quote(" "));
            crud.deletaFunc(divideResultado[divideResultado.length-1]);
            crud.deletaLogin(divideResultado[divideResultado.length-1]);
            Toast.makeText(getApplicationContext(),"Excluido",Toast.LENGTH_LONG).show();
            Intent at = new Intent(ConsultaFuncActivity.this, ConsultaFuncActivity.class);
            startActivity(at);
            finish();
        }else{
            return false;
        }
        return true;
    }

}