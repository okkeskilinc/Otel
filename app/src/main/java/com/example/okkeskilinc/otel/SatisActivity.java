package com.example.okkeskilinc.otel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.okkeskilinc.otel.Classes.Customer;
import com.example.okkeskilinc.otel.Classes.Rooms;
import com.example.okkeskilinc.otel.Classes.Tarihler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class SatisActivity extends Fragment {
    private DatePickerDialog.OnDateSetListener dateSetListenerBirthday,dateSetListenerEntry,dateSetListenerOut;
    private EditText name,TC,phone,address;
    private TextView dogum,entryDate,outDate;
    private Button satis,rezevasyon;
    private Spinner spnroom;

    String [] arrayIdData;
    String [] arrayNameData;
    String [] arrayDurumData;
    String [] arrayKatNoData;
    String [] arrayTypeData;
    int idindis;

    private DatabaseReference defTarih,defCostumer,defRoom;

    @Override
    public void onStart() {
        super.onStart();
        RoomSpnDatas();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_satis_flexible,container,false);
        name=(EditText)v.findViewById(R.id.et_name);
        phone=(EditText)v.findViewById(R.id.et_phone);
        TC=(EditText)v.findViewById(R.id.et_tc);
        address=(EditText)v.findViewById(R.id.et_adres);
        dogum=(TextView)v.findViewById(R.id.tvDogum);
        entryDate=(TextView)v.findViewById(R.id.tvEntry);
        outDate=(TextView)v.findViewById(R.id.tvOut);
        spnroom=(Spinner)v.findViewById(R.id.spn_rooms);
        satis=(Button)v.findViewById(R.id.btnsatis);
        rezevasyon=(Button)v.findViewById(R.id.btnrezerve);

        /*Giris cikis tarihleri ve dogun tarih islemleri baslangic*/
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        month +=1;
        year -=30;
        String date= day+"/"+month+"/"+year;
        dogum.setText(date);
        year +=30;
        date= day+"/"+month+"/"+year;
        entryDate.setText(date);
        day+=3;
        date= day+"/"+month+"/"+year;
        outDate.setText(date);

        entryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCalendar(dateSetListenerEntry);
            }
        });
        dateSetListenerEntry=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DateSetTextView(entryDate,year,month,dayOfMonth);
            }
        };
        outDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCalendar(dateSetListenerOut);
            }
        });
        dateSetListenerOut= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DateSetTextView(outDate,year,month,dayOfMonth);
            }
        };
        dogum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCalendar(dateSetListenerBirthday);
            }
        });
        dateSetListenerBirthday=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DateSetTextView(dogum,year,month,dayOfMonth);
            }
        };
        /*TARIH ISLEMLERI BITIS*/

        satis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextController()==false)
                {
                    Toast.makeText(getContext(),"Fill all places!",Toast.LENGTH_LONG).show();
                }else{CustomerDatabase();}
            }
        });

        rezevasyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextController()==false)
                {
                    Toast.makeText(getContext(),"Fill all places!",Toast.LENGTH_LONG).show();
                }else{CustomerDatabase();}
            }
        });

        return v;
    }

    private void CustomerDatabase() {
        String n=name.getText().toString();
        String tc=TC.getText().toString();
        String tel=phone.getText().toString();
        String adres=address.getText().toString();
        String d=dogum.getText().toString();
        String dEntry=entryDate.getText().toString();
        String dOut=outDate.getText().toString();
        String roomId=spnroom.getSelectedItem().toString();
        String durum="";
        int i=0;
        if (satis.isPressed())
            durum="satis";
        else if (rezevasyon.isPressed())
            durum="rezevasyon";
        while(true)
        {
            if (roomId.equals(arrayNameData[i])){
                idindis=i;break;}
            else{i++;}
        }
        defRoom=FirebaseDatabase.getInstance().getReference("Rooms").child(arrayIdData[idindis]);
        defCostumer=FirebaseDatabase.getInstance().getReference("Customer");
        String Cid=defCostumer.push().getKey();
        //defTarih=FirebaseDatabase.getInstance().getReference("Tarihler").child(id);
        defTarih=FirebaseDatabase.getInstance().getReference("Tarihler");
        Rooms rooms=new Rooms(arrayIdData[idindis],arrayNameData[idindis],arrayKatNoData[idindis],arrayTypeData[idindis],"DOLU");
        defRoom.setValue(rooms);

        String tid=defTarih.push().getKey();
        Tarihler tarihler=new Tarihler(tid,tc,dEntry,dOut,roomId);
        //defTarih.child(tid).setValue(tarihler); //tarih tablosunda eklenen costumerin id si altinda tarih icin tekrar bir id olusur.
        defTarih.child(Cid).setValue(tarihler);

        Customer customer =new Customer(Cid,n,tc,tel,adres,d,durum);
        defCostumer.child(Cid).setValue(customer);

        AlertDialog.Builder costumerinfoBuilder=new AlertDialog.Builder(getContext());
        String mesaj = " ";
        if (satis.isPressed())
            mesaj="Satis islemi gerceklestirildi";
        else if (rezevasyon.isPressed())
            mesaj="Rezervasyon islemi gerceklestirildi";
        costumerinfoBuilder.setTitle("Customer Info")
                .setMessage(mesaj)
                .setCancelable(false)
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        name.setText("");
                        TC.setText("");
                        phone.setText("");
                        address.setText("");
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog costumerinfo= costumerinfoBuilder.create();
        costumerinfo.show();




    }

    private boolean editTextController() {
        boolean etboolean=true;
        if (name.getText().toString().isEmpty()||TC.getText().toString().isEmpty()||
                phone.getText().toString().isEmpty()||address.getText().toString().isEmpty())
        {etboolean = false;}
        return etboolean;
    }

    private void RoomSpnDatas() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Rooms");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> roomName=new ArrayList<String >();
                ArrayList<String> roomId=new ArrayList<String >();
                ArrayList<String> roomDurum=new ArrayList<String >();
                ArrayList<String> roomKatNo=new ArrayList<String >();
                ArrayList<String> roomType=new ArrayList<String >();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if (ds.child("durum").getValue(String.class).equals("bos"))
                    {
                        String roomIdData=ds.child("id").getValue(String.class);
                        String roomNameData=ds.child("no").getValue(String.class);
                        String roomKatNoData=ds.child("katNo").getValue(String.class);
                        String roomTypeData=ds.child("type").getValue(String.class);
                        String roomDurumData=ds.child("durum").getValue(String.class);
                        roomId.add(roomIdData);
                        roomName.add(roomNameData);
                        roomKatNo.add(roomKatNoData);
                        roomType.add(roomTypeData);
                        roomDurum.add(roomDurumData);
                    }
                    arrayIdData= roomId.toArray(new String[0]);
                    arrayNameData=roomName.toArray(new String[0]);
                    arrayKatNoData=roomKatNo.toArray(new String[0]);
                    arrayTypeData=roomType.toArray(new String[0]);
                    arrayDurumData=roomDurum.toArray(new String [0]);
                }
               //ArrayAdapter<String> roomNameAdapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_item,roomName);
                ArrayAdapter<String> roomNameAdapter=new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item,arrayNameData);
                roomNameAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                ArrayAdapter<String> roomIdAdapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_item,roomId);
                spnroom.setAdapter(roomNameAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void DateSetTextView(TextView textView,int year,int month,int day) {
        month +=1;
        String date = day+"/"+month+"/"+year;
        textView.setText(date);
    }

    private void CreateCalendar(DatePickerDialog.OnDateSetListener onDateSetListener) {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog=new DatePickerDialog(getContext(),android.R.style.Theme_DeviceDefault_Light_Panel, onDateSetListener,year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#48a999")));
        dialog.show();

    }
}
