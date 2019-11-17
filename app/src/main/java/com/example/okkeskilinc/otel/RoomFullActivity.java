package com.example.okkeskilinc.otel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.okkeskilinc.otel.Classes.Rooms;
import com.example.okkeskilinc.otel.Classes.Tarihler;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by OKKES KILINC on 19.03.2018.
 */

public class RoomFullActivity extends Fragment {

    TextView tvDolu,tvEnYakinTarih,tvRoomCount,tvCustomerCount;
    int roomDC,roomBC,CCount;
    List<Date> dateList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_room_full,container,false);

        tvDolu=(TextView)v.findViewById(R.id.tv_dolu);
        tvRoomCount=(TextView)v.findViewById(R.id.tv_room_count);
        tvCustomerCount=(TextView)v.findViewById(R.id.tv_customer_count);
        tvEnYakinTarih=(TextView)v.findViewById(R.id.tv_en_yakin);

        DatabaseReference defCustomerCount=FirebaseDatabase.getInstance().getReference("Customer");
        defCustomerCount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CCount=0;
                for (DataSnapshot dc:dataSnapshot.getChildren())
                {
                    CCount++;
                }
                tvCustomerCount.setText(CCount+" ");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Rooms");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomDC=0;roomBC=0;
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if (ds.child("durum").getValue(String.class).equals("bos"))
                    {roomBC=roomBC+1;}
                    else if (ds.child("durum").getValue(String.class).equals("DOLU"))
                    {roomDC=roomDC+1;}
                }
                String rDoluC=roomDC+"";
                int rC=roomBC+roomDC;
                tvRoomCount.setText(rC+"");
                tvDolu.setText(rDoluC);//sorgu ile dolu oda sayisi alinir.
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        final DatabaseReference defTarih=FirebaseDatabase.getInstance().getReference("Tarihler");
        dateList=new ArrayList<Date>();
        final DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        defTarih.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dateList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Tarihler tarihler=ds.getValue(Tarihler.class);
                    try {
                        dateList.add(dateFormat.parse(tarihler.getCostumerOut()));
                    } catch (ParseException e) {e.printStackTrace();}
                }
                Collections.sort(dateList);
                Date date=dateList.get(0);
                //String enYakin=dateList.get(0).toString();
                String enYakin=dateFormat.format(date);
                tvEnYakinTarih.setText(enYakin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }
}
