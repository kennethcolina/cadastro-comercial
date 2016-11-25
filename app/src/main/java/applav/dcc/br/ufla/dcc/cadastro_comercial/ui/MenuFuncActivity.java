package applav.dcc.br.ufla.dcc.cadastro_comercial.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import applav.dcc.R;

/**
 * Created by João Paulo on 19/07/2016.
 */
public class MenuFuncActivity extends AppCompatActivity{
    private String cpfFunc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menufunc);
        Button bConsultas = (Button) findViewById(R.id.button6);
        Button bCadastroCliente = (Button) findViewById(R.id.button4);
        Button bVender = (Button) findViewById(R.id.button2);
        cpfFunc = this.getIntent().getStringExtra("funcionarioCPF");
        bCadastroCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuFuncActivity.this, ClienteActivity.class);
                startActivity(it);
            }
        });

        bVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuFuncActivity.this, VendaActivity.class);
                it.putExtra("funcionarioCPF", cpfFunc);
                startActivity(it);
            }
        });

        bConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuFuncActivity.this, MenuConsultasFuncActivity.class);
                startActivity(it);
            }
        });
    }
}
