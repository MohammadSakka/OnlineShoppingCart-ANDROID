package com.example.junk.project1;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.junk.project1.db.AccountDb;
import com.example.junk.project1.model.Account;


import java.util.ArrayList;


public class MainActivity extends Activity {

    private EditText edtUsername;
    private EditText edtPassword;

    String username;
    String password;

    ArrayList<Account> currentAccount = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Visual Clothing");

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnCreate = findViewById(R.id.btn_create);

        // perform error checking and login(start ShoppingActivity)
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorCheck();

                }

        });

        // start createAccount activity
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCreateAccountActivity();
            }
        });
    }

    // start shoppingActivity and pass username and password in the intent
    private void launchShoppingActivity() {

        Intent intent = new Intent(
                getApplicationContext(), ShoppingActivity.class);

        intent.putExtra("username", edtUsername.getText().toString());
        intent.putExtra("password",edtPassword.getText().toString());

        startActivity(intent);

    }

    //start createAccount activity
    private void launchCreateAccountActivity() {

        Intent intent = new Intent(
                getApplicationContext(), CreateAccount.class);
        startActivity(intent);

    }

    // perform error checking and call launchShoppingActivity method
    private void errorCheck(){

        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();

        // set error when username is null
        if (username.length() == 0 || username == null) {

            edtUsername.setError("Username cannot be empty");

        }
        // set error when is password is null or less than 5 characters
        if ( password.length() <= 4 || password == null) {

            edtPassword.setError("Password has to be more than 5 characters");


            // if it passes all error checking
        } else if ( username.length() != 0 &&  password.length() >= 5) {

            AccountDb accountDb = new AccountDb(getApplicationContext());

            currentAccount.clear();

            // get current account from database and add it to currentAccount
            currentAccount = accountDb.getAccounts(username,password);

            // check to see if account already exits
            if (currentAccount.size() != 0) {
                launchShoppingActivity();
            }

            else {
                Toast.makeText(MainActivity.this,
                        "Username or password doesn't match the saved account ",
                        Toast.LENGTH_LONG).show();
            }


        }

    }

}




