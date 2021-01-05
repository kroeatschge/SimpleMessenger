package com.kro.simplemessenger;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

public class ColorPicker extends AppCompatActivity {
    private String myColorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        //set the colour taken from settings as background
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        if (savedInstanceState != null) {
            myColorText = savedInstanceState.getString("myColorText");
        }else {
            myColorText = sharedPref.getString("backgroundColor", "");
        }

    }

    /**
     * save myColorText variable
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("myColorText", myColorText);
    }

    /**
     * onResume set the background color of the view
     */
    @Override
    protected void onResume() {
        super.onResume();
        ViewGroup myLayout;


        //get reference to the view
        myLayout = (ConstraintLayout) findViewById(R.id.portrait);
        if (myLayout == null){
            myLayout = (ConstraintLayout) findViewById(R.id.landscape);
        }

        /*
           set backgroundColor of view
           Title: Set Background color programmatically
           Author: Piyush
           Date: 07/05/2014
           Source: https://stackoverflow.com/questions/23517879/set-background-color-programmatically
         */
        myLayout.setBackgroundColor(Color.parseColor(myColorText));
    }

    /**
     * Handle ColorPicker clicks on buttons
     * @param view current view element
     */
    public void onColorPicker(View view) {
        Button myButton = (Button) findViewById(view.getId());

        /*
          get resource by name as getting background tint is only possible from API 21+
          Author: theJoe7
          Date: 19/12/2013
          Title: Android : ImageView getID(); returning integer
          Source: https://stackoverflow.com/questions/3920833/android-imageview-getid-returning-integer
         */
        String myId = getResources().getResourceEntryName(myButton.getId());

        /*
          get color by name
          Author: mihanovak1024
          Date: 02/10/2016
          Title: Android : Possible to get color with string?
          Source: https://stackoverflow.com/questions/39817299/possible-to-get-color-with-string
         */
        int colorId = getResources().getIdentifier(myId, "color", getPackageName());
        Integer myColor = getResources().getColor(colorId);

        //set the class attribute to remember the selected color
        myColorText = getResources().getString(colorId);
        //Toast.makeText(this, myColorText, Toast.LENGTH_SHORT).show();

        //get reference to the parent view and set its color
        ViewGroup myLayout;
        myLayout = (ConstraintLayout) view.getParent();
        myLayout.setBackgroundColor(myColor);
    }

    /**
     * save the currently selected color as background
     * @param view current view element
     */
    public void onSaveClick(View view) {

        /*
          get settings editor
          Author: Michael Yaworski
          Title: How to save app settings?
          Date: 14/10/2013
          Source: https://stackoverflow.com/questions/19353758/how-to-save-app-settings
         */
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString("backgroundColor", myColorText);
        prefEditor.apply();

        Toast.makeText(this, "Background colour saved: " + myColorText, Toast.LENGTH_SHORT).show();

        //end the activity
        finish();

    }

}