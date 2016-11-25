package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.BD_ControleDAO;
import applav.dcc.br.ufla.dcc.cadastro_comercial.controle.Mascara;
import applav.dcc.R;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Cliente;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Funcionario;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Roupa;
import applav.dcc.br.ufla.dcc.cadastro_comercial.recurso.Venda;

/**
 * Created by João Paulo on 26/07/2016.
 */
public class VendaActivity extends AppCompatActivity {

    //spinner
    private Funcionario funcionario;
    private  EditText tCPFClienteVenda;
    private  String produtoSelecionado;
    //private  String codigo = "";
    private  ArrayList<String> itens = new ArrayList<String>();
    private  String novoItem;
    private   ArrayList<Roupa> roupasVenda = new ArrayList<Roupa>();
    private   ArrayAdapter<String> adapter;
    private   ListView listViewVenda;
    private   ArrayAdapter<String> spinnerArrayAdapterVenda;
    private   Spinner spinnerVenda;
    private   TextView textTotal;
    private   double totalCompra = 0;
    private   String funcCPF = "";
    private BD_ControleDAO crud;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);

        funcCPF = this.getIntent().getStringExtra("funcionarioCPF");
        crud = new BD_ControleDAO(getBaseContext());
        textTotal = (TextView) findViewById(R.id.TextSomaValor);
        spinnerVenda = (Spinner) findViewById(R.id.spinnerVenda);

        tCPFClienteVenda = (EditText) findViewById(R.id.tCPFclienteVenda);
        tCPFClienteVenda.addTextChangedListener(Mascara.insert("###.###.###-##",tCPFClienteVenda));
        Button bAddCarrinho = (Button) findViewById(R.id.bAddCarrinho);
        Button bVender = (Button) findViewById(R.id.bVender);

        ArrayList<String> produtos = null;
        produtos = crud.carregaVendaRoupa();

        ArrayAdapter<String> arrayAdapterVenda = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, produtos);
        spinnerArrayAdapterVenda = arrayAdapterVenda;
        spinnerArrayAdapterVenda.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerVenda.setAdapter(spinnerArrayAdapterVenda);


        listViewVenda = (ListView) findViewById(R.id.listViewVenda);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itens);
        listViewVenda.setAdapter(adapter);

        bAddCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerVenda.getSelectedItem() == null){
                    Toast.makeText(getApplicationContext(), "Nenhum produto cadastrado.", Toast.LENGTH_LONG).show();
                }else {
                    produtoSelecionado = spinnerVenda.getSelectedItem().toString();
                    String[] divideResultado = produtoSelecionado.split(Pattern.quote(" "));
                    String resultado = divideResultado[0];
                    Roupa r = crud.procuraRoupa(resultado);
                    novoItem = r.getCodigo() + ":   " + r.getNome() + "     R$" + r.getPreco();
                    atualizarListViewVendas();
                    atualizarTotal(r);

                }

            }
        });

        bVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Roupa> roupas = carregaArrayRoupas();
                funcionario = crud.procuraFunc(funcCPF);
                String CPFcliente = tCPFClienteVenda.getText().toString();
                Cliente cliente;
                cliente = crud.procuraCliente(CPFcliente);
                if(cliente == null){
                    tCPFClienteVenda.setError("CPF inválido");
                    return;
                }
                String dataVenda = DateFormat.getDateInstance().format(new Date());
                Venda venda = new Venda(0,funcionario,cliente,roupas,dataVenda);
                ArrayList<String> codigosRoupas= new ArrayList<String>();
                for(Roupa r : roupas){
                    codigosRoupas.add(r.getCodigo());
                }
                int idVenda = crud.insereVenda(venda.getFuncionario().getCPF(),venda.getCliente().getCPF(),venda.getDataVenda(),codigosRoupas);
                if(idVenda > 0){
                    Toast.makeText(getApplicationContext(), "Venda realizada com sucesso!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Erro na venda", Toast.LENGTH_LONG).show();
                }
                venda.setIdVenda(idVenda);
                finish();
            }
        });

    }

    private void atualizarListViewVendas() {
        itens.add(novoItem);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itens);
        listViewVenda.setAdapter(adapter);
    }

    private ArrayList<Roupa> carregaArrayRoupas(){
        for(String s : itens){
            String[] divideString = s.split(Pattern.quote(":"));
            String res = divideString[0];
            Roupa r = crud.procuraRoupa(res);
            roupasVenda.add(r);
        }
        return roupasVenda;
    }

    private void atualizarTotal(Roupa r){
        totalCompra += r.getPreco();
        textTotal.setText("Total: "+totalCompra);
    }

}
