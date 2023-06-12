package com.cursoandroid.findby.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cursoandroid.findby.R;
import com.cursoandroid.findby.api.CEPService;
import com.cursoandroid.findby.entidades.CEP;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TelaCadastro extends AppCompatActivity {

    private EditText entradaNome, entradaCep, entradaComplemento;
    private Button btcadastrar;

    private Retrofit retrofit;

    private TextView resultado;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    DatabaseReference enderecos = reference.child("enderecos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        entradaNome = findViewById(R.id.entradaNome);
        entradaCep = findViewById(R.id.entradaCEP);
        entradaComplemento = findViewById(R.id.entradaComplemento);
        btcadastrar = findViewById(R.id.btEnviarCadastro);




        retrofit = new Retrofit.Builder().baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        btcadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecuperaCEPRetrofit();
                Toast.makeText(getApplicationContext(),"Endereço cadastrado",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void RecuperaCEPRetrofit(){

        CEPService cepService = retrofit.create(CEPService.class);
        Call<CEP> call = cepService.RecuperaCep(entradaCep.getText().toString());
        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if(response.isSuccessful()){
                    CEP cepRetro = response.body();
                    CEP cepBanco = new CEP();
                    cepBanco.setCep(cepRetro.getCep());
                    cepBanco.setBairro(cepRetro.getBairro());
                    cepBanco.setComplemento(entradaComplemento.getText().toString());
                    cepBanco.setLocalidade(cepRetro.getLocalidade());
                    cepBanco.setNome(entradaNome.getText().toString());
                    cepBanco.setLogradouro(cepRetro.getLogradouro());
                    enderecos.push().setValue(cepBanco);
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });
    }
}