package com.example.lucky.alarmsystemfull.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucky.alarmsystemfull.R;

import java.util.List;

/**
 * Created by Lucky on 08.02.2018.
 */

public class PhonesAdapter extends RecyclerView.Adapter<PhonesAdapter.MyViewHolder> {

    private List<String> phones;
    private onClick listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView phone;
        public View btnDelete;

        public MyViewHolder(View view) {
            super(view);
            phone = view.findViewById(R.id.phone);
            btnDelete = view.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.click(getAdapterPosition());
                }
            });
        }
    }

    public PhonesAdapter(List<String> phones, Context context) {
        this.phones = phones;
        this.listener = (onClick) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phones, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String phone = phones.get(position);
        holder.phone.setText(phone);
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    public interface onClick{
        void click(int position);
    }
}