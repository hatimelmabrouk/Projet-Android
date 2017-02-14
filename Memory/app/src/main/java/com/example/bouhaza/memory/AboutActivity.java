package com.example.bouhaza.memory;

import android.app.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;





/**
 * Created by Utilisateur on 30/11/2016.
 */

public class AboutActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // initialise notre activity avec le constructeur parent
        super.onCreate(savedInstanceState);
        // charge le fichier activity_play.xml comme vue de l'activité
        setContentView(R.layout.activity_about);

    }
}

