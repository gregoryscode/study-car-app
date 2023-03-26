package com.example.carapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * LoginActivity.java - Classe responsável pelo login
 * @author  Gregory Perozzo
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {

    private static final String DATABASE_TABLE_USER = "users";
    private LinearLayout _view;
    private EditText _txtUsername;
    private TextInputLayout _txtUsernameLayout;
    private EditText _txtPassword;
    private TextInputLayout _txtPasswordLayout;
    private DatabaseReference _firebaseDatabase;
    private List<User> _users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        ActivityCompat.requestPermissions(LoginActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1 );

        _view = findViewById(R.id.layoutLogin);
        _txtUsername = findViewById(R.id.txtUsername);
        _txtUsernameLayout = findViewById(R.id.txtUsernameLayout);
        _txtPassword = findViewById(R.id.txtPassword);
        _txtPasswordLayout = findViewById(R.id.txtPasswordLayout);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView txtRegister = findViewById(R.id.txtRegister);

        _txtUsername.addTextChangedListener(new TextWatcher() {

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
                    _txtUsernameLayout.setError(null);
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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    String username = _txtUsername.getText().toString().trim();
                    String password = _txtPassword.getText().toString().trim();

                    if(username.length() == 0) {
                        _txtUsernameLayout.setError(getString(R.string.login_no_username));
                    }

                    if(password.length() == 0) {
                        _txtPasswordLayout.setError(getString(R.string.login_no_password));
                    }

                    if(_txtUsernameLayout.getError() == null && _txtPasswordLayout.getError() == null) {

                        User authenticated = Authenticate(username, password);

                        if(authenticated != null) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("user", authenticated);
                            LoginActivity.this.startActivity(intent);
                            LoginActivity.this.finish();
                        }
                        else {
                            ShowMessage(view, getString(R.string.login_no_auth));
                        }
                    }
                }
                catch (Exception e) {
                    ShowMessage(view, getString(R.string.login_error));
                }
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivityForResult(intent, 69);
            }
        });

        LoadUsers();
    }

    @Override
    protected void onResume() {
        super.onResume();

        _txtUsername.clearFocus();
        _txtPassword.clearFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 69) {
            if(resultCode == Activity.RESULT_OK){
                LoadUsers();
                ShowMessage(_view, getString(R.string.login_register_success));
            }
        }
    }

    private void LoadUsers() {
        _firebaseDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_TABLE_USER);
        Query query = _firebaseDatabase.orderByChild("email");
        _users = new ArrayList<User>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    _users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private User Authenticate(String email, String password) {
        try{
            for(User user : _users) {
                if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    return user;
                }
            }

            return null;
        }
        catch (Exception e){
            return null;
        }
    }

    private void ShowMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.app_ok), null).show();
    }
}
