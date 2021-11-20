package com.example.activities;

import static com.example.activities.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import ie.app.models.Donation;

public class Donate extends Base {
    private RadioGroup paymentMethod;
    private ProgressBar progressBar;
    private NumberPicker amountPicker;
    private EditText amountText;
    private TextView amountTotal;
    static int minAmountPicker = 0 ;
    static String totalDonatedStr = "$" + 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);


        Button donateButton = findViewById(id.donateButton);
        if (donateButton != null)
        {
            Log.v("Donate", "Really got the donate button");
        }
        paymentMethod = (RadioGroup) findViewById(id.paymentMethod);
        progressBar = (ProgressBar) findViewById(id.progressBar);
        amountPicker = (NumberPicker) findViewById(id.amountPicker) ;
        amountText = (EditText) findViewById(R.id.paymentAmount);
        amountTotal = (TextView) findViewById(R.id.totalSoFar);
        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);
        amountPicker.setValue(998);
        progressBar.setMax(10000);
        progressBar.setProgress(minAmountPicker);
        amountTotal.setText(totalDonatedStr);

    }
    public void donateButtonPressed (View view)
    {
        int donateAmount = amountPicker.getValue();
        int radioId = paymentMethod.getCheckedRadioButtonId();
        String method = radioId == id.PayPal ? "PayPal" : "Direct";
        if(donateAmount==0){
            String text = amountText.getText().toString();
            if (!text.equals(""))
                donateAmount = Integer.parseInt(text);
        }
        if(donateAmount>0)
        {
            app.newDonation(new Donation(donateAmount,method));
            minAmountPicker = app.totalDonated ;
            progressBar.setProgress(minAmountPicker);
            totalDonatedStr = "$" + app.totalDonated;
            minAmountPicker = app.totalDonated;
            amountTotal.setText(totalDonatedStr);

        }
    }


    public void reset(MenuItem item)
    {
        app.dbManager.reset();
        app.totalDonated = 0;
        progressBar.setProgress(app.totalDonated);
        amountTotal.setText("S" + app.totalDonated);


    }
}