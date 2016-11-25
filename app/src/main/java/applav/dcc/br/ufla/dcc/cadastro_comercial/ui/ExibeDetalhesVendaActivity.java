package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.regex.Pattern;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Roupa;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Venda;

/**
 * Created by João Paulo on 03/08/2016.
 */
public class ExibeDetalhesVendaActivity extends AppCompatActivity {
    private ListView listaDetalhes;
    private BD_ControleDAO crud;
    private ArrayList<String> vendas = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibevendadetalhes);

        String resultadoVenda = this.getIntent().getStringExtra("detalhesVenda");
        String[] divideResultado = resultadoVenda.split(Pattern.quote("-"));
        int idBusca = Integer.parseInt(divideResultado[0]);
        crud = new BD_ControleDAO(getBaseContext());
        Venda venda = crud.procuraVenda(idBusca);
        double soma = crud.somaPrecos(idBusca) + crud.somaPrecosBackup(idBusca);
                vendas.add("ID: " + venda.getIdVenda());
                vendas.add("Nome do Vendedor: " + venda.getFuncionario().getNome() + " " +venda.getFuncionario().getSobrenome());
                vendas.add("CPF do Vendedor: " + venda.getFuncionario().getCPF());
                vendas.add("Nome do Cliente: " + venda.getCliente().getNome() + " " + venda.getCliente().getSobrenome());
                vendas.add("CPF do Cliente: " + venda.getCliente().getCPF());
                vendas.add("Data da venda: " + venda.getDataVenda());
                vendas.add("Produtos Vendidos:");
                for(Roupa r : venda.getRoupas()){
                    vendas.add(r.getCodigo() + "  " + r.getNome() + "  " + r.getPreco());
                }
                vendas.add("Total da venda: " + soma);
                double pf = soma * 0.1;
                int ptsFid =  (int) pf;
                venda.getCliente().setPtsFidelidade(ptsFid);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vendas);
        listaDetalhes = (ListView) findViewById(R.id.listViewExibeVendaDetalhes);
        listaDetalhes.setAdapter(adapter);

    }
}
