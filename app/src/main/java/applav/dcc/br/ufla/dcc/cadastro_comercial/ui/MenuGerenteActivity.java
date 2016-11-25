package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import applav.dcc.R;

/**
 * Created by João Paulo on 26/07/2016.
 */
public class MenuGerenteActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menugerente);
        Button bCadastroRoupa = (Button) findViewById(R.id.bCadastroRoupa);
        Button bCadastroCliente = (Button) findViewById(R.id.bCadastroCliente);
        Button bCadastroFuncionario = (Button) findViewById(R.id.bCadastroFuncionario);
        Button bConsulta = (Button) findViewById(R.id.bConsulta);

        bCadastroRoupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuGerenteActivity.this, RoupaActivity.class);
                startActivity(it);
            }
        });

        bCadastroCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuGerenteActivity.this, ClienteActivity.class);
                startActivity(it);
            }
        });

        bCadastroFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuGerenteActivity.this, FuncionarioActivity.class);
                startActivity(it);
            }
        });

        bConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuGerenteActivity.this, MenuConsultasActivity.class);
                startActivity(it);
            }
        });
    }
}
