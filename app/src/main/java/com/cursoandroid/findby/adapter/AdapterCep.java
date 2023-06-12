package com.cursoandroid.findby.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cursoandroid.findby.R;
import com.cursoandroid.findby.entidades.CEP;

import java.util.ArrayList;
import java.util.List;

public class AdapterCep extends RecyclerView.Adapter<AdapterCep.MyViewHolder> {

    List<CEP> listaCep = new ArrayList<>();

    public AdapterCep(List<CEP> listaCep) {
        this.listaCep = listaCep;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cepLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_cep,parent,false);
        return new MyViewHolder(cepLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CEP c = listaCep.get(position);

        holder.bairro.setText(c.getBairro());
        holder.nome.setText(c.getNome());
        holder.cep.setText(c.getCep());
        holder.complemento.setText(c.getComplemento());
        holder.logradouro.setText(c.getLogradouro());
        holder.localidade.setText(c.getLocalidade());

    }

    @Override
    public int getItemCount() {
        return listaCep.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome, cep, logradouro, complemento, bairro,localidade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.resultadoNome);
            cep = itemView.findViewById(R.id.resultadoCep);
            logradouro = itemView.findViewById(R.id.resultadoLogradouro);
            complemento = itemView.findViewById(R.id.resultadoComplemento);
            bairro = itemView.findViewById(R.id.resultadoBairro);
            localidade = itemView.findViewById(R.id.resultadoLocalidade);

        }
    }

}