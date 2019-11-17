package com.example.okkeskilinc.otel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class MainMenuRelative extends Fragment {
    //RelativeLayout rellay_costumer,rellay_satis,rellay_bedfull,rellay_bednull,rellay_broom, relative_room_istatistik,rellay_reception,rellay_food_menu;
    ImageButton rellay_costumer,rellay_satis,rellay_bedfull,rellay_bednull,rellay_broom, relative_room_istatistik,rellay_reception,rellay_food_menu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View view=inflater.inflate(R.layout.content_menu,container,false);
        View view=inflater.inflate(R.layout.activity_main_menu,container,false);

        rellay_reception=view.findViewById(R.id.relative_reception);
        rellay_satis=view.findViewById(R.id.relative_tlmoney);
        rellay_bedfull=view.findViewById(R.id.relative_bedfull);
        rellay_bednull=view.findViewById(R.id.relative_bednull);
        rellay_broom=view.findViewById(R.id.relative_broom);
        rellay_costumer=view.findViewById(R.id.relative_costumer);
        rellay_food_menu=view.findViewById(R.id.relative_forkspoon);
        relative_room_istatistik=view.findViewById(R.id.relative_room_istatistik);

        final TarihDetailActivity detailActivity=new TarihDetailActivity();
        final RoomFullActivity roomFullActivity=new RoomFullActivity();
        final RoomInfoActivity roomInfoActivity=new RoomInfoActivity();
        final RoomNullActivity roomNullActivity=new RoomNullActivity();
        final SatisActivity satisActivity=new SatisActivity();
        final CostumerInfoActivity costumerInfoActivity=new CostumerInfoActivity();
        final FoodListActivity foodListActivity=new FoodListActivity();
        final RoomIstatistikActivity roomIstatistikActivity=new RoomIstatistikActivity();

        final FragmentManager manager=getFragmentManager();
        final FragmentTransaction transaction=manager.beginTransaction();

        rellay_satis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container,satisActivity,"SatisActivity").commit();
            }
        });

        rellay_reception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container,satisActivity,"Reception").commit();
            }
        });

        rellay_bedfull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container,roomFullActivity,"RoomFullActivity").commit();
            }
        });

        rellay_bednull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container,roomNullActivity,"RoomNullActivity").commit();
            }
        });

        rellay_broom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container,roomInfoActivity,"RoomInfoActivity").commit();
            }
        });

        rellay_costumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container,costumerInfoActivity,"CostumerInfoActivity").commit();
            }
        });

        rellay_food_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container,foodListActivity,"FoodListActivity").commit();
            }
        });

        relative_room_istatistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction.replace(R.id.fragment_container,roomIstatistikActivity,"RoomIstatistikActivity").commit();
            }
        });

        return view;
    }
}
