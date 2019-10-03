package com.example.junk.project1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.junk.project1.db.AccountDb;
import com.example.junk.project1.model.Account;

import java.util.ArrayList;

public class CreateAccount extends Activity {

    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtConfirmPassword;

    public String password;
    public String username;
    private String confirmPassword;
    // private String currentAccount;
    ArrayList<Account> currentAccount = new ArrayList<>();

    RadioButton radByName;

    private SQLiteDatabase database;

    String priceRange;
    String clothesCategory;
    String cardType;
    String cardNumber;
    String expiryDate;
    String cvcCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        radByName = findViewById(R.id.radByName);

        Button btnCreate = findViewById(R.id.btn_create);
        Button btnBack = findViewById(R.id.btn_back);
        Button btnDelete = findViewById(R.id.btn_delete);

        // deletes all accounts or by name from SQLdatabase
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radByName.isChecked()) {
                    getNameFromUser();

                } else {
                    deleteAccounts(null);
                }
            }


        });

        // finishes activity to go back to MainActivity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // create button saves new instances of account
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();

            }

        });
    }

    // method to create an instance of account from what user entered
    private Account loadAccount() {

        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();
        priceRange = "";
        clothesCategory = "";
        cardType = "";
        cardNumber = "";
        expiryDate = "";
        cvcCode = "";

        return new Account(username, password, priceRange, clothesCategory,
                cardType, cardNumber, expiryDate, cvcCode);

    }

    private void save() {
        // load account from UI
        loadAccount();
        confirmPassword = edtConfirmPassword.getText().toString();

        // performs error checking
        // set error when username is null
        if (username.length() == 0 || username == null) {
            edtUsername.setError("Username cannot be empty");

        }
        // set error when password is null or less or equal to 4 characters long
        if (password.length() <= 4 || password == null) {
            edtPassword.setError("Password has to be at least 5 characters");

        }
        //set error when confirm password is null
        if (confirmPassword.length() == 0 || confirmPassword == null) {
            edtConfirmPassword.setError("Confirm password cannot be empty");

            // performs all other checks including password must equal confirm password
        } else if (password.equals(confirmPassword) && username.length() != 0
                && password.length() >= 5) {

            // create instance of account
            Account account = loadAccount();

            currentAccount.clear();

            AccountDb accountDb = new AccountDb(getApplicationContext());

            // get current account from database and add it to currentAccount
            currentAccount = accountDb.getAccounts(username, password);

            // check to see if account already exits
            // Save new account only if account does not exist in database

            if (currentAccount.size() != 0) {
                Toast.makeText(this, "Account already exists "
                        , Toast.LENGTH_SHORT).show();
            } else {

                accountDb.saveAccount(account);

                Toast.makeText(this, "Account: " + username + " created!"
                        , Toast.LENGTH_SHORT).show();

            }

            //set error when password doesn't equal confirm password
        } else if (password.equals(confirmPassword) == false) {
            edtConfirmPassword.setError("Password and confirm password doesn't match");

        }

    }

    // deleting account and toasting number of rows deleted
    private void deleteAccounts(String name) {

        AccountDb accountDb = new AccountDb(getApplicationContext());

        int numRows = accountDb.deleteAccounts(name);

        Toast.makeText(this, "Number of deleted rows: " + numRows,
                Toast.LENGTH_LONG).show();


    }

    // creates AlertDialog to get name from the user using EditText to delete account by name
    private void getNameFromUser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText edtInput = new EditText(this);
        edtInput.setHint("Name");
        builder.setView(edtInput)
                .setTitle("Enter Name to search for")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edtInput.getText().toString();
                        deleteAccounts(name);
                    }

                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}












