package com.example.okkeskilinc.otel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.okkeskilinc.otel.Classes.Tarihler;
import com.example.okkeskilinc.otel.R;

import java.util.List;

public class TarihAdapter extends RecyclerView.Adapter<TarihAdapter.ViewHolder> {
    List<Tarihler> tarihList;
    Context context;

    public TarihAdapter(Context context,List<Tarihler> tarihList){
        this.context=context;
        this.tarihList=tarihList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tarihCusTC;
        public TextView tarihEntry;
        public TextView tarihOut;
        public TextView tarihRoom;
        public ViewHolder(View view) {
            super(view);
            tarihCusTC=(TextView)view.findViewById(R.id.tarih_cus_tc);
            tarihEntry=(TextView)view.findViewById(R.id.tarih_entery);
            tarihOut=(TextView)view.findViewById(R.id.tarih_out);
            tarihRoom=(TextView)view.findViewById(R.id.tarih_room);
            view.setTag(view);
        }
    }
    @Override
    public void onBindViewHolder(TarihAdapter.ViewHolder holder, int position) {
        holder.tarihRoom.setText("Oda No: "+tarihList.get(position).getRoomId());
        holder.tarihOut.setText("Müşteri Çıkış: "+tarihList.get(position).getCostumerOut());
        holder.tarihEntry.setText("Müşteri Giriş: "+tarihList.get(position).getCostumerEntry());
        holder.tarihCusTC.setText("Müşteri TC No: "+tarihList.get(position).getCostumerTc());

    }
    @Override
    public TarihAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.tarih_item,parent,false);
        return new ViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return tarihList.size();
    }


}
