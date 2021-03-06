package com.kro.simplemessenger;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    private EditText myEmail;
    private EditText myMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
          implement OnFocusChangeListener
          Author: Ian
          Title: EditText setOnFocusChangeListener on all EditTexts
          Date: 01/05/2013
          Source: https://stackoverflow.com/questions/16311609/edittext-setonfocuschangelistener-on-all-edittexts
         */
        View.OnFocusChangeListener myFocusListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        };

        //get reference to view
        myEmail = findViewById(R.id.editEmail);
        myMessage = findViewById(R.id.editMessage);

        //add onFocusChangeListener
        myEmail.setOnFocusChangeListener(myFocusListener);
        myMessage.setOnFocusChangeListener(myFocusListener);

        /*
          initialise default values for settings
          Author: android.developer.com
          Title: Android fundamentals 09.2: App settings
          Date: n.d.
          Source: https://developer.android.com/codelabs/android-training-adding-settings-to-app#3
         */
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);

    }



    /**
     * onResume set the background color of the view
     */
    @Override
    protected void onResume() {
        super.onResume();
        //set the colour taken from settings as background
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String bgColor = sharedPref.getString("backgroundColor", "");

        /*
          set backgroundColor of view
          Title: Set Background color programmatically
          Author: Piyush
          Date: 07/05/2014
          Source: https://stackoverflow.com/questions/23517879/set-background-color-programmatically
         */
        //get reference to the view
        ViewGroup mainLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        mainLayout.setBackgroundColor(Color.parseColor(bgColor));
    }

    /**
     * hide the keyboard when losing focus
     * @param view current view element
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Author: adityamshidlyali, geeksforgeeks.org
     * Title: Implement Email Validator in Android
     * Date: 14/12/2020
     * Source: https://www.geeksforgeeks.org/implement-email-validator-in-android/
     * @param checkMail EditText to be checked
     * @return true if correct email address
     */
    public boolean emailValidator(EditText checkMail){
        // extract the entered data from the EditText
        String emailToText = checkMail.getText().toString();

        // Android offers the inbuilt patterns which the entered
        // data from the EditText field needs to be compared with
        // In this case the the entered data needs to compared with
        // the EMAIL_ADDRESS, which is implemented same below
        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            return true;
        } else {
            Toast.makeText(this,getString(R.string.check_email), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    /**
     * handle send click with another APP
     * Author: Dawn Griffiths & David Griffiths
     * Date: 2015
     * Title: Head First Android Development
     * Source: p.98
     * @param view current view element
     */
    public void onSendClick(View view) {
        //get message and email
        String messageText = myMessage.getText().toString();
        String[] emailTo = new String[1];
        emailTo[0] = myEmail.getText().toString();

        //check message entered
        if(messageText.equals("")){
            Toast.makeText(this,getString(R.string.check_message), Toast.LENGTH_SHORT).show();
            return;
        }

        //check email entered
        if(emailValidator(myEmail)) {
            //setup intent
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            //add message and email
            intent.putExtra(Intent.EXTRA_TEXT, messageText);
            intent.putExtra(Intent.EXTRA_EMAIL, emailTo);
            //send intend to android and always show chooser
            startActivity(Intent.createChooser(intent, getResources().getText(R.string.app_name)));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * handle click on menu item
     * @param item item that was clicked
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //show Activity with color picker
        if (id == R.id.action_mysettings) {
            Intent intent = new Intent(this, ColorPicker.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}