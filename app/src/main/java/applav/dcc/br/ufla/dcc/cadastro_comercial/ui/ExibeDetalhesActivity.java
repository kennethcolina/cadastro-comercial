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

/**
 * Created by João Paulo on 03/08/2016.
 */
public class ExibeDetalhesActivity extends AppCompatActivity {
    private  ListView listaDetalhes;
    private BD_ControleDAO crud;
    private ArrayList<Roupa> itens = null;
    private ArrayList<String> roupas = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibedetalhes);
        String resultadoRoupa = this.getIntent().getStringExtra("detalhes");
        String[] divideResultado = resultadoRoupa.split(Pattern.quote(" "));
        crud = new BD_ControleDAO(getBaseContext());
        Roupa r = crud.procuraRoupa(divideResultado[divideResultado.length-1]);
                roupas.add("Codigo: " + r.getCodigo());
                roupas.add("Nome: " + r.getNome());
                roupas.add("Tipo: " + r.getReferencia());
                roupas.add("Preço: " + r.getPreco());
                roupas.add("Cor: " + r.getCor());
                roupas.add("Tamanho: " + r.getTamanho());
                roupas.add("Material: " + r.getMaterial());
                roupas.add("Detalhes: " + r.getDetalhes());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roupas);
        listaDetalhes = (ListView) findViewById(R.id.listViewExibeDetalhes);
        listaDetalhes.setAdapter(adapter);

    }
}
