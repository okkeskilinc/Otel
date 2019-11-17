package com.example.okkeskilinc.otel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.okkeskilinc.otel.Adapter.CustomerAdapter;
import com.example.okkeskilinc.otel.Adapter.ItemClickListener;
import com.example.okkeskilinc.otel.Classes.Customer;
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

public class CostumerInfoActivity extends Fragment{
    public static final String CUSTOMER_NAME = "C_NAME";
    public static final String CUSTOMER_ID = "C_ID";
    private EditText etTC;
    private DatabaseReference defCustomers,defTarih;
    private List<Customer> customersList;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CustomerAdapter customerAdapter;
    Button btnSorgu;

    @Override
    public void onStart() {
        super.onStart();
        defCustomers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customersList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    Customer customer=ds.getValue(Customer.class);
                    customersList.add(customer);
                }
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager=new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                customerAdapter=new CustomerAdapter(getContext(),customersList);
                mRecyclerView.setAdapter(customerAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_costumer,container,false);

        defCustomers=FirebaseDatabase.getInstance().getReference("Customer");
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        etTC=(EditText)view.findViewById(R.id.c_sorgu_TC);
        btnSorgu=(Button)view.findViewById(R.id.btn_getir);
        customersList=new ArrayList<>();

        btnSorgu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        TarihDetailActivity tarihDetailActivity=new TarihDetailActivity();
                        Bundle arg=new Bundle();
                        arg.putString("customerTc",etTC.getText().toString());
                        tarihDetailActivity.setArguments(arg);
                        FragmentManager manager=getFragmentManager();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.fragment_container,tarihDetailActivity,"TarihDetailActivity").commit();

            }
        });

        return view;
    }
}
