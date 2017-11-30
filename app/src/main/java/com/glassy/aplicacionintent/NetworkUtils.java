package com.glassy.aplicacionintent;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by glassy on 11/29/17.
 */

public class NetworkUtils {
    public static final String HOST = "http://10.0.0.2/api";
    private String m_response;

    public String getResponse(){
        return m_response;
    }

    public StringRequest getGetRequest(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, HOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        m_response = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("NetworkUtils: ",error.toString());
            }

        });
        return stringRequest;
    }

}
