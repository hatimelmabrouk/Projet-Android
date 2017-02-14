package com.example.bouhaza.memory;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.view.View;


/**
 * Created by Utilisateur on 28/11/2016.
 */

public class PlayActivity extends Activity {

    EditText t1;
    EditText t2;
    EditText t3;
    private PlayView mPlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // initialise notre activity avec le constructeur parent
        super.onCreate(savedInstanceState);
        // charge le fichier activity_play.xml comme vue de l'activité
        setContentView(R.layout.activity_play);
        // recuperation de la vue une voie cree � partir de son id
        mPlayView = (PlayView) findViewById(R.id.PlayView);
        // rend visible la vue
        mPlayView.setVisibility(View.VISIBLE);


// pour savoir quel spinne a été choisi

        /*t1=(EditText) findViewById(R.id.editText);
        t2=(EditText) findViewById(R.id.editText2);
        t3=(EditText) findViewById(R.id.editText3);
        Intent intent = getIntent();
        String s= intent.getStringExtra("jouer");
        String s1= intent.getStringExtra("moi");
        String s2= intent.getStringExtra("toi");
        t1.setText(s);
        t2.setText(s1);
        t3.setText(s2); */


    }
}




