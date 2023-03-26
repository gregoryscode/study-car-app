package com.example.carapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * RegisterActivity.java - Classe responsável pelo cadastro de usuários
 * @author  Gregory Perozzo
 * @version 1.0
 */
public class RegisterActivity extends AppCompatActivity {

    private static final String DATABASE_TABLE_USER = "users";
    private EditText _txtName;
    private TextInputLayout _txtNameLayout;
    private EditText _txtEmail;
    private TextInputLayout _txtEmailLayout;
    private EditText _txtPassword;
    private TextInputLayout _txtPasswordLayout;
    private EditText _txtConfirmPassword;
    private TextInputLayout _txtConfirmPasswordLayout;
    private DatabaseReference _firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbarRegister);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _txtName = findViewById(R.id.txtRegisterName);
        _txtNameLayout = findViewById(R.id.txtRegisterNameLayout);
        _txtEmail = findViewById(R.id.txtRegisterEmail);
        _txtEmailLayout = findViewById(R.id.txtRegisterEmailLayout);
        _txtPassword = findViewById(R.id.txtRegisterPassword);
        _txtPasswordLayout = findViewById(R.id.txtRegisterPasswordLayout);
        _txtConfirmPassword = findViewById(R.id.txtRegisterConfirmPassword);
        _txtConfirmPasswordLayout = findViewById(R.id.txtRegisterConfirmPasswordLayout);
        FloatingActionButton fabSave = findViewById(R.id.fabSave);
        FloatingActionButton fabClear = findViewById(R.id.fabClear);

        _txtName.addTextChangedListener(new TextWatcher() {

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
                    _txtNameLayout.setError(null);
                }
            }
        });

        _txtEmail.addTextChangedListener(new TextWatcher() {

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
                    _txtEmailLayout.setError(null);
                }
            }
        });

        _txtPassword.addTextChangedListener(new TextWatcher() {

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
                    _txtPasswordLayout.setError(null);
                }
            }
        });

        _txtConfirmPassword.addTextChangedListener(new TextWatcher() {

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
                    _txtConfirmPasswordLayout.setError(null);
                }
            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if(_txtName.getText().toString().trim().length() == 0) {
                        _txtNameLayout.setError(getString(R.string.register_no_name));
                    }

                    if(_txtEmail.getText().toString().trim().length() == 0) {
                        _txtEmailLayout.setError(getString(R.string.register_no_email));
                    }

                    if(_txtPassword.getText().toString().trim().length() == 0) {
                        _txtPasswordLayout.setError(getString(R.string.register_no_password));
                    }

                    if(_txtConfirmPassword.getText().toString().trim().length() == 0) {
                        _txtConfirmPasswordLayout.setError(getString(R.string.register_no_confirm));
                    }

                    if(_txtPassword.getText().toString().trim().length() != 0 && _txtConfirmPassword.getText().toString().trim().length() != 0 &&
                            !_txtPassword.getText().toString().equals(_txtConfirmPassword.getText().toString())) {
                        _txtConfirmPasswordLayout.setError(getString(R.string.register_confirm_wrong));
                    }

                    if(_txtNameLayout.getError() == null && _txtEmailLayout.getError() == null && _txtPasswordLayout.getError() == null && _txtConfirmPasswordLayout.getError() == null) {

                        _firebaseDatabase = FirebaseDatabase.getInstance().getReference();

                        User user = new User(_txtName.getText().toString(), _txtEmail.getText().toString(), _txtPassword.getText().toString());

                        _firebaseDatabase.child(DATABASE_TABLE_USER).child(user.getId()).setValue(user);

                        Clear();

                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        RegisterActivity.this.finish();
                    }
                }
                catch (Exception e) {
                    ShowMessage(view, getString(R.string.register_error));
                }
            }
        });

        fabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clear();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void Clear() {
        _txtName.setText("");
        _txtEmail.setText("");
        _txtPassword.setText("");
        _txtConfirmPassword.setText("");
        _txtNameLayout.setError(null);
        _txtEmailLayout.setError(null);
        _txtPasswordLayout.setError(null);
        _txtConfirmPasswordLayout.setError(null);
        _txtName.requestFocus();
    }

    private void ShowMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.app_ok), null).show();
    }
}
