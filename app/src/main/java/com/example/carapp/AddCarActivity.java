package com.example.carapp;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * AddCarActivity.java - Classe responsável por adicionar um veículo no banco de dados
 * @author  Gregory Perozzo
 * @version 1.0
 */
public class AddCarActivity extends AppCompatActivity {

    private static final String DATABASE_TABLE_VEHICLE = "vehicles";
    private CoordinatorLayout _view;
    private DatabaseReference _firebaseDatabase;
    private Spinner _sprCarManufacturer;
    private Spinner _sprCarColor;
    private Spinner _sprCarYear;
    private EditText _txtPlate;
    private EditText _txtModel;
    private TextInputLayout _txtPlateLayout;
    private TextInputLayout _txtModelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_add_car);
        Toolbar toolbar = findViewById(R.id.toolbarAddCar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _view = findViewById(R.id.layoutAddCar);
        _sprCarManufacturer = findViewById(R.id.sprCarManufacturer);
        _sprCarColor = findViewById(R.id.sprCarColor);
        _sprCarYear = findViewById(R.id.sprCarYear);
        _txtPlate = findViewById(R.id.txtCarPlate);
        _txtModel = findViewById(R.id.txtCarModel);
        _txtPlateLayout = findViewById(R.id.txtCarPlateLayout);
        _txtModelLayout = findViewById(R.id.txtCarModelLayout);

        FloatingActionButton fabSaveCar = findViewById(R.id.fabSaveCar);
        FloatingActionButton fabClearCar = findViewById(R.id.fabClearCar);

        _txtPlate.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0) {
                    _txtPlateLayout.setError(null);
                }
            }
        });

        _txtModel.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0) {
                    _txtModelLayout.setError(null);
                }
            }
        });

        fabSaveCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    String plate = _txtPlate.getText().toString().trim();
                    String model = _txtModel.getText().toString().trim();

                    if(plate.length() == 0) {
                        _txtPlateLayout.setError(getString(R.string.car_add_no_plate));
                    }

                    if(model.length() == 0) {
                        _txtModelLayout.setError(getString(R.string.car_add_no_model));
                    }

                    if(_txtPlateLayout.getError() == null && _txtModelLayout.getError() == null) {

                        _firebaseDatabase = FirebaseDatabase.getInstance().getReference();

                        Vehicle vehicle = new Vehicle(plate.toUpperCase(), _sprCarManufacturer.getSelectedItem().toString(), model.toUpperCase(), _sprCarColor.getSelectedItem().toString(), _sprCarYear.getSelectedItem().toString());

                        _firebaseDatabase.child(DATABASE_TABLE_VEHICLE).child(vehicle.getId()).setValue(vehicle);

                        ShowMessage(view, getString(R.string.car_add_success));
                        Clear();
                    }
                }
                catch (Exception e) {
                    ShowMessage(view, getString(R.string.car_add_error));
                }
            }
        });

        fabClearCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clear();
            }
        });

        SetupSpinners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void SetupSpinners() {

        try{
            String manufacturers[] = { "Acura", "Alfa Romeo", "Aston Martin", "Audi", "Bentley", "BMW", "Bugatti", "Buick", "Cadillac", "Chevrolet", "Chrysler", "Citroën", "Dodge", "Ferrari", "Fiat", "Ford", "Honda", "Hyundai", "Infiniti", "Jaguar", "Jeep", "Kia", "Koenigsegg", "Lamborghini", "Land Rover", "Lexus", "Maserati", "Mazda", "McLaren", "Mercedes-Benz", "Mini", "Mitsubishi", "Nissan", "Pagani", "Peugeot", "Porsche", "Ram", "Renault", "Rolls-Royce", "Subaru", "Suzuki", "Tesla", "Toyota", "Volkswagen", "Volvo"};
            String colors[] = { "Amarelo", "Azul", "Bordô", "Branco", "Cinza", "Creme", "Gelo", "Laranja", "Marrom", "Prata", "Preto", "Rosa", "Roxo", "Salmão", "Verde", "Vermelho", "Vinho"};
            String years[] = { "2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006","2005","2004","2003","2002","2001","2000","1999","1998","1997","1996","1995","1994","1993","1992","1991","1990","1989","1988","1987","1986","1985","1984","1983","1982","1981","1980","1979","1978","1977","1976","1975","1974","1973","1972","1971","1970","1969","1968","1967","1966","1965","1964","1963","1962","1961","1960","1959","1958","1957","1956","1955","1954","1953","1952","1951","1950","1949","1948","1947","1946","1945","1944","1943","1942","1941","1940","1939","1938","1937","1936","1935","1934","1933","1932","1931","1930","1929","1928","1927","1926","1925","1924","1923","1922","1921","1920","1919","1918","1917","1916","1915","1914","1913","1912","1911","1910","1909","1908","1907","1906","1905","1904","1903","1902","1901","1900","1899","1898","1897","1896","1895","1894","1893","1892","1891","1890","1889","1888","1887","1886","1885","1884","1883","1882","1881","1880","1879","1878","1877","1876","1875","1874","1873","1872","1871","1870","1869","1868","1867","1866","1865","1864","1863","1862","1861","1860","1859","1858","1857","1856","1855","1854","1853","1852","1851","1850"};

            ArrayAdapter<String> manufacturerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, manufacturers);
            manufacturerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            _sprCarManufacturer.setAdapter(manufacturerAdapter);

            ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
            colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            _sprCarColor.setAdapter(colorAdapter);

            ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
            yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            _sprCarYear.setAdapter(yearAdapter);
        }
        catch (Exception e) {
            ShowMessage(_view, getString(R.string.car_add_load_data_error));
        }
    }

    private void Clear() {
        _txtPlate.setText("");
        _txtModel.setText("");
        _sprCarManufacturer.setSelection(0);
        _sprCarColor.setSelection(0);
        _sprCarYear.setSelection(0);
        _txtPlateLayout.setError(null);
        _txtModelLayout.setError(null);
        _txtPlate.requestFocus();
    }

    private void ShowMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.app_ok), null).show();
    }
}
