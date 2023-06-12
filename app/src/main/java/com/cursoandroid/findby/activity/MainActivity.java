package com.cursoandroid.findby.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cursoandroid.findby.R;
import com.cursoandroid.findby.adapter.AdapterCep;
import com.cursoandroid.findby.entidades.CEP;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    DatabaseReference enderecos = reference.child("enderecos");

    List<CEP> listaCep;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recyclerView);
        listaCep = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        enderecos.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaCep.clear();

                for(DataSnapshot current_user:snapshot.getChildren()){

                    CEP c = new CEP();
                    c.setNome(current_user.child("nome").getValue().toString());
                    c.setCep(current_user.child("cep").getValue().toString());
                    c.setLocalidade(current_user.child("localidade").getValue().toString());
                    c.setBairro(current_user.child("bairro").getValue().toString());
                    c.setComplemento(current_user.child("complemento").getValue().toString());
                    c.setLogradouro(current_user.child("logradouro").getValue().toString());
                    listaCep.add(c);
                }
                AdapterCep adapterCep = new AdapterCep(listaCep);
                recyclerView.setAdapter(adapterCep);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), TelaCadastro.class);
            startActivity(intent);
            }
        });
    }
}