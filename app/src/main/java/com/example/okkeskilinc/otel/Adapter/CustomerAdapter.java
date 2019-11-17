package com.example.okkeskilinc.otel.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.okkeskilinc.otel.Classes.Customer;
import com.example.okkeskilinc.otel.CustomerDetail;
import com.example.okkeskilinc.otel.R;
import com.example.okkeskilinc.otel.TarihDetailActivity;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    List<Customer> customerList;
    Context context;
    private ItemClickListener clickListener;
    public CustomerAdapter(Context context, List<Customer> customerList) {

        this.customerList = customerList;
        this.context = context;
    }
    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.customerName.setText("Müşteri Adı: "+customerList.get(position).getName());
        holder.customerAddress.setText("Adres: "+customerList.get(position).getAddress());
        holder.customerPhone.setText("Telefon: "+customerList.get(position).getTelephone());
        holder.customerTC.setText("TC No: "+customerList.get(position).getTC());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView customerName;
        public TextView customerAddress;
        public TextView customerTC;
        public TextView customerPhone;
        public ImageView customerInfo;
        public ViewHolder(View view) {
            super(view);
            customerName= (TextView)view.findViewById(R.id.customer_name);
            customerAddress= (TextView)view.findViewById(R.id.customer_address);
            customerTC= (TextView)view.findViewById(R.id.customer_TC);
            customerPhone=(TextView)view.findViewById(R.id.customer_telephone);
            customerInfo=(ImageView) view.findViewById(R.id.img_info);
            view.setTag(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener!=null){
                clickListener.onClick(v,getAdapterPosition());}
        }
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
