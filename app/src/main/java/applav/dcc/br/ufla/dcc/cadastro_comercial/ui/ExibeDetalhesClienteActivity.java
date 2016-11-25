package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.regex.Pattern;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Cliente;

/**
 * Created by João Paulo on 03/08/2016.
 */
public class ExibeDetalhesClienteActivity extends AppCompatActivity {
    private ListView listaDetalhes;
    private BD_ControleDAO crud;
    private ArrayList<String> clientes = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibeclientedetalhes);
        String resultadoCliente = this.getIntent().getStringExtra("detalhesCliente");
        String[] divideResultado = resultadoCliente.split(Pattern.quote(" "));
        crud = new BD_ControleDAO(getBaseContext());
        Cliente cliente = crud.procuraCliente(divideResultado[divideResultado.length-1]);
        clientes.add("CPF: " + cliente.getCPF());
        clientes.add("Nome: " + cliente.getNome());
        clientes.add("Sobrenome: " +cliente.getSobrenome());
        clientes.add("Data de nascimento: " + cliente.getDataNascimento());
        clientes.add("Email: " + cliente.getEmail());
        clientes.add("Telefone: " + cliente.getTelefone());
        clientes.add("Pontos de fidelidade: " + cliente.getPtsFidelidade());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,clientes);
        listaDetalhes = (ListView) findViewById(R.id.listViewExibeClienteDetalhes);
        listaDetalhes.setAdapter(adapter);

    }
}
