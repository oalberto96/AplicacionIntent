package com.glassy.aplicacionintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Contacto> contactos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactos = new ArrayList<Contacto>();
        NetworkUtils net_utils = new NetworkUtils();
        getContacts();

    }

    private void main(){
        ArrayList<String> nombresContactos = new ArrayList<>();
        for (Contacto contacto: contactos){
            nombresContactos.add(contacto.getNombre());
        }

        ListView lstContactos = (ListView) findViewById(R.id.lstContactos);
        lstContactos.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombresContactos));
        lstContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(getResources().getString(R.string.name), contactos.get(position).getNombre());
                intent.putExtra(getResources().getString(R.string.phone_number), contactos.get(position).getTelefono());
                intent.putExtra(getResources().getString(R.string.email), contactos.get(position).getEmail());
                startActivity(intent);
            }
        });
    }

    private void getContacts(){
        RequestQueue queue = Volley.newRequestQueue(this);
        final NetworkUtils network_utils = new NetworkUtils();
        StringRequest str_request = network_utils.getGetRequest();

        queue.add(str_request);
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                parseJSON(network_utils.getResponse());
                main();
            }
        });
    }

    private void parseJSON(String response) {
        try {

            JSONObject j_obj = new JSONObject(response);
            JSONArray j_array = j_obj.getJSONArray("results");
            for(int i = 0; i < j_array.length(); i++){
                contactos.add(new Contacto(
                        j_array.getJSONObject(i).getString("nombre"),
                        j_array.getJSONObject(i).getString("telefono"),
                        j_array.getJSONObject(i).getString("email"))
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
