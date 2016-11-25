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
public class MenuConsultasActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuconsultas);
        Button bListarRoupas = (Button) findViewById(R.id.bConsultaRoupa);
        Button bListarFunc = (Button) findViewById(R.id.bConsultaFunc);
        Button bListarCliente = (Button) findViewById(R.id.bConsultaCliente);
        Button bListarVendas =  (Button) findViewById(R.id.bListaVendas);

        bListarRoupas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuConsultasActivity.this, ConsultaRoupaActivity.class);
                startActivity(it);
            }
        });

        bListarFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuConsultasActivity.this,ConsultaFuncActivity.class);
                startActivity(it);
            }
        });

        bListarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuConsultasActivity.this,ConsultaClienteActivity.class);
                startActivity(it);
            }
        });
        bListarVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuConsultasActivity.this,ConsultaVendaActivity.class);
                startActivity(it);            }
        });
    }
}
