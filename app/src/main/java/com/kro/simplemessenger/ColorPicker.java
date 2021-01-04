package com.kro.simplemessenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ColorPicker extends AppCompatActivity {
    private String myColorText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
    }

    /**
     * Handle ColorPicker clicks
     * @param view current view element
     */
    public void onColorPicker(View view){
        Button myButton = (Button) findViewById(view.getId());

        /**
         * get resource by name as getting background tint is only possible from API 21+
         * Author: theJoe7
         * Date: 19/12/2013
         * Title: Android : ImageView getID(); returning integer
         * Source: https://stackoverflow.com/questions/3920833/android-imageview-getid-returning-integer
         */
        String myId = getResources().getResourceEntryName(myButton.getId());

        /**
         * get color by name
         * Author: mihanovak1024
         * Date: 02/10/2016
         * Title: Android : Possible to get color with string?
         * Source: https://stackoverflow.com/questions/39817299/possible-to-get-color-with-string
         */
        int colorId = getResources().getIdentifier(myId, "color", getPackageName());
        Integer myColor = getResources().getColor(colorId);

        //set the class attribute to remember the selected color
        myColorText =  getResources().getString(colorId);
        Toast.makeText(this, myColorText, Toast.LENGTH_SHORT).show();

        //get reference to the parent view and set its color
        ViewGroup myLayout;
        myLayout = (ConstraintLayout) view.getParent();
        myLayout.setBackgroundColor(myColor);
    }

    /**
     * save the currently selected color as background
      * @param view current view element
     */
    public void onSaveClick(View view){
        Toast.makeText(this, myColorText, Toast.LENGTH_SHORT).show();
    }

}