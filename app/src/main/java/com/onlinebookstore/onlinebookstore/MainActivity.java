package com.onlinebookstore.onlinebookstore;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.onlinebookstore.onlinebookstore.ServerRequest.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername ;
    private EditText etPassword;
    private TextView tvRegisterLink ;
    private Button bLogin ;
    private TextView result;


    public static String HostingIP="swe-bookstore.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.loginUsername);
        etPassword = findViewById(R.id.loginPass);
        tvRegisterLink =findViewById(R.id.registerButton);
        bLogin = findViewById(R.id.loginButton);
        result = findViewById(R.id.loginStatus);

        checkAlreadyLogin();

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

                            JSONObject jsonResponse = new JSONObject(response);


                            String token = jsonResponse.getString("token");
                            String message = jsonResponse.getString("message");
                            Log.d("message",jsonResponse.getString("message"));
                            Log.d("token",token);


                            //store token
                            savedToken(token);
                            //unvisible login log
                            result.setVisibility(View.GONE);


                            Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,HOMEActivity.class);
                            startActivity(intent);


                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,"Error 401",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
                result.setText("Login fail. try again");
                etPassword.setText("");
            }

        });
    }


    //store token
    public void savedToken(String token){

        String fileName = "internalStorage";

        try {

            FileOutputStream fileOutputStream = openFileOutput(fileName,MODE_PRIVATE);
            fileOutputStream.write(token.getBytes());
            fileOutputStream.close();

        }catch (Exception e){
            Toast.makeText(MainActivity.this,"Fail to store token",Toast.LENGTH_SHORT).show();
        }

    }

    // check already have token
    public void checkAlreadyLogin(){

        String queryToken="";
        try {
            String fileName = "internalStorage";
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            queryToken = bufferedReader.readLine().toString();

        }catch (Exception e){
        }

        if(queryToken.length()>0){
            Toast.makeText(MainActivity.this,"already have token",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,HOMEActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this,"Have no token",Toast.LENGTH_SHORT).show();
        }


    }

}