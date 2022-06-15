package com.example.nummus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList date_id, time_id, amount_id, paymentmethod_id, note_id, category_id, currency_id, key_id;

    public MyAdapter(Context context, ArrayList key_id, ArrayList date_id, ArrayList time_id, ArrayList amount_id, ArrayList paymentmethod_id, ArrayList note_id, ArrayList category_id,ArrayList currency_id) {
        this.context = context;
        this.date_id = date_id;
        this.time_id = time_id;
        this.amount_id = amount_id;
        this.paymentmethod_id = paymentmethod_id;
        this.note_id = note_id;
        this.category_id = category_id;
        this.currency_id = currency_id;
        this.key_id = key_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.key_id.setText(String.valueOf(key_id.get(position)));
        holder.date_id.setText(String.valueOf(date_id.get(position)));
        holder.time_id.setText(String.valueOf(time_id.get(position)));
        holder.amount_id.setText(String.valueOf(amount_id.get(position)));
        holder.paymentmethod_id.setText(String.valueOf(paymentmethod_id.get(position)));
        holder.note_id.setText(String.valueOf(note_id.get(position)));
        holder.category_id.setText(String.valueOf(category_id.get(position)));
        holder.currency_id.setText(String.valueOf(currency_id.get(position)));
    }

    @Override
    public int getItemCount() {
        return date_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_id, time_id, amount_id, paymentmethod_id, note_id, category_id, currency_id, key_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            key_id = itemView.findViewById(R.id.textkeyUser);
            date_id = itemView.findViewById(R.id.textname);
            time_id = itemView.findViewById(R.id.textemail);
            amount_id = itemView.findViewById(R.id.textage);
            paymentmethod_id = itemView.findViewById(R.id.textPaymentMethod);
            note_id = itemView.findViewById(R.id.textNote);
            category_id = itemView.findViewById(R.id.textCategory);
            currency_id = itemView.findViewById(R.id.textcurrency);
        }
    }
}