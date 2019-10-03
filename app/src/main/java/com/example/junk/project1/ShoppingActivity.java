package com.example.junk.project1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.junk.project1.db.AccountDb;
import com.example.junk.project1.model.Clothes;
import com.example.junk.project1.model.Account;

import java.util.ArrayList;

public class ShoppingActivity extends Activity {

    ArrayList<Clothes> currentItems = new ArrayList<>();
    ArrayList<Clothes> selectedItems = new ArrayList<>();
    ArrayList<Clothes> priceRangeItems = new ArrayList<>();
    ArrayList<Clothes> clothes = new ArrayList<>();

    ArrayList<Clothes> jackets = Clothes.getJackets();
    ArrayList<Clothes> sweaters = Clothes.getSweaters();
    ArrayList<Clothes> boots = Clothes.getBoots();

    RecyclerView mRecyclerView;

    private int i;

    Spinner spnPrice;
    Spinner spnCategory;

    private String username;
    private String password;
    private String priceRange;
    private String clothesCategory;

    private AccountDb accountDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        spnPrice = findViewById(R.id.spn_price_range);
        spnCategory = findViewById(R.id.spn_clothes_category);

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        mRecyclerView = findViewById(R.id.recycler_view);

        Button btnLaunch = findViewById(R.id.btn_launch);
        Button btnSetRange = findViewById(R.id.btn_range);
        Button btnShowItems = findViewById(R.id.btn_show_items);

        clothes.addAll(jackets);
        clothes.addAll(sweaters);
        clothes.addAll(boots);

        loadClothes();

