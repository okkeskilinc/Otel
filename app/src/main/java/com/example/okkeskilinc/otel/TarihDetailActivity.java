package com.example.okkeskilinc.otel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.okkeskilinc.otel.Adapter.TarihAdapter;
import com.example.okkeskilinc.otel.Classes.Tarihler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OKKES KILINC on 19.03.2018.
 */

public class TarihDetailActivity extends Fragment {

    private DatabaseReference defTarih;
    private TextView tarihCusTC;
    private TarihAdapter tarihAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    List<Tarihler> tarihList;
    String customerTc;
    @Override
    public void onStart() {
        super.onStart();

        customerTc=getArguments().getString("customerTc");
        tarihCusTC.setText(customerTc);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_tarih_detail,container,false);
        customerTc=getArguments().getString("customerTc");

        defTarih=FirebaseDatabase.getInstance().getReference("Tarihler");

        tarihCusTC=(TextView)v.findViewById(R.id.t_cus_tc);
        recyclerView=(RecyclerView)v.findViewById(R.id.tarih_recycler_view);
        tarihList=new ArrayList<>();
        defTarih.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tarihList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    if (ds.child("costumerTc").getValue(String.class).equals(customerTc)){
                        Tarihler tarihler=ds.getValue(Tarihler.class);
                        tarihList.add(tarihler);}
                }

                recyclerView.setHasFixedSize(true);
                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);

                tarihAdapter = new TarihAdapter(getContext(), tarihList);
                recyclerView.setAdapter(tarihAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return v;
    }
}
