package com.example.carapp;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * CarActivity.java - Classe responsável por mostrar os veículos cadastrados
 * @author  Gregory Perozzo
 * @version 1.0
 */
public class CarActivity extends AppCompatActivity {

    public static final String VEHICLE_ID = "VEHICLE_ID";
    private static final String DATABASE_TABLE_VEHICLE = "vehicles";
    private LinearLayout _pbLoading;
    private TextView _txtNoCars;
    private ListView _listCars;
    private List<Vehicle> _vehicles;
    private DatabaseReference _firebaseDatabase;
    private CoordinatorLayout _view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_car);
        Toolbar toolbar = findViewById(R.id.toolbarCar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _view = findViewById(R.id.layoutCar);
        _pbLoading = findViewById(R.id.pbLoading);
        _txtNoCars = findViewById(R.id.txtNoCars);
        _listCars = findViewById(R.id.listCars);
        FloatingActionButton fabAddCar = findViewById(R.id.fabAddCar);

        fabAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CarActivity.this, AddCarActivity.class);
                CarActivity.this.startActivity(intent);
            }
        });

        _listCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    String vehicleId = _vehicles.get(position).getId();

                    Intent intent = new Intent(getApplicationContext(), CarDetailActivity.class);
                    intent.putExtra(VEHICLE_ID, vehicleId);
                    startActivity(intent);
                }
                catch (Exception e) {
                    ShowMessage(_view, getString(R.string.car_not_selected));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        LoadCars();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void LoadCars() {

        try {
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

                    if(_vehicles.size() == 0) {
                        _pbLoading.setVisibility(View.GONE);
                        _listCars.setVisibility(View.GONE);
                        _txtNoCars.setVisibility(View.VISIBLE);
                    }
                    else {
                        _pbLoading.setVisibility(View.GONE);
                        _txtNoCars.setVisibility(View.GONE);
                        _listCars.setVisibility(View.VISIBLE);

                        ListAdapter adapter = new ListAdapter(CarActivity.this, R.layout.list_view_item, _vehicles);
                        _listCars.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e) {
            _pbLoading.setVisibility(View.GONE);
            _listCars.setVisibility(View.GONE);
            _txtNoCars.setVisibility(View.VISIBLE);
        }
    }

    private void ShowMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.app_ok), null).show();
    }
}
