package com.onlinebookstore.onlinebookstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import java.io.InputStreamReader;

public class HOMEActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView NameBox;
    private TextView EmailBox;

    ///
    private ActionBarDrawerToggle toggle;

    // user prfile
    String name;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        NameBox=headerView.findViewById(R.id.nevNameID);;
        EmailBox=headerView.findViewById(R.id.nevEmailID);

        //GET bearer token
        final String token  = getToken();
        try {
            getOwnProfile(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }







        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);




    }

    // get token
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

    // get own profile
    public void getOwnProfile(String token) throws JSONException {
        Log.d("token", "getOwnProfile: "+token);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d("Response", "onResponse: "+response.toString());

                    JSONObject userObj= jsonResponse.getJSONObject("user");
                    name = userObj.getString("name").toString();
                    email = userObj.getString("email").toString();

                    Log.d("Name",userObj.getString("name").toString());
                    Toast.makeText(HOMEActivity.this,"Name: "+userObj.getString("name").toString(),Toast.LENGTH_SHORT).show();
                    setUserProfile();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        /// Get request /api/users/me
        GetProfileRequest getProfileRequest = new GetProfileRequest(token,responseListener);
        RequestQueue queue = Volley.newRequestQueue(HOMEActivity.this);
        queue.add(getProfileRequest);



    }

    public void setUserProfile(){
        NameBox.setText(name);
        EmailBox.setText(email);
    }

    // logout
    public void deleteToken(){
        String fileName = "internalStorage";
        Log.d("delete", "deleteToken: ");
        try {
            String message="";
            FileOutputStream fileOutputStream = openFileOutput(fileName,MODE_PRIVATE);
            fileOutputStream.write(message.getBytes());
            fileOutputStream.close();

        }catch (Exception e){
            Toast.makeText(HOMEActivity.this,"unable to delete token",Toast.LENGTH_SHORT).show();
        }
    }


    // back press button
    @Override
    public void onBackPressed()
    {
        // No back option
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nevItemDashboard) {
            //
        } else if (id == R.id.nevItemAllBook) {

        } else if (id == R.id.nevItemCategories) {

        } else if (id == R.id.navItemlogout) {
            deleteToken();
            Intent intent = new Intent(HOMEActivity.this,MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}