package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.regex.Pattern;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Funcionario;

/**
 * Created by João Paulo on 03/08/2016.
 */
public class ExibeDetalhesFuncActivity extends AppCompatActivity {
    private  ListView listaDetalhes;
    private BD_ControleDAO crud;
    private ArrayList<String> funcionarios = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibefuncdetalhes);
        String resultadoFunc = this.getIntent().getStringExtra("detalhesFunc");
        String[] divideResultado = resultadoFunc.split(Pattern.quote(" "));
        crud = new BD_ControleDAO(getBaseContext());
        Funcionario funcionario = crud.procuraFunc(divideResultado[divideResultado.length-1]);
        funcionarios.add("CPF: " + funcionario.getCPF());
        funcionarios.add("Nome: " + funcionario.getNome());
        funcionarios.add("Sobrenome: " +funcionario.getSobrenome());
        funcionarios.add("Data de nascimento: " + funcionario.getDataNascimento());
        funcionarios.add("Email: " + funcionario.getEmail());
        funcionarios.add("Telefone: " + funcionario.getTelefone());
        funcionarios.add("Salário: " + funcionario.getSalario());
        funcionarios.add("Comissão: " + funcionario.getComissao());
        funcionarios.add("Nível: " + funcionario.getNivel());
        funcionarios.add("Data de início: " + funcionario.getDataInicio());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,funcionarios);
        listaDetalhes = (ListView) findViewById(R.id.listViewExibeFuncDetalhes);
        listaDetalhes.setAdapter(adapter);

    }
}
