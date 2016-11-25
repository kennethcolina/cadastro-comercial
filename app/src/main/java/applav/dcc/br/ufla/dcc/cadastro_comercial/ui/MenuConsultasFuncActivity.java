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
public class MenuConsultasFuncActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuconsultasfunc);
        Button bListarRoupas = (Button) findViewById(R.id.bConsultaRoupa);
        Button bListarCliente = (Button) findViewById(R.id.bConsultaCliente);
        Button bListarVendas =  (Button) findViewById(R.id.bListaVendas);

        bListarRoupas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuConsultasFuncActivity.this, ConsultaRoupaFuncActivity.class);
                startActivity(it);
            }
        });
        bListarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuConsultasFuncActivity.this,ConsultaClienteActivity.class);
                startActivity(it);
            }
        });
        bListarVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuConsultasFuncActivity.this,ConsultaVendaActivity.class);
                startActivity(it);            }
        });
    }
}
