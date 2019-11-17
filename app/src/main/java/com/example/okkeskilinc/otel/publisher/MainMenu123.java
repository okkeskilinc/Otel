package com.example.okkeskilinc.otel.publisher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.okkeskilinc.otel.CostumerInfoActivity;
import com.example.okkeskilinc.otel.FoodListActivity;
import com.example.okkeskilinc.otel.TarihDetailActivity;
import com.example.okkeskilinc.otel.R;
import com.example.okkeskilinc.otel.RoomFullActivity;
import com.example.okkeskilinc.otel.RoomInfoActivity;
import com.example.okkeskilinc.otel.RoomNullActivity;
import com.example.okkeskilinc.otel.SatisActivity;


public class MainMenu123 extends Fragment {
    Button btnsatis,btnreception,btnbedfull,btnbednull,btncostumer,btnbroom,btnpricelist,btnmenu;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_menu,container,false);
/*
        btnsatis=(Button)view.findViewById(R.id.satis);
        btnreception=(Button)view.findViewById(R.id.reception);
        btnbedfull=(Button)view.findViewById(R.id.bedfull);
        btnbednull=(Button)view.findViewById(R.id.bednull);
        btncostumer=(Button)view.findViewById(R.id.costumer);
        btnbroom=(Button)view.findViewById(R.id.broom);
        btnpricelist=(Button)view.findViewById(R.id.pricelist);
        btnmenu=(Button)view.findViewById(R.id.menu);
*/
        final TarihDetailActivity priceListActivity=new TarihDetailActivity();
        final RoomFullActivity roomFullActivity=new RoomFullActivity();
        final RoomInfoActivity roomInfoActivity=new RoomInfoActivity();
        final RoomNullActivity roomNullActivity=new RoomNullActivity();
        final SatisActivity satisActivity=new SatisActivity();
        final CostumerInfoActivity costumerInfoActivity=new CostumerInfoActivity();
        final FoodListActivity foodListActivity=new FoodListActivity();

       final FragmentManager manager=getFragmentManager();
       final FragmentTransaction transaction=manager.beginTransaction();

        btnpricelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container,priceListActivity,"PriceListActivity").commit();
            }
        });


        btnbedfull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container, roomFullActivity, "RoomFullActivity").commit();
            }
        });

        btnbednull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container, roomNullActivity, "RoomNullActivity").commit();
            }
        });

        btnbroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container, roomInfoActivity, "RoomInfoActivity").commit();
            }
        });

        btnsatis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container, satisActivity, "SatisActivity").commit();
            }
        });

        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container, foodListActivity, "FoodListActivity").commit();
            }
        });

        btncostumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container, costumerInfoActivity, "CostumerInfoActivity").commit();
            }
        });

       return view;
    }
}


