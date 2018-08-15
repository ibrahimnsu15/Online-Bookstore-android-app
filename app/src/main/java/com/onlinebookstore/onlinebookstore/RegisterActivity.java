package com.onlinebookstore.onlinebookstore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.Attributes;

public class RegisterActivity extends AppCompatActivity {
    private EditText fullName;
    private EditText Username;
    private EditText Email;
    private TextInputEditText Password;
    private Button bRegister;
    private TextView haveId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.d("Test", "onCreate: RegActivity");
        fullName = findViewById(R.id.RegFullName);
        Username = findViewById(R.id.RegUsername);
        Email = findViewById(R.id.RegEmail);
        Password = findViewById(R.id.RegPassword);
        bRegister = findViewById(R.id.RegDoneButton);
        haveId = findViewById(R.id.alreadyHaveId);

        haveId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    intent = new Intent(RegisterActivity.this,MainActivity.class);
                    Toast.makeText(RegisterActivity.this,"login pannel",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }catch (Exception e){

                }
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = fullName.getText().toString();
                final String username = Username.getText().toString();
                final String email = Email.getText().toString();
                final String password = Password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("register", response.toString());
                            Log.d("register", "onResponse: Register success");
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            RegisterActivity.this.startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, username,email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}