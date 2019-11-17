package com.example.okkeskilinc.otel;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.okkeskilinc.otel.Adapter.RoomAdapter;
import com.example.okkeskilinc.otel.Classes.Rooms;
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

public class RoomInfoActivity extends Fragment {
    List<Rooms> listRooms;
    Button btnAddRoom;
    RecyclerView recyclerView;
    RoomAdapter roomAdapter;
    DatabaseReference defRoom;

    @Override
    public void onStart() {
        super.onStart();

        listemele();

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_room_info,container,false);

        btnAddRoom=(Button)v.findViewById(R.id.btn_room_ekle);
        recyclerView=(RecyclerView)v.findViewById(R.id.recycler_view);

        final FoodListActivity foodListActivity=new FoodListActivity();
        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomDurumUpdate();            }
        });
        listRooms=new ArrayList<>();
        defRoom=FirebaseDatabase.getInstance().getReference("Rooms");


        return v;
    }

    private void RoomDurumUpdate() {
        LinearLayout layout=new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText etNo=new EditText(getContext());
        final EditText etType=new EditText(getContext());
        final EditText etDurum=new EditText(getContext());
        final EditText etKat=new EditText(getContext());
        etNo.setHint("Name");
        etType.setHint("Type");
        etDurum.setHint("Durum");
        etKat.setHint("Kat No");
        layout.addView(etNo);
        layout.addView(etType);
        layout.addView(etKat);
        layout.addView(etDurum);
        AlertDialog.Builder b=new AlertDialog.Builder(getContext());
        b.setTitle("Add Room").setView(layout).setNegativeButton("iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Oda ekleme iptal edildi",Toast.LENGTH_LONG).show();
            }
        })
                .setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String n=etNo.getText().toString();
                String t=etType.getText().toString();
                String k=etKat.getText().toString();
                String d=etDurum.getText().toString();

                DatabaseReference defRoom=FirebaseDatabase.getInstance().getReference("Rooms");
                String id=defRoom.push().getKey();
                Rooms rooms=new Rooms(id,n,k,t,d);
                defRoom.child(id).setValue(rooms);
                Toast.makeText(getContext(),"Yeni Oda eklendi.",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog a=b.create();
        a.show();

    }

    private void listemele() {

        defRoom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listRooms.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Rooms rooms=ds.getValue(Rooms.class);
                    listRooms.add(rooms);
                }

                Context contextStr=getContext();
                recyclerView.setHasFixedSize(true);
                if(getActivity()!=null)
                {
                    roomAdapter=new RoomAdapter(getContext(),listRooms);
                    recyclerView.setAdapter(roomAdapter);
                }
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
