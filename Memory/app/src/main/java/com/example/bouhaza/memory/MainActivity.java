package com.example.bouhaza.memory;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.Window;


/* bouhaza sofiane et hatim elmabrouk*/

public class MainActivity extends Activity implements OnClickListener {

    // declaration des spinners

    private Spinner spinner_level;
    private Spinner spinner_carte;
    private Spinner spinner_portrait;
    private Button play, help, score, sound;
    private boolean soundOn;
    private Typeface font;
    private MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButton();
        SharedPreferences settings = getSharedPreferences("MEMORY", 0);
        soundOn = settings.getBoolean("isSoundOn", true);
        changeSoundImage();

    }
        // declaration des boutons
    public void initButton() {
         play = (Button) findViewById(R.id.play);
         score = (Button) findViewById(R.id.scores);
         sound = (Button) findViewById(R.id.soundbtn);
         help = (Button) findViewById(R.id.about);

        // declaration des listeners pour les bouttons

        play.setOnClickListener(this);
        score.setOnClickListener(this);
        sound.setOnClickListener(this);
        help.setOnClickListener(this);

    }

    private void changeSoundImage() {
        if (!soundOn)
            sound.setBackgroundResource(R.drawable.soundoff);
    }



    @Override
    public void onClick(View v) {
        Intent inten;
        switch (v.getId()) {
            case R.id.play:
                inten = new Intent(MainActivity.this, PlayActivity.class);
                inten.putExtra("isSoundOn", soundOn);
                startActivity(inten);
                break;
            case R.id.about:
                inten = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(inten);
                break;
            case R.id.scores:
                inten = new Intent(MainActivity.this, ScoresActivity.class);
                startActivity(inten);
                break;
            case R.id.soundbtn:
                if (soundOn) {
                    soundOn = false;
                    sound.setBackgroundResource(R.drawable.soundoff);
                } else {
                    soundOn = true;
                    sound.setBackgroundResource(R.drawable.sound);
                    playSound();

                }
                break;
            default:
                break;
        }

    }




        /* spinner pour afficher les items pour la difficulté du jeu

        spinner_level = (Spinner) findViewById(R.id.spinner_level);
        ArrayAdapter<CharSequence> adapter
                = ArrayAdapter.createFromResource(this,
                R.array.level, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_level.setAdapter(adapter);
        spinner_level.setOnItemSelectedListener(this);


        // spinner pour afficher les items pour choisir le type de cartes

                spinner_carte = (Spinner) findViewById(R.id.spinner_cartes);
        ArrayAdapter<CharSequence> adapter2
                = ArrayAdapter.createFromResource(this,
                R.array.cartes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_carte.setAdapter(adapter2);
        spinner_carte.setOnItemSelectedListener(this);

        // spinner pour afficher les items pour choisir le mode portrait

        spinner_portrait = (Spinner) findViewById(R.id.spinner_portrait);
        ArrayAdapter<CharSequence> adapter3
                = ArrayAdapter.createFromResource(this,
                R.array.portrait, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_portrait.setAdapter(adapter3);
        spinner_portrait.setOnItemSelectedListener(this);

        */



    // action a faire lorsque l'on clique sur les trois boutons différents


    // cette méthode permet de jouer la mélodie que l'on a enregistrer dans le dossier raw

    private void playSound() {

        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        } catch (Exception e) {
            mediaPlayer = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.setVolume(6, 6);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }
    }




}