        // sets price range and clothes category and changes adapter to show those clothes items
        btnSetRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectClothesCategory();
                selectPriceRange();

            }
        });

        // shows selected items
        btnShowItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectedItems();

            }
        });

        // loads selected item,
        // update users preferred price range and clothes category to SQLdatabase
        // and start SummaryActivity
        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCheckedClothes();
                update();
            }
        });
    }

    // show all selected items with selected size and quantity
    private void showSelectedItems() {
        selectedItems.clear();
        loadCheckedClothes();
        ClothesAdapter adapter =
                new ClothesAdapter(ShoppingActivity.this, selectedItems);

        mRecyclerView.setAdapter(adapter);

    }

    // set mRecyclerView to show items of clothes. Method called when activity first starts
    private void loadClothes() {

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(manager);

        // set mRecyclerView to show all clothes if selectedItems is empty
        if (selectedItems.size() == 0) {
            ClothesAdapter adapter =
                    new ClothesAdapter(ShoppingActivity.this, clothes);

            mRecyclerView.setAdapter(adapter);
        }

        // otherwise set mRecyclerView to show selectedItems
        else {
            ClothesAdapter selectedAdapter =
                    new ClothesAdapter(ShoppingActivity.this, selectedItems);

            mRecyclerView.setAdapter(selectedAdapter);
        }

    }

    // loads all items selected by user
    private void loadCheckedClothes() {

        //clear selectedItems so it doesn't duplicate items selected when launching SummaryActivity
        selectedItems.clear();
        for (int i = 0; i < clothes.size(); i++) {
            // get all items that is selected
            if (clothes.get(i).getChecked()) {

                //add to selectedItems only if it already doesn't contain that selected item
                if (selectedItems.contains(clothes.get(i).getChecked())) {
                    selectedItems.remove(clothes.get(i).getChecked());
                } else {
                    selectedItems.add(clothes.get(i));
                }
            }
        }

    }

    // update priceRange and clothesCategory information and start summaryActivity
    private void update() {
        //if selectedItems is not empty,
        // put values in content values according to selected range and category from user
        if (selectedItems.size() != 0) {

            ContentValues cv = new ContentValues();
            cv.put("priceRange", priceRange);
            cv.put("clothesCategory", clothesCategory);

            // specifying columns username and password
            // and finding row where it matches name and passWord
            String where = "username=? AND password=?";
            String[] whereArgs = new String[]{username, password};

            accountDb = new AccountDb(getApplicationContext());
            accountDb.database = accountDb.openHelper.getWritableDatabase();

            // calling the update on the database
            accountDb.database.update(accountDb.ACCOUNT_TABLE, cv, where, whereArgs);

            accountDb.database.close();

            launchSummaryActivity();

        } else {

            Toast.makeText(ShoppingActivity.this,
                    "You must select at least one item",
                    Toast.LENGTH_LONG).show();
        }
    }

    // start SummaryActivity and pass in subtotal, username and password in the intents
    private void launchSummaryActivity() {

        double subtotal = 0;
        // get price of selectedItems and multiply by quantities
        for (i = 0; i < selectedItems.size(); i++) {
            subtotal += selectedItems.get(i).price * Double.valueOf(selectedItems.get(i).quantity);
        }

        Intent intent = new Intent(
                getApplicationContext(), SummaryActivity.class);
        intent.putParcelableArrayListExtra("selectedItems", selectedItems);
        intent.putExtra("subtotal", subtotal);

        intent.putExtra("username", username);
        intent.putExtra("password", password);

        startActivity(intent);

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

    // show selected price range items on mRecyclerView
    private void selectPriceRange() {

        //get selected item from price spinner
        String interval = spnPrice.getSelectedItem().toString();

        priceRangeItems.clear();

        // check if interval matches price values
        if (interval.matches("0-30")) {

            // set priceRange(String) according to price value
            priceRange = "0-30";

            for (i = 0; i < currentItems.size(); i++) {

                // add all the currentItems that has price in range(0-30) to priceRangeItems
                if (0 <= currentItems.get(i).price && currentItems.get(i).price <= 30) {
                    priceRangeItems.add(currentItems.get(i));
                }
            }

        } else if (interval.matches("30-60")) {
            priceRange = "30-60";
            for (i = 0; i < currentItems.size(); i++) {

                if (30 <= currentItems.get(i).price && currentItems.get(i).price <= 60) {
                    priceRangeItems.add(currentItems.get(i));
                }
            }

        } else if (interval.matches("60-100")) {
            priceRange = "60-100";

            for (i = 0; i < currentItems.size(); i++) {

                if (60 <= currentItems.get(i).price && currentItems.get(i).price <= 100) {
                    priceRangeItems.add(currentItems.get(i));
                }
            }
        } else if (interval.matches("All")) {
            priceRange = "All";
            for (i = 0; i < currentItems.size(); i++) {
                priceRangeItems.add(currentItems.get(i));
            }

        }

        // set adapter to priceRangeItems
        ClothesAdapter adapter =
                new ClothesAdapter(ShoppingActivity.this, priceRangeItems);

        mRecyclerView.setAdapter(adapter);

    }

    private void selectClothesCategory() {

        //get selected item from category spinner
        String interval = spnCategory.getSelectedItem().toString();

        currentItems.clear();

        // check if interval matches category values
        if (interval.matches("Jacket")) {

            // set categoryRange(String) according to category value
            clothesCategory = "Jacket";

            //add all items from catgory into currentItems
            currentItems.addAll(jackets);

        } else if (interval.matches("Sweater")) {
            clothesCategory = "Sweater";
            currentItems.addAll(sweaters);

        } else if (interval.matches("Boots")) {
            clothesCategory = "Boots";
            currentItems.addAll(boots);

        } else if (interval.matches("All")) {
            clothesCategory = "All";
            currentItems.addAll(clothes);

        }

        // set adapter to currentItems to show selected category of items
        ClothesAdapter adapter =
                new ClothesAdapter(ShoppingActivity.this, currentItems);

        mRecyclerView.setAdapter(adapter);

    }

    // get current account and set all spinner items according to information from SQL database
    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Account> currentAccount = new ArrayList<>();

        selectedItems.clear();
        currentAccount.clear();

        accountDb = new AccountDb(getApplicationContext());
        accountDb.database = accountDb.openHelper.getReadableDatabase();

        AccountDb accountDb = new AccountDb(getApplicationContext());

        // get current account from database and add it to currentAccount
        currentAccount = accountDb.getAccounts(username, password);

        // get values according to information from SQL database
        priceRange = currentAccount.get(0).getPriceRange();
        clothesCategory = currentAccount.get(0).getClothesCategory();

        ArrayAdapter<String> priceAdapter =
                (ArrayAdapter<String>) spnPrice.getAdapter();

        // find the position of the user-selected value in the Spinner
        int pricePosition = priceAdapter.getPosition(priceRange);

        // set the currently selected item to be that position
        spnPrice.setSelection(pricePosition);

        ArrayAdapter<String> categoryAdapter =
                (ArrayAdapter<String>) spnCategory.getAdapter();

        int categoryPosition = categoryAdapter.getPosition(clothesCategory);

        spnCategory.setSelection(categoryPosition);

        loadClothes();

    }
}



