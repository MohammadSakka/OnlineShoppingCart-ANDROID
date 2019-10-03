package com.example.junk.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ConfirmationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Button btnLogout = findViewById(R.id.btn_logout);

        // logout button launches back to MainActivity
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();

            }
        });

    }

    // method starting MainActivity class
    private void launchMainActivity(){

        Intent intent = new Intent(
                getApplicationContext(), MainActivity.class);

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

}
