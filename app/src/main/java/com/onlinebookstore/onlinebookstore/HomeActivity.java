package com.onlinebookstore.onlinebookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HomeActivity extends AppCompatActivity {

    private  TextView TV;
    private Button logout;
    private Button sentGetRequest;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = findViewById(R.id.logoutButton);
        TV = findViewById(R.id.homeTV);
        sentGetRequest = findViewById(R.id.getRrequest);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteToken();
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        final String token  = getToken();
        getOwnProfile(token);


        sentGetRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("token", "sentGetRequest: "+token);
                getOwnProfile(token);
            }
        });


    }

    public String getToken(){
        String queryToken="";
        try {
            String fileName = "internalStorage";
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            queryToken = bufferedReader.readLine().toString();

        }catch (Exception e){

        }

        return queryToken;
    }

    public void deleteToken(){
        String fileName = "internalStorage";

        try {
            String message="";
            FileOutputStream fileOutputStream = openFileOutput(fileName,MODE_PRIVATE);
            fileOutputStream.write(message.getBytes());
            fileOutputStream.close();

        }catch (Exception e){
            Toast.makeText(HomeActivity.this,"unable to delete token",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed()
    {
        // No back option
    }

    public void getOwnProfile(String token){
        Log.d("token", "getOwnProfile: "+token);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d("Response", "onResponse: "+response.toString());

                    JSONObject userObj= jsonResponse.getJSONObject("user");
                    name = userObj.getString("name");
                    Log.d("Name",name);
                    Toast.makeText(HomeActivity.this,"Name: "+name,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        /// Get request /api/users/me
        GetProfileRequest getProfileRequest = new GetProfileRequest(token,responseListener);
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        queue.add(getProfileRequest);



        TV.setText("Name : "+name);
    }


}
