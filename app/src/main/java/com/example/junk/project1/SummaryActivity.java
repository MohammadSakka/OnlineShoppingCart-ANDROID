package com.example.junk.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.junk.project1.model.Clothes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SummaryActivity extends Activity {

    private ArrayList<Clothes> summaryItems = new ArrayList<>();

    private double subtotal;
    private double tax;
    private double shippingFee;
    private double total;

    private String username;
    private String password;

    private String date;
    int day;
    int month;
    int year;

    private ListView lstSum;
    private TextView txtText;
    private TextView txtNumber;
    private RadioGroup rgpShipping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        txtText = findViewById(R.id.txt_text);
        txtNumber = findViewById(R.id.txt_number);

        Button btnBack = findViewById(R.id.btn_back);
        Button btnCalculate = findViewById(R.id.btn_calculate);
        Button btnLaunch = findViewById(R.id.btn_launch);

        // getting selectedItems from shoppingActivity and put it into summaryItems
        summaryItems = getIntent().getParcelableArrayListExtra("selectedItems");
        subtotal = getIntent().getDoubleExtra("subtotal", subtotal);
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        rgpShipping = findViewById(R.id.rgp_shipping_fee);


        lstSum = findViewById(R.id.list);

        // set list view adapter to all summary items
        ArrayAdapter<Clothes> adapter = new ArrayAdapter<>(this,
                R.layout.list_summary, R.id.txt_summary, summaryItems);

        lstSum.setAdapter(adapter);

        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateTotal();
            }
        });

        // finish activity to go back to ShoppingActivity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // start paymentActivity
        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchPaymentActivity();
            }
        });

    }

    // start shoppingActivity and pass username and password in the intent
    private void launchPaymentActivity() {

        Intent intent = new Intent(
                getApplicationContext(), PaymentActivity.class);

        intent.putExtra("username", username);
        intent.putExtra("password", password);

        startActivity(intent);
    }

    // change shipping price and receiving date depending on selected radio button
    private void selectShoppingFee() {

        switch (rgpShipping.getCheckedRadioButtonId()) {
            case R.id.rad_normal:
                shippingFee = 10;
                // shows dd/mm/yy
                date = (day + 5) + "/" + (month + 1) + "/" + (year - 2000);
                break;

            case R.id.rad_express:
                shippingFee = 20;
                date = (day + 1) + "/" + (month + 1) + "/" + (year - 2000);
                break;
        }
    }

    // calculate all numbers and display it on TextViews using StringBuilder
    private void calculateTotal() {
        selectShoppingFee();
        tax = (subtotal + shippingFee) * 0.13;
        total = subtotal + tax + shippingFee;

        StringBuilder text = new StringBuilder();
        text.append("Date: ");
        text.append("\nSubtotal:");
        text.append("\nShipping Fee:");
        text.append("\nTax:");
        text.append("\nTotal:");

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        StringBuilder number = new StringBuilder();
        number.append(date);
        number.append("\n$" + decimalFormat.format(subtotal));
        number.append("\n$" + decimalFormat.format(shippingFee));
        number.append("\n$" + decimalFormat.format(tax));
        number.append("\n$" + decimalFormat.format(total));

        txtText.setText(text.toString());
        txtNumber.setText(number.toString());
    }

    // inflate menu and add items to the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    // starting MainActivity when logout is clicked from the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {

            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
