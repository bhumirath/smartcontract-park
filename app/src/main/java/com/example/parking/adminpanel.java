package com.example.parking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class adminpanel extends AppCompatActivity {
    A a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        Button add = findViewById(R.id.add);
        Button update=findViewById(R.id.update);
        Button delete=findViewById(R.id.delete);
        Button view = findViewById(R.id.view);
        Button end = findViewById(R.id.logout);
        DBHelper DB = new DBHelper(this);
        TextView booking = (TextView) findViewById(R.id.data);

        Web3j web3 = Web3j.build(new HttpService("https://goerli.infura.io/v3/3c59a71b0d314179abfc444cdf1633b4"));
        Credentials credentials = Credentials.create("bbd7190000e6ad6bb82ca7bcd816ca21bf8a8c8288427a0c57dec2b485acb67e");
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        a = A.load("0x07Ec9d9bd96E58E110995064F9C8F9aCc6C74FD8", web3, credentials, contractGasProvider);

        a.booking().flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<BigInteger>() {
            @Override
            public void accept(BigInteger bigInteger) throws Exception {
                Log.i("vac","accept: " + bigInteger);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        booking.setText(String.valueOf(bigInteger));
                    }
                });
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addPark.class);
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), updatePark.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), deletePark.class);
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = DB.viewbuses();
                if (result.getCount()==0){
                    Toast.makeText(adminpanel.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()){
                    buffer.append("ID :"+result.getString(0)+"\n");
                    buffer.append("Total Park :"+result.getString(1)+"\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(adminpanel.this);
                builder.setCancelable(true);
                builder.setTitle("ALL BUSES DETAILS");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

            }
        }

