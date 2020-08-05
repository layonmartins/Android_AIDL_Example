package com.layon.android.aidlclientcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.layon.android.aidlservercalculator.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    IMyAidlInterface aidlInterface;
    private ServiceConnection serviceConnection;
    private String TAG = "layonf - aidlclientcalculator";
    private TextView tvResult;
    private EditText et1;
    private EditText et2;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get references
        tvResult = (TextView) findViewById(R.id.textViewResult);
        et1 = (EditText) findViewById(R.id.editTextNumber1);
        et2 = (EditText) findViewById(R.id.editTextNumber2);
        btn = (Button) findViewById(R.id.buttonSum);

        //set button clickListener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double n1 = Double.parseDouble(et1.getText().toString());
                    double n2 = Double.parseDouble(et2.getText().toString());
                    double result = aidlInterface.sum(n1, n2);
                    tvResult.setText("" + result);
                } catch (Exception e) {
                    Log.e(TAG, "error: " + e.getMessage());
                }
            }
        });

        //Service Connection for comunication App Server
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "onServiceConnected");
                aidlInterface = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "onServiceDisconnected");
                aidlInterface = null;
            }
        };

        //Intent initialize service
        Intent intent = new Intent("com.layon.android.aidlservercalculator.CALCULATE");
        intent.setPackage("com.layon.android.aidlservercalculator");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }
}