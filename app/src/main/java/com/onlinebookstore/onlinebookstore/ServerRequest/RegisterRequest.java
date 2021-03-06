package com.onlinebookstore.onlinebookstore.ServerRequest;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.onlinebookstore.onlinebookstore.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://"+ MainActivity.HostingIP+"/api/register";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, String email, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email",email);
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}