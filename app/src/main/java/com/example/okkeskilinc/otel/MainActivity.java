package com.example.okkeskilinc.otel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth auth;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        auth=FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainMenuRelative()).commit();
    }

    @Override
    //Navigation drawer menu nun aktifleştirme butonu.
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    //Navigation menude butonların seçildiğinde çağrılacak functions choose işlemi.
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
    switch (item.getItemId()){
        case R.id.nav_bedfull:
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RoomFullActivity()).commit();
            break;
        case R.id.nav_bednull:
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RoomNullActivity()).commit();
            break;
        case R.id.nav_costumer:
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CostumerInfoActivity()).commit();
            break;
        case R.id.nav_login:
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            break;
        case R.id.nav_menu:
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainMenuRelative()).commit();
            break;
        case R.id.nav_logout:
            auth.signOut();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            break;
    }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
