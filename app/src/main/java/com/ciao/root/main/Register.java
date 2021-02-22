package com.ciao.root.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    EditText fullName, email, password, confirmPassword, contact, alternateContact;
    Button register;
    TextView login;
    FirebaseAuth mAuth;
    DatabaseReference dbReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();


        fullName = findViewById(R.id.userFullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        contact = findViewById(R.id.contact);
        alternateContact = findViewById(R.id.alternateContact);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

//        String pass = password.getText().toString();
//        String cnfPass = confirmPassword.getText().toString();
//        String mail = email.getText().toString();

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Register.this, email.getText().toString() + " ," + password.getText().toString() + " ," + confirmPassword.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        register.setOnClickListener(v -> {

            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                if (TextUtils.isEmpty(fullName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(contact.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Enter your contact", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                        Map<String, Object> map = new HashMap<>();
                        map.put("Name", fullName.getText().toString());
                        map.put("Contact", contact.getText().toString());
                        map.put("Email", email.getText().toString());
                        if(!TextUtils.isEmpty(alternateContact.getText()))
                            map.put("Alternate Contact",alternateContact.getText().toString());
                        dbReference.child("Users").child(mAuth.getCurrentUser().getUid()).updateChildren(map);

                        startActivity(new Intent(getApplicationContext(), Home.class));

                    } else
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                });
            } else
                Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();


        });
    }
}