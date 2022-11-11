package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class deletePark extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletebus);

        EditText busID = findViewById(R.id.busid);
        Button deletePark = findViewById(R.id.deletebus);
        Button back = findViewById(R.id.button03);
        DBHelper DB = new DBHelper(this);

        deletePark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = busID.getText().toString();
                if (id.equals(""))
                {
                    Toast.makeText(deletePark.this, "Please Enter Bus ID", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkid= DB.checkid(id);
                    if (checkid==true){
                        Boolean delete = DB.deleteBus(id);
                        if (delete==true){
                            Toast.makeText(deletePark.this, "Bus deleted successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), adminpanel.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(deletePark.this, "Entry not deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(deletePark.this, "ID doesnot exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminpanel.class);
                startActivity(intent);
            }
        });

    }
}