package com.example.simulacropc02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.simulacropc02.Api.ServiceRevistaApi;
import com.example.simulacropc02.Entity.Revista;
import com.example.simulacropc02.Util.ConnectionRest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre;
    Spinner spnFrecuencia, spnPais, spnEstado;
    Button btnEnviar;

    ServiceRevistaApi apiRevista = null;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtRevistaNombres);
        spnFrecuencia = findViewById(R.id.spnRevistaFrecuencia);
        spnPais = findViewById(R.id.spnRevistaPais);
        spnEstado = findViewById(R.id.spnRevistaEstado);
        btnEnviar = findViewById(R.id.idbtnEnvAut);

        apiRevista = ConnectionRest.getConnection().create(ServiceRevistaApi.class);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nom = txtNombre.getText().toString();
                String fre = spnFrecuencia.getSelectedItem().toString();
                String pai = spnPais.getSelectedItem().toString();
                int est = spnEstado.getSelectedItemPosition();

                Revista obj = new Revista();
                obj.setNombre(nom);
                obj.setFrecuencia(fre);
                obj.setPais(pai);
                obj.setEstado(est);

                insertaRevista(obj);
            }
        });

    }

    public void insertaRevista(Revista obj){
        Call<Revista> call = apiRevista.insertaRevista(obj);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if(response.isSuccessful()){
                    Revista obj = response.body();
                    if (obj == null) {
                        mensaje("ERROR -> " + " " + "No se insertó");
                    }else{
                        mensaje("ÉXITO ->" + " " + "Se insertó correctamente : " + obj.getIdRevista());
                    }
                }else{
                    mensaje("ERROR -> " + "Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensaje("ERROR" + t.getMessage());
            }
        });
    }

    void mensaje(String msg){
        Toast toast1 =  Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG);
        toast1.show();
    }
}