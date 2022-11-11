package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addPark extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpark);

        EditText busid = findViewById(R.id.id);
        EditText seats = findViewById(R.id.seats);
        Button addbus = findViewById(R.id.addbus);
        Button back1 =findViewById(R.id.button2);
        DBHelper DB = new DBHelper(this);


        addbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = busid.getText().toString();
                String total_seats = seats.getText().toString();

                if (id.equals("") || total_seats.equals(""))
                {
                    Toast.makeText(addPark.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkid= DB.checkid(id);
                    if (checkid==false){
                        Boolean insert = DB.insertBus(id,total_seats);
                        if (insert==true){
                            Toast.makeText(addPark.this, "Bus has been added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), adminpanel.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(addPark.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(addPark.this, "ID already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        /*back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminpanel.class);
                startActivity(intent);
            }
        });*/

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminpanel.class);
                startActivity(intent);
            }
        });

    }
}