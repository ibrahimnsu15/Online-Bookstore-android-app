package com.onlinebookstore.onlinebookstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.onlinebookstore.onlinebookstore.ServerRequest.GetAllBookRequest;
import com.onlinebookstore.onlinebookstore.ServerRequest.GetProfileRequest;
import com.onlinebookstore.onlinebookstore.ServerRequest.GetRecommendBookRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HOMEActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView NameBox;
    private TextView EmailBox;
    private Spinner CategoriesSpinner;
    private TextView spinnerText;

    String[] spinnerItem={"All Catagories","Classics","Fantasy","Fiction","Thriller"};

    ///
    private ActionBarDrawerToggle toggle;

    // user prfile
    String name;
    String email;

    ///
    public String token;

    List<Book> listBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        //
        NameBox=headerView.findViewById(R.id.nevNameID);;
        EmailBox=headerView.findViewById(R.id.nevEmailID);
        spinnerText= findViewById(R.id.catagoriesTextID);
        CategoriesSpinner = findViewById(R.id.CategoriesSpinnerViewID);

        //
        ///

        /// Categories Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this,R.layout.spinner_item_view,R.id.CategoriesItemViewID,spinnerItem);
        CategoriesSpinner.setAdapter(adapter);

        //GET bearer token
        token  = getToken();
        try {
            getOwnProfile(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }






        /// navigation
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        /// book list instant create
        listBook = new ArrayList<>();
        getRecommendBook();
        setBookView();

    }


    private void setBookView() {

        Log.d("book", "setBookView: added book");

        try {
            RecyclerView recyclerView = findViewById(R.id.recylerview_id);
            RecyclerViewAdapter recyclerViewAdapter= new RecyclerViewAdapter(this,listBook);
            recyclerView.setLayoutManager(new GridLayoutManager(this,1));
            recyclerView.setAdapter(recyclerViewAdapter);

        }catch (Exception e){
            Log.d("message", "setBookView: Unable to add book");
        }

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


    public void getAllBook(){
        Log.d("book", "getAllBook: get all book");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d("Response", "onResponse: "+response.toString());

                    JSONArray jsonBookArray = jsonResponse.getJSONArray("books");


                    for (int i = 0; i < jsonBookArray.length(); i++) {
                        JSONObject singleBookObj = jsonBookArray.getJSONObject(i);

                        Log.d("single book", "onResponse: "+singleBookObj.toString());

                        String tmpName = singleBookObj.getString("title").toString();
                        String tmpAuthor = singleBookObj.getString("author").toString();
                        String tmpISBN = singleBookObj.getString("ISBN").toString();
                        String tmpDetails = singleBookObj.getString("details").toString();
                        String tmpImageUrl = singleBookObj.getString("image").toString();
                        double tmpRating=0;
                        int tmpCount=0;
                        if(singleBookObj.getJSONObject("rating")!=null){
                            JSONObject ratingObj = singleBookObj.getJSONObject("rating");
                            tmpRating = ratingObj.getDouble("rating");
                            tmpCount=ratingObj.getInt("count");
                        }



                        Log.d("rating", "onSinglebook: "+tmpRating);


                        Log.d("name", "onResponse: "+tmpName);
                        Log.d("author", "onResponse: "+tmpAuthor);
                        Log.d("ISBN", "onResponse: "+tmpISBN);

                        listBook.add(new Book(tmpName,tmpAuthor,tmpISBN,tmpDetails,tmpImageUrl,tmpRating,tmpCount));
                    }

                    Log.d("BookArray", "BookArray: complete");
                    setBookView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        /// Get request /api/users/me
        GetAllBookRequest getAllBookRequest = new GetAllBookRequest(token,responseListener);
        RequestQueue queue = Volley.newRequestQueue(HOMEActivity.this);
        queue.add(getAllBookRequest);

    }

    public void getRecommendBook(){
        Log.d("book", "getRecommendBook: get all book");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d("Response", "onResponse: "+response.toString());

                    JSONArray jsonBookArray = jsonResponse.getJSONArray("books");






                    for (int i = 0; i < jsonBookArray.length(); i++) {
                        JSONObject singleBookObj = jsonBookArray.getJSONObject(i);

                        Log.d("single book", "onResponse: "+singleBookObj.toString());

                        String tmpName = singleBookObj.getString("title").toString();
                        String tmpAuthor = singleBookObj.getString("author").toString();
                        String tmpISBN = singleBookObj.getString("ISBN").toString();
                        String tmpDetails = singleBookObj.getString("details").toString();
                        String tmpImageUrl = singleBookObj.getString("image").toString();
                        double tmpRating=0;
                        int tmpCount=0;
                        if(singleBookObj.getJSONObject("rating")!=null){
                            JSONObject ratingObj = singleBookObj.getJSONObject("rating");
                            tmpRating = ratingObj.getDouble("rating");
                            tmpCount=ratingObj.getInt("count");
                        }

                        Log.d("rating", "onSinglebook: "+tmpRating);


                        Log.d("name", "onResponse: "+tmpName);
                        Log.d("author", "onResponse: "+tmpAuthor);
                        Log.d("ISBN", "onResponse: "+tmpISBN);

                        listBook.add(new Book(tmpName,tmpAuthor,tmpISBN,tmpDetails,tmpImageUrl,tmpRating,tmpCount));
                    }

                    Log.d("BookArray", "BookArray: complete");


                    setBookView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        /// Get request /api/users/me
        GetRecommendBookRequest getRecommendBookRequest = new GetRecommendBookRequest(token,responseListener);
        RequestQueue queue = Volley.newRequestQueue(HOMEActivity.this);
        queue.add(getRecommendBookRequest);

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
            //recommend Book
            listBook.clear();
            getRecommendBook();
            Log.d("recommendbook", "onNavigationItemSelected: complete get recommend book");
        } else if (id == R.id.nevItemAllBook) {
            listBook.clear();
            getAllBook();
            Log.d("allbook", "onNavigationItemSelected: complete get book");

        }else if (id == R.id.navItemlogout) {
            deleteToken();
            Intent intent = new Intent(HOMEActivity.this,MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
