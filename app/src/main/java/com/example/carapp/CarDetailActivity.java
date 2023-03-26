package com.example.carapp;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

/**
 * CarDetailActivity.java - Classe responsável por mostrar os detalhes de um veículo
 * @author  Gregory Perozzo
 * @version 1.0
 */
public class CarDetailActivity extends AppCompatActivity {

    private static final String DATABASE_TABLE_VEHICLE = "vehicles";
    private DatabaseReference _firebaseDatabase;
    private EditText _txtId;
    private EditText _txtPlate;
    private EditText _txtManufacturer;
    private EditText _txtModel;
    private EditText _txtColor;
    private EditText _txtYear;
    private List<Vehicle> _vehicles;
    private CoordinatorLayout _view;
    private String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_car_detail);
        Toolbar toolbar = findViewById(R.id.toolbarCarDetail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        _id = intent.getStringExtra(CarActivity.VEHICLE_ID);

        _view = findViewById(R.id.layoutCarDetail);
        _txtId = findViewById(R.id.txtCarDetailId);
        _txtPlate = findViewById(R.id.txtCarDetailPlate);
        _txtManufacturer = findViewById(R.id.txtCarDetailManufacturer);
        _txtModel = findViewById(R.id.txtCarDetailModel);
        _txtColor = findViewById(R.id.txtCarDetailColor);
        _txtYear = findViewById(R.id.txtCarDetailYear);

        LoadVehicle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void LoadVehicle() {
        try {
            _firebaseDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_TABLE_VEHICLE);
            Query query = _firebaseDatabase.orderByChild("plate");
            _vehicles = new ArrayList<Vehicle>();

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Vehicle vehicle = postSnapshot.getValue(Vehicle.class);

                        if(vehicle.getId().equals(_id)) {
                            _txtId.setText(vehicle.getId());
                            _txtPlate.setText(vehicle.getPlate());
                            _txtManufacturer.setText(vehicle.getManufacturer());
                            _txtModel.setText(vehicle.getModel());
                            _txtColor.setText(vehicle.getColor());
                            _txtYear.setText(vehicle.getYear());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e) {
            ShowMessage(_view, getString(R.string.car_detail_not_loaded));
        }
    }

    private void ShowMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.app_ok), null).show();
    }
}
