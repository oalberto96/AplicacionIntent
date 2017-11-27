package com.glassy.aplicacionintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Contacto> contactos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactos = new ArrayList<Contacto>();

        contactos.add(new Contacto("OElr SDa","6461828382","aoa@gmail.com"));
        contactos.add(new Contacto("Sasuke Uchi","6641328398","itachi@gmail.com"));
        contactos.add(new Contacto("Ragnar Lothbrok","6234343223","dddatios@gmail.com"));

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
}
