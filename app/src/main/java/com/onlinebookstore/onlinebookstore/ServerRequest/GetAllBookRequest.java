package com.onlinebookstore.onlinebookstore.ServerRequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.onlinebookstore.onlinebookstore.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class GetAllBookRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "http://"+ MainActivity.HostingIP+"/api/books/";
    private Map<String, String> header;

    public GetAllBookRequest(String token, Response.Listener<String> listener) {
        super(Method.GET, REGISTER_REQUEST_URL, listener, null);
        header = new HashMap<>();
        header.put("Authorization","Bearer "+token);
    }
/*
    @Override
    public Map<String, String> getParams(){
        return params;
    }*/

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        return header;
    }

}
