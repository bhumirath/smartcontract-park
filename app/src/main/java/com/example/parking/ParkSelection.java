package com.example.parking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ParkSelection extends AppCompatActivity {

    A a;
    Button btncardpay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        btncardpay = (Button) findViewById(R.id.btncardpay);
        TextView booking = (TextView) findViewById(R.id.booking);

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
    }

        public void clk(View view){
            EditText promptText = (EditText) findViewById(R.id.editTextTextPersonName);
            String inputText = promptText.getText().toString();
            a.store(new BigInteger(inputText)).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
                @Override
                public void accept(TransactionReceipt transactionReceipt) throws Exception {
                    Log.i("vac","accept: ");
                }
            });
        }
        public void clkShowData(View view){
            TextView booking = (TextView) findViewById(R.id.booking);
            a.booking().flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<BigInteger>() {
                @Override
                public void accept(BigInteger bigInteger) throws Exception {
                    booking.setText(String.valueOf(bigInteger));
                }
            });
        }
}


//        btncardpay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(ParkSelection.this, payment_activity.class);
//                startActivity(i);
//          }
//        });
//
//    }
//    public void cardpay(View view) {
//        Intent intent = new Intent(ParkSelection.this, payment_activity.class);
//        startActivity(intent);
//    }
//}