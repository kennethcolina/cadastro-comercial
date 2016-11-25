package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Venda;

/**
 * Created by João Paulo on 26/07/2016.
 */
public class ConsultaVendaActivity extends AppCompatActivity {

    private ListView lista;
    private BD_ControleDAO crud;
    private ArrayList<Venda> itens = null;
    private ArrayList<String> vendas = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultavenda);
        crud = new BD_ControleDAO(getBaseContext());
        itens = crud.carregaVendas();
        for (Venda venda : itens) {
            vendas.add(venda.getIdVenda() + "-   " +venda.getCliente().getCPF()+"    "+venda.getCliente().getNome() + "    " + venda.getDataVenda());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vendas);
        lista = (ListView) findViewById(R.id.listVVendas);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = lista.getItemAtPosition(position);
                String resultado = o.toString();
                Intent it = new Intent(ConsultaVendaActivity.this, ExibeDetalhesVendaActivity.class);
                it.putExtra("detalhesVenda", resultado);
                startActivity(it);
            }
        });

    }
}
