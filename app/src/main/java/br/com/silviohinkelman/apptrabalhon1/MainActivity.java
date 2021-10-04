package br.com.silviohinkelman.apptrabalhon1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvCars;
    private List<Cars> listaCars;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FromularioActivity.class);
                intent.putExtra("acao", "inserir");
                startActivity(intent);
            }
        });

        lvCars = findViewById(R.id.lvCars);

        lvCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, FromularioActivity.class);
                intent.putExtra("acao", "editar");
                int idCar = listaCars.get(i).getId();
                intent.putExtra("idCar", idCar);
                startActivity(intent);
            }
        });
        //clica e segura setOnClickListener abaixo
        lvCars.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                excluir(i);
                return true;
            }
        });

        carregarLista();
    }

    private void excluir(int posicao){
        Cars carSelecionado = listaCars.get(posicao);
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Excluir");
        msg.setIcon(android.R.drawable.ic_menu_delete);
        msg.setMessage("Deseja realmente excluir este veículo -" + carSelecionado + " ?");

        msg.setNegativeButton("Cancelar", null);

       msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int which) {
               CarDAO.excluir(MainActivity.this, carSelecionado.getId() );
               carregarLista();
           }
       });
        msg.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarLista();
    }

    private void carregarLista(){

        listaCars = CarDAO.getCars(this);

        Toast.makeText(this, R.string.atualized, Toast.LENGTH_SHORT).show();
        if(listaCars.size() == 0){
            Cars fake = new Cars("Não existe","veículos","cadastrados");
            listaCars.add(fake);
            lvCars.setEnabled(false);
        }else{
            lvCars.setEnabled(true);
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, listaCars);
        lvCars.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}