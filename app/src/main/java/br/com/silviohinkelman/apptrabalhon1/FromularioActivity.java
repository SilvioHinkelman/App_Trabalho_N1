package br.com.silviohinkelman.apptrabalhon1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FromularioActivity extends AppCompatActivity {

    private EditText etMarca;
    private EditText etModelo;
    private Spinner spColors;
    private Button btnSalvar;
    private String acao;
    private Cars cars;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fromulario);

        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        spColors = findViewById(R.id.spColors);
        btnSalvar = findViewById(R.id.btnSalvar);
        checkBox = (CheckBox)findViewById(R.id.checkbox_id);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        acao = getIntent().getStringExtra("acao");
        if(acao.equals("editar")){
            carregarFormulario();
        }
    }

    private void carregarFormulario(){
        int idCar = getIntent().getIntExtra("idCar",0);
        cars = CarDAO.getCarsById(this, idCar);
        etMarca.setText(cars.getMarca());
        etModelo.setText(cars.getModelo());
        String[] colors = getResources().getStringArray(R.array.colors);

        for(int x =0 ; x < colors.length; x++){
            if(cars.getColor().equals(colors[x])){
                spColors.setSelection(x);
                break;
            }
        }
    }

    private void salvar(){
        String marca = etMarca.getText().toString();
        String modelo = etModelo.getText().toString();

        if(marca.isEmpty() || modelo.isEmpty() || spColors.getSelectedItemPosition() ==0 || !checkBox.isChecked() ){
            Toast.makeText(this, R.string.complete, Toast.LENGTH_LONG).show();
        }else{

            if(acao.equals("inserir")) {
                cars = new Cars();
            }
            cars.setMarca( marca);
            cars.setModelo(modelo);
            cars.setColor(spColors.getSelectedItem().toString());

            if(acao.equals("inserir")) {
                CarDAO.inserir(this, cars);
                etMarca.setText("");
                etModelo.setText("");
                spColors.setSelection(0 , true);
                checkBox.setChecked(false);
            }else {
                CarDAO.editar(this, cars);
                finish();
            }
        }
    }
}