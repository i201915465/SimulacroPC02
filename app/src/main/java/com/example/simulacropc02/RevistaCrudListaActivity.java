package com.example.simulacropc02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simulacropc02.Api.ServiceRevistaApi;
import com.example.simulacropc02.Entity.Revista;
import com.example.simulacropc02.Util.ConnectionRest;
import com.example.simulacropc02.adapter.RevistaAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaCrudListaActivity extends AppCompatActivity {

    List<Revista> lstData = new ArrayList<Revista>();
    RevistaAdapter adaptador = null;
    ListView listView = null;
    ServiceRevistaApi api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_lista);

        listView = findViewById(R.id.idListaCrudRevista);
        adaptador = new RevistaAdapter(this, R.layout.activity_revista_crud_item, lstData);
        listView.setAdapter(adaptador);

        api = ConnectionRest.getConnection().create(ServiceRevistaApi.class);

        lista();
    }

    public void lista(){
        mensaje("LOG ->  En método lista 1");
        Call<List<Revista>> call = api.listaRevista();
        call.enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                mensaje("LOG ->  En método lista 2");
                if (response.isSuccessful()){
                    mensaje("LOG ->  En método lista 3");
                    List<Revista> lista = response.body();
                    lstData.clear();
                    lstData.addAll(lista);
                    adaptador.notifyDataSetChanged();
                }else{
                    mensaje("ERROR -> " + "Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {
                mensaje("ERROR -> " + "Error en la respuesta");

            }
        });
    }

    void mensaje(String msg){
        Toast toast1 = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast1.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.idMenuCrudRevista) {
            Intent intent = new Intent(this, RevistaCrudListaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuRegistroRevista) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}