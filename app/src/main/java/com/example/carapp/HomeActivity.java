package com.example.carapp;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * HomeActivity.java - Classe responsável por mostrar a tela inicial
 * @author  Gregory Perozzo
 * @version 1.0
 */
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private static final String DATABASE_TABLE_VEHICLE = "vehicles";
    private DrawerLayout _drawerLayout;
    private NavigationView _navigationView;
    private User _user;
    private List<Vehicle> _vehicles;
    private DatabaseReference _firebaseDatabase;
    private TextView _txtVehicleCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        _drawerLayout = findViewById(R.id.drawerLayout);
        _navigationView = findViewById(R.id.navView);
        TextView txtHeader = findViewById(R.id.txtHomeHeader);
        View headerNav = _navigationView.getHeaderView(0);
        TextView headerNavName = headerNav.findViewById(R.id.navHeaderName);
        TextView headerNavEmail = headerNav.findViewById(R.id.navHeaderEmail);
        _txtVehicleCount = findViewById(R.id.txtVehicleCount);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, _drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        _drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        _navigationView.setNavigationItemSelectedListener(this);

        _user = (User)getIntent().getSerializableExtra("user");
        txtHeader.setText("Bem-vindo(a), " + _user.getName());
        headerNavName.setText(_user.getName());
        headerNavEmail.setText(_user.getEmail());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navCars: {
                Intent intent = new Intent(HomeActivity.this, CarActivity.class);
                HomeActivity.this.startActivity(intent);
                break;
            }
            case R.id.navWho: {
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                HomeActivity.this.startActivity(intent);
                break;
            }
            case R.id.navPrivacy: {
                Intent intent = new Intent(HomeActivity.this, PrivacyActivity.class);
                HomeActivity.this.startActivity(intent);
                break;
            }
            case R.id.navExit: {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                HomeActivity.this.startActivity(intent);
                HomeActivity.this.finish();
                break;
            }
        }

        _drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (_drawerLayout.isDrawerOpen(GravityCompat.START)) {
            _drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        LoadCars();
    }

    private void LoadCars() {

        try{
            _firebaseDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_TABLE_VEHICLE);
            Query query = _firebaseDatabase.orderByChild("plate");
            _vehicles = new ArrayList<Vehicle>();

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Vehicle vehicle = postSnapshot.getValue(Vehicle.class);
                        _vehicles.add(vehicle);
                    }

                    _txtVehicleCount.setText(String.valueOf(_vehicles.size()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e) {
            _txtVehicleCount.setText("0");
        }
    }
}
