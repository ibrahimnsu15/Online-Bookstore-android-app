package com.onlinebookstore.onlinebookstore;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername ;
    private EditText etPassword;
    private TextView tvRegisterLink ;
    private Button bLogin ;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.loginUsername);
        etPassword = findViewById(R.id.loginPass);
        tvRegisterLink =findViewById(R.id.registerButton);
        bLogin = findViewById(R.id.loginButton);
        result = findViewById(R.id.loginStatus);

        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                try {
                    intent = new Intent(MainActivity.this,RegisterActivity.class);
                    Toast.makeText(MainActivity.this,"Clicked for register",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }catch (Exception e){

                }

            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                Log.d("loginButton", "onClick: click login");
                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("Response", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.d("JSonObj", jsonResponse.toString());
                            Log.d("message",jsonResponse.getString("message"));

                            String token = jsonResponse.getString("token");

                            Log.d("token",token);
//                            Toast.makeText(MainActivity.this,token,Toast.LENGTH_SHORT).show();
//                            result.setText(result.getText().toString()+token);
////
//                            if (success) {
//                                Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                Toast.makeText(MainActivity.this,"Login Fail",Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                builder.setMessage("Login Failed")
//                                        .setNegativeButton("Retry", null)
//                                        .create()
//                                        .show();
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}