package com.stucom.grupo4.typhone.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.MyVolley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    final static String URL = "http://api.flx.cat/dam2game/register";
    EditText registerEmail, registerUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    protected void onResume(){ super.onResume(); }

    void registration(){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dpc", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = error.toString();
                NetworkResponse response = error.networkResponse;
                if(response != null){
                    message = response.statusCode + " " + message;
                }
            }
        }){
            @Override protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("email", registerEmail.getText().toString());
                return params;
            }
        };
        MyVolley.getInstance(this).add(request);
    }

    /*
    * to do:
    * make another method to search if the provided
    * email already exisits in the database
    * */

}
