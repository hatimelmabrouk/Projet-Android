package com.example.bouhaza.memory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.view.Menu;

/**
 * Created by Utilisateur on 28/11/2016.
 */

public class ScoresActivity extends Activity {


    private String message = null ;
    int meilleurspoints = 0 ;
    private TextView texte = null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_scores);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            SharedPreferences Prefs = this.getSharedPreferences("prefs", MODE_WORLD_READABLE);
            SharedPreferences.Editor prefsEditor = Prefs.edit();

            meilleurspoints = Prefs.getInt("bestscore", 0);
            message = "Meilleur score = " + Integer.toString(meilleurspoints);

            texte = new TextView(this);
            texte.setText(message);
            setContentView(texte);
        }
}







