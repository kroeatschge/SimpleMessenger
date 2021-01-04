package com.kro.simplemessenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class MainActivity extends AppCompatActivity {
    private EditText myEmail;
    private EditText myMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get reference to view
        myEmail = findViewById(R.id.editEmail);
        myMessage = findViewById(R.id.editMessage);

        //add onFocusChangeListener
       //myEmail.setOnFocusChangeListener(onFocusChange());

    }

    /**
     * hide the keyboard
     * @param view
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            hideKeyboard(view);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            /**
             * Source: https://stackoverflow.com/questions/53902494/navigation-component-cannot-find-navcontroller
             * Author: JunaidKhan
             * Date: 03/12/2019
             * Title: Navigation Component: Cannot find NavController
             */
           // Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_FirstFragment_to_SecondFragment);

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;


        }

        return super.onOptionsItemSelected(item);
    }
}