package com.example.bouhaza.memory;


import java.util.Random;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by Utilisateur on 28/11/2016.
 */

public class PlayView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    // Declaration des images

    private Bitmap 		maptile;
    private Bitmap		bottombarL;
    private Bitmap		bottombarC;
    private Bitmap		bottombarR;
    private Bitmap		keydown;
    private Bitmap		keyrefresh;
    private Bitmap      imagecarte1;
    private Bitmap      imagecarte2;
    private Bitmap      imagecarte3;
    private Bitmap      imagecarte4;
    private Bitmap      imagecarte5;
    private Bitmap      imagecarte6;
    private Bitmap      imagecarte7;
    private Bitmap      imagecarte8;
    private Bitmap      imagecarte9;
    private Bitmap      imagecarte10;
    private Bitmap      coups;
    private Bitmap      temps;
    private Bitmap      c0;
    private Bitmap      c1;
    private Bitmap      c2;
    private Bitmap      c3;
    private Bitmap      c4;
    private Bitmap      c5;
    private Bitmap      c6;
    private Bitmap      c7;
    private Bitmap      c8;
    private Bitmap      c9;
    private Bitmap      scoreimg;
    private Bitmap      fond ;

    // Declaration des objets Ressources et Context permettant d'acc�der aux ressources de notre application et de les charger

    private Resources 	mem;
    private Context 	memorycontext;

    // bolean pour savoir si on a gagne ou perdu
    public boolean gameover = false;
    public boolean win = false ;

    // thread utiliser pour animer les cartes
    private boolean in	= true;
    private Thread  	cv_thread;
    SurfaceHolder 		holder;


    // tableau de reference du terrain
    private int carteaplacer[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10 };

    private Point lastcart = new Point(-1,-1);

    // aucune carte n'est retournée par défaut
    private int cartetrouver[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private int indice = 0;
    int nbr_coup ;

    private static final int Empty = 0;


    // ancres pour pouvoir centrer la carte du jeu

    int         mapTopAnchor;                   // coordonnÃ©es en Y du point d'ancrage de notre terrain
    int         mapLeftAnchor;                  // coordonnÃ©es en X du point d'ancrage de notre terrain

    int         mapTopAnchor_tmp;                   // coordonnÃ©es en Y du point d'ancrage de notre terrain
    int         mapLeftAnchor_tmp;                  // coordonnÃ©es en X du point d'ancrage de notre terrain

    int 		deckeys;

    int score ;

    // taille de la carte
    static final int    mapWidth    = 5;
    static final int    mapHeight   = 5;
    static final int    mapTileSize = 105;

    // tableau modelisant la carte du jeu
    public      int[][] map;

    private     boolean keyused = false;


    public      boolean carteVIS[][] = new boolean [mapHeight][mapWidth] ;
    private     boolean onecarte = false;

    public      int		refreshlimiter = 0;

    private     Random  rd;

    public long start_time =  System.currentTimeMillis()  ;
    public int test_time;
    public int last_time;
    public int final_time = 99;
    public int affiche_time = final_time;

    private int d_seconde;
    private int u_seconde;

    private int nbr_coup_d;
    private int nbr_coup_u;

    private int score_c;
    private int score_d;
    private int score_u;
    public long time_now ;

    public int nbr_to_win = 10 ;
    /**
     * The constructor called from the main JetBoy activity
     *
     * @param context
     * @param attrs
     */
    public PlayView(Context context, AttributeSet attrs) {
        super(context, attrs);


        // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed
        holder = getHolder();
        holder.addCallback(this);

        memorycontext= context;
        mem 	= memorycontext.getResources();

        //charge les images contenues dans la methode loadimages()
        loadimages(mem);
        //
        rd          = new Random();
        //creation du thread
        cv_thread   = new Thread(this);
        // prise de focus pour gestion des touches
        setFocusable(true);
    }

    // chargement des images
    private void loadimages(Resources res) {
        maptile 		= BitmapFactory.decodeResource(res, R.drawable.cartenv);

        bottombarL		= BitmapFactory.decodeResource(res, R.drawable.bottombar_01);
        bottombarC		= BitmapFactory.decodeResource(res, R.drawable.bottombar_02);
        bottombarR		= BitmapFactory.decodeResource(res, R.drawable.bottombar_03);
        keydown			= BitmapFactory.decodeResource(res, R.drawable.keydown);
        keyrefresh		= BitmapFactory.decodeResource(res, R.drawable.keyrefresh);


        scoreimg = BitmapFactory.decodeResource(res,R.drawable.score);

        imagecarte1 = BitmapFactory.decodeResource(res, R.drawable.carte1);
        imagecarte2 = BitmapFactory.decodeResource(res, R.drawable.carte2) ;
        imagecarte3 = BitmapFactory.decodeResource(res, R.drawable.carte3) ;
        imagecarte4 = BitmapFactory.decodeResource(res, R.drawable.carte4);
        imagecarte5 = BitmapFactory.decodeResource(res, R.drawable.carte5);
        imagecarte6 = BitmapFactory.decodeResource(res, R.drawable.carte6);
        imagecarte7 = BitmapFactory.decodeResource(res, R.drawable.carte7);
        imagecarte8 = BitmapFactory.decodeResource(res, R.drawable.carte8);
        imagecarte9 = BitmapFactory.decodeResource(res, R.drawable.carte9);
        imagecarte10 = BitmapFactory.decodeResource(res, R.drawable.carte10);

        coups = BitmapFactory.decodeResource(res, R.drawable.coups);
        temps = BitmapFactory.decodeResource(res, R.drawable.temps);

        c0 = BitmapFactory.decodeResource(res, R.drawable.c0);
        c1 = BitmapFactory.decodeResource(res, R.drawable.c1);
        c2 = BitmapFactory.decodeResource(res, R.drawable.c2);
        c3 = BitmapFactory.decodeResource(res, R.drawable.c3);
        c4 = BitmapFactory.decodeResource(res, R.drawable.c4);
        c5 = BitmapFactory.decodeResource(res, R.drawable.c5);
        c6 = BitmapFactory.decodeResource(res, R.drawable.c6);
        c7 = BitmapFactory.decodeResource(res, R.drawable.c7);
        c8 = BitmapFactory.decodeResource(res, R.drawable.c8);
        c9 = BitmapFactory.decodeResource(res, R.drawable.c9);


    }



    // initialisation du jeu
    public void initparameters() {
        map                 = new int[mapHeight][mapWidth];
        mapTopAnchor        = (getHeight()- mapHeight*mapTileSize- bottombarC.getHeight())/2;
        mapLeftAnchor       = (getWidth()- mapWidth*mapTileSize)/2;
        mapTopAnchor_tmp    = mapTopAnchor;
        mapLeftAnchor_tmp   = mapLeftAnchor;
        start_time = System.currentTimeMillis();
        time_now = System.currentTimeMillis();
        last_time = (int) (time_now - start_time)/1000;
        deckeys 			= (getWidth()- 5* keydown.getWidth()- 4*5)/2;

        for(int i = 0 ; i<10 ; i++)
            cartetrouver[i]=0;
        nbr_coup = 99;
        nbr_to_win = 10;
        win = false ;
        keyused  = false;
        initmap();
        initVIS();


        if ((cv_thread!=null) && (!cv_thread.isAlive())) {
            cv_thread.start();
        }
    }


    // action a faire lorsque l'on clique sur une carte
    private void check_carte(int x_c , int y_c) {
        boolean exist = false ;

        for(int i=0;i<10;i++)
        {
            if(cartetrouver[i]==Math.abs(map[x_c][y_c]))
                exist=true;

        }

        if(exist==false)
        {
            if(!onecarte)
            {
                onecarte = true ;
                lastcart.setPoint(x_c,y_c);
            } else
            {
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(map[x_c][y_c]+map[lastcart.getX()][lastcart.getY()]!=0)
                {
                    carteVIS[lastcart.getX()][lastcart.getY()] = false;
                    carteVIS[x_c][y_c] = false;
                    onecarte = false;
                    nbr_coup--;


                }
                else
                {
                    carteVIS[lastcart.getX()][lastcart.getY()] = true;
                    carteVIS[x_c][y_c] = true;
                    onecarte = false;
                    lastcart.setPoint(-1, -1);
                    cartetrouver[indice] = Math.abs(map[x_c][y_c]);
                    indice++;
                    nbr_to_win--;

                }
            }

        }

    }
    //initialisation de la carte
    private void initmap() {
        int x , y ;
        Random r = new Random();

        for (int i=0; i< mapHeight; i++) {
            for (int j=0; j< mapWidth; j++) {

                map[i][j]   = Empty ;


            }
        }


        for (short i = 0; i < carteaplacer.length; i++) {

            x = r.nextInt(4);
            y = r.nextInt(5);
            while (map[y][x] != 0) {
                x = r.nextInt(4);
                y = r.nextInt(5);
            }
            map[y][x] = carteaplacer[i];
        }


    }

    private void initVIS() {
        for (int i=0; i< mapHeight; i++) {
            for (int j=0; j< mapWidth; j++) {

                carteVIS[i][j] = false ;

            }
        }
    }


    /**
     * run (run du thread cr��)
     * on endort le thread, on modifie le compteur d'animation, on prend la main pour dessiner et on dessine puis on lib�re le canvas
     */
    public void run() {
        Canvas c = null;

        while (in) {


            try {

                cv_thread.sleep(40);
                refreshlimiter = (refreshlimiter + 1) %3;
                if(!win)
                {
                    if(!gameover)
                    {

                        test_time = (int) (time_now - start_time)/1000;
                        if(test_time != last_time)
                        {
                            affiche_time--;

                            if(affiche_time<=0)
                            {
                                affiche_time = 0;
                            }
                            last_time=test_time;
                        }
                        d_seconde = affiche_time/10;
                        u_seconde = affiche_time%10;
                        nbr_coup_d = nbr_coup/10;
                        nbr_coup_u = nbr_coup%10;
                        time_now = System.currentTimeMillis();
                        //Log.e("RUN TIME"," seconde "+ test_time);
                    }
                }


                if(nbr_to_win==0)
                {

                    score =  nbr_coup+affiche_time;
                    score = score/10;
                    score_d = score /10 ;
                    score_u = score % 10;
                    win = true ;

                    Log.e("-> SCORE <-", " = " + score);

                }

                if( test_time > final_time)
                {
                    gameover = true;
                }

                if(nbr_coup==0)
                {
                    gameover = true;
                }





                try {
                    c = holder.lockCanvas(null);
                    dessin(c);
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch(Exception e) {
                Log.e("-> RUN <-", "PB DANS RUN" + e.getMessage());
            }
        }
    }


    // dessin de la carte du jeu
    public void paintMap(Canvas canvas) {

        Bitmap   tmpimg  = maptile;
        for (int i=0; i< mapHeight; i++) {
            for (int j=0; j< mapWidth; j++) {
                switch (Math.abs(map[i][j])) {

                    case 1:
                        if(carteVIS[i][j]==true)
                        {
                            tmpimg  = imagecarte1;
                        }else
                        {
                            tmpimg = maptile;
                        }
                        canvas.drawBitmap(tmpimg, mapLeftAnchor+ j*mapTileSize, mapTopAnchor+ i*mapTileSize, null);
                        break;

                    case 2:
                        if(carteVIS[i][j]==true)
                        {
                            tmpimg  = imagecarte2;
                        }else
                        {
                            tmpimg = maptile;
                        }

                        canvas.drawBitmap(tmpimg, mapLeftAnchor+ j*mapTileSize, mapTopAnchor+ i*mapTileSize, null);
                        break;

                    case 3:

                        if(carteVIS[i][j]==true)
                        {
                            tmpimg  = imagecarte3;
                        }else
                        {
                            tmpimg = maptile;
                        }
                        canvas.drawBitmap(tmpimg, mapLeftAnchor+ j*mapTileSize, mapTopAnchor+ i*mapTileSize, null);
                        break;

                    case 4:
                        if(carteVIS[i][j]==true)
                        {
                            tmpimg  = imagecarte4;
                        }else
                        {
                            tmpimg = maptile;
                        }

                        canvas.drawBitmap(tmpimg, mapLeftAnchor+ j*mapTileSize, mapTopAnchor+ i*mapTileSize, null);
                        break;

                    case 5:
                        if(carteVIS[i][j]==true)
                        {
                            tmpimg  = imagecarte5;
                        }else
                        {
                            tmpimg = maptile;
                        }

                        canvas.drawBitmap(tmpimg, mapLeftAnchor+ j*mapTileSize, mapTopAnchor+ i*mapTileSize, null);
                        break;

                    case 6:
                        if(carteVIS[i][j]==true)
                        {
                            tmpimg  = imagecarte6;
                        }else
                        {
                            tmpimg = maptile;
                        }
                        canvas.drawBitmap(tmpimg, mapLeftAnchor+ j*mapTileSize, mapTopAnchor+ i*mapTileSize, null);
                        break;

                    case 7:
                        if(carteVIS[i][j]==true)
                        {
                            tmpimg  = imagecarte7;
                        }else
                        {
                            tmpimg = maptile;
                        }
                        canvas.drawBitmap(tmpimg, mapLeftAnchor+ j*mapTileSize, mapTopAnchor+ i*mapTileSize, null);
                        break;

                    case 8:
                        if(carteVIS[i][j]==true)
                        {
                            tmpimg  = imagecarte8;
                        }else
                        {
                            tmpimg = maptile;
                        }

                        canvas.drawBitmap(tmpimg, mapLeftAnchor+ j*mapTileSize, mapTopAnchor+ i*mapTileSize, null);
                        break;

                    case 9:
                        if(carteVIS[i][j]==true)
                        {
                            tmpimg  = imagecarte9;
                        }else
                        {
                            tmpimg = maptile;
                        }

                        canvas.drawBitmap(tmpimg, mapLeftAnchor+ j*mapTileSize, mapTopAnchor+ i*mapTileSize, null);
                        break;

                    case 10:
                        if(carteVIS[i][j]==true)
                        {
                            tmpimg  = imagecarte10;
                        }else
                        {
                            tmpimg = maptile;
                        }
                        canvas.drawBitmap(tmpimg, mapLeftAnchor+ j*mapTileSize, mapTopAnchor+ i*mapTileSize, null);
                        break;

                }

            }
        }
    }
    // dessin de la carte si la partie est perdue
    public void paintgameover(Canvas canvas) {
        for (int i=0; i< (getWidth()/ bottombarC.getWidth()) + 1; i++) {
            canvas.drawBitmap(bottombarC, i*bottombarC.getWidth(), (getHeight()- bottombarC.getHeight())/2, null);
        }
        canvas.drawBitmap(bottombarL, 0, (getHeight()- bottombarC.getHeight())/2, null);
        canvas.drawBitmap(bottombarR, getWidth()- bottombarR.getWidth(), (getHeight()- bottombarC.getHeight())/2, null);
        canvas.drawBitmap(keyrefresh, deckeys+ 4* keydown.getWidth()+ 4*5, (getHeight()- bottombarC.getHeight())/2, null);

    }

    // dessin de la carte si la partie est gagnee
    public void paintwin(Canvas canvas) {
        for (int i=0; i< (getWidth()/ bottombarC.getWidth()) + 1; i++) {
            canvas.drawBitmap(bottombarC, i*bottombarC.getWidth(), (getHeight()- bottombarC.getHeight())/2, null);
        }
        canvas.drawBitmap(bottombarL, 0, (getHeight()- bottombarC.getHeight())/2, null);
        canvas.drawBitmap(bottombarR, getWidth()- bottombarR.getWidth(), (getHeight()- bottombarC.getHeight())/2, null);
        canvas.drawBitmap(keyrefresh, deckeys+ 4* keydown.getWidth()+ 4*5, (getHeight()- bottombarC.getHeight())/2, null);


        float positionvertical_u =  ((getHeight()- bottombarC.getHeight())/2 )+28;
        float positionhor_c = 1 ;
        float positionhor_d = 70 ;
        float positionhor_u = 60 ;



        canvas.drawBitmap(scoreimg, positionhor_c, positionvertical_u-25, null);

        switch(score_u)
        {
            case 0:
                canvas.drawBitmap(c0, positionhor_u, positionvertical_u, null);
                break;
            case 1:
                canvas.drawBitmap(c1, positionhor_u, positionvertical_u, null);
                break;
            case 2:
                canvas.drawBitmap(c2, positionhor_u,positionvertical_u, null);
                break;
            case 3:
                canvas.drawBitmap(c3, positionhor_u,positionvertical_u, null);
                break;
            case 4:
                canvas.drawBitmap(c4,positionhor_u, positionvertical_u, null);
                break;
            case 5:
                canvas.drawBitmap(c5,positionhor_u, positionvertical_u, null);
                break;
            case 6:
                canvas.drawBitmap(c6, positionhor_u, positionvertical_u, null);
                break;
            case 7:
                canvas.drawBitmap(c7, positionhor_u,positionvertical_u, null);
                break;
            case 8:
                canvas.drawBitmap(c8, positionhor_u, positionvertical_u, null);
                break;
            case 9:
                canvas.drawBitmap(c9, positionhor_u, positionvertical_u, null);
                break;



        }




        switch(score_d)
        {
            case 0:
                canvas.drawBitmap(c0,positionhor_d, positionvertical_u, null);
                break;

            case 1:
                canvas.drawBitmap(c1, positionhor_d, positionvertical_u, null);
                break;

            case 2:
                canvas.drawBitmap(c2, positionhor_d, positionvertical_u, null);
                break;

            case 3:
                canvas.drawBitmap(c3,positionhor_d, positionvertical_u, null);
                break;

            case 4:
                canvas.drawBitmap(c4,positionhor_d, positionvertical_u, null);
                break;

            case 5:
                canvas.drawBitmap(c5, positionhor_d, positionvertical_u, null);
                break;

            case 6:
                canvas.drawBitmap(c6, positionhor_d, positionvertical_u, null);
                break;

            case 7:
                canvas.drawBitmap(c7, positionhor_d, positionvertical_u, null);
                break;

            case 8:
                canvas.drawBitmap(c8, positionhor_d, positionvertical_u, null);
                break;

            case 9:
                canvas.drawBitmap(c9, positionhor_d, positionvertical_u, null);
                break;


        }







    }

    // dessin du compte a rebours de la partie
    public void paintBottomBar(Canvas canvas) {
        for (int i=0; i< (getWidth()/ bottombarC.getWidth()) + 1; i++) {
            canvas.drawBitmap(bottombarC, i*bottombarC.getWidth(), getHeight()- bottombarC.getHeight(), null);
        }
        canvas.drawBitmap(bottombarL, 0, getHeight()- bottombarC.getHeight(), null);
        canvas.drawBitmap(bottombarR, getWidth()- bottombarR.getWidth(), getHeight()- bottombarC.getHeight(), null);

        canvas.drawBitmap(coups,(deckeys+ 4* c0.getWidth()+ 3*61)/2 -130,  getHeight()- bottombarC.getHeight(), null);
        canvas.drawBitmap(temps,(deckeys+ 4* c0.getWidth()+ 3*61) -160,  getHeight()- bottombarC.getHeight(), null);
        switch(u_seconde)
        {
            case 0:
                canvas.drawBitmap(c0, deckeys+ 4* c0.getWidth()+ 3*61, getHeight()- bottombarC.getHeight(), null);
                break;
            case 1:
                canvas.drawBitmap(c1, deckeys+ 4* c0.getWidth()+ 3*61, getHeight()- bottombarC.getHeight(), null);
                break;
            case 2:
                canvas.drawBitmap(c2, deckeys+ 4* c0.getWidth()+ 3*61, getHeight()- bottombarC.getHeight(), null);
                break;
            case 3:
                canvas.drawBitmap(c3, deckeys+ 4* c0.getWidth()+ 3*61, getHeight()- bottombarC.getHeight(), null);
                break;
            case 4:
                canvas.drawBitmap(c4, deckeys+ 4* c0.getWidth()+ 3*61, getHeight()- bottombarC.getHeight(), null);
                break;
            case 5:
                canvas.drawBitmap(c5, deckeys+ 4* c0.getWidth()+ 3*61, getHeight()- bottombarC.getHeight(), null);
                break;
            case 6:
                canvas.drawBitmap(c6, deckeys+ 4* c0.getWidth()+ 3*61, getHeight()- bottombarC.getHeight(), null);
                break;
            case 7:
                canvas.drawBitmap(c7, deckeys+ 4* c0.getWidth()+ 3*61, getHeight()- bottombarC.getHeight(), null);
                break;
            case 8:
                canvas.drawBitmap(c8, deckeys+ 4* c0.getWidth()+ 3*61, getHeight()- bottombarC.getHeight(), null);
                break;
            case 9:
                canvas.drawBitmap(c9, deckeys+ 4* c0.getWidth()+ 3*61, getHeight()- bottombarC.getHeight(), null);
                break;



        }




        switch(d_seconde)
        {
            case 0:
                canvas.drawBitmap(c0, deckeys+ 3* c1.getWidth()+ 3*60, getHeight()- bottombarC.getHeight(), null);
                break;

            case 1:
                canvas.drawBitmap(c1, deckeys+ 3* c1.getWidth()+ 3*60, getHeight()- bottombarC.getHeight(), null);
                break;

            case 2:
                canvas.drawBitmap(c2, deckeys+ 3* c1.getWidth()+ 3*60, getHeight()- bottombarC.getHeight(), null);
                break;

            case 3:
                canvas.drawBitmap(c3, deckeys+ 3* c1.getWidth()+ 3*60, getHeight()- bottombarC.getHeight(), null);
                break;

            case 4:
                canvas.drawBitmap(c4, deckeys+ 3* c1.getWidth()+ 3*60, getHeight()- bottombarC.getHeight(), null);
                break;

            case 5:
                canvas.drawBitmap(c5, deckeys+ 3* c1.getWidth()+ 3*60, getHeight()- bottombarC.getHeight(), null);
                break;

            case 6:
                canvas.drawBitmap(c6, deckeys+ 3* c1.getWidth()+ 3*60, getHeight()- bottombarC.getHeight(), null);
                break;

            case 7:
                canvas.drawBitmap(c7, deckeys+ 3* c1.getWidth()+ 3*60, getHeight()- bottombarC.getHeight(), null);
                break;

            case 8:
                canvas.drawBitmap(c8, deckeys+ 3* c1.getWidth()+ 3*60, getHeight()- bottombarC.getHeight(), null);
                break;

            case 9:
                canvas.drawBitmap(c9, deckeys+ 3* c1.getWidth()+ 3*60, getHeight()- bottombarC.getHeight(), null);
                break;


        }






        switch(nbr_coup_u)
        {
            case 0:
                canvas.drawBitmap(c0, (deckeys+ 4* c0.getWidth()+ 3*61)/2, getHeight()- bottombarC.getHeight(), null);
                break;
            case 1:
                canvas.drawBitmap(c1, (deckeys+ 4* c0.getWidth()+ 3*61)/2, getHeight()- bottombarC.getHeight(), null);
                break;
            case 2:
                canvas.drawBitmap(c2, (deckeys+ 4* c0.getWidth()+ 3*61)/2, getHeight()- bottombarC.getHeight(), null);
                break;
            case 3:
                canvas.drawBitmap(c3, (deckeys+ 4* c0.getWidth()+ 3*61)/2, getHeight()- bottombarC.getHeight(), null);
                break;
            case 4:
                canvas.drawBitmap(c4, (deckeys+ 4* c0.getWidth()+ 3*61)/2, getHeight()- bottombarC.getHeight(), null);
                break;
            case 5:
                canvas.drawBitmap(c5, (deckeys+ 4* c0.getWidth()+ 3*61)/2, getHeight()- bottombarC.getHeight(), null);
                break;
            case 6:
                canvas.drawBitmap(c6, (deckeys+ 4* c0.getWidth()+ 3*61)/2, getHeight()- bottombarC.getHeight(), null);
                break;
            case 7:
                canvas.drawBitmap(c7, (deckeys+ 4* c0.getWidth()+ 3*61)/2, getHeight()- bottombarC.getHeight(), null);
                break;
            case 8:
                canvas.drawBitmap(c8, (deckeys+ 4* c0.getWidth()+ 3*61)/2, getHeight()- bottombarC.getHeight(), null);
                break;
            case 9:
                canvas.drawBitmap(c9, (deckeys+ 4* c0.getWidth()+ 3*61)/2, getHeight()- bottombarC.getHeight(), null);
                break;



        }




        switch(nbr_coup_d)
        {
            case 0:
                canvas.drawBitmap(c0, (deckeys+ 3* c1.getWidth()+ 3*54)/2, getHeight()- bottombarC.getHeight(), null);
                break;

            case 1:
                canvas.drawBitmap(c1, (deckeys+ 3* c1.getWidth()+ 3*53)/2, getHeight()- bottombarC.getHeight(), null);
                break;

            case 2:
                canvas.drawBitmap(c2, (deckeys+ 3* c1.getWidth()+ 3*53)/2, getHeight()- bottombarC.getHeight(), null);
                break;

            case 3:
                canvas.drawBitmap(c3, (deckeys+ 3* c1.getWidth()+ 3*53)/2, getHeight()- bottombarC.getHeight(), null);
                break;

            case 4:
                canvas.drawBitmap(c4, (deckeys+ 3* c1.getWidth()+ 3*53)/2, getHeight()- bottombarC.getHeight(), null);
                break;

            case 5:
                canvas.drawBitmap(c5, (deckeys+ 3* c1.getWidth()+ 3*53)/2, getHeight()- bottombarC.getHeight(), null);
                break;

            case 6:
                canvas.drawBitmap(c6, (deckeys+ 3* c1.getWidth()+ 3*53)/2, getHeight()- bottombarC.getHeight(), null);
                break;

            case 7:
                canvas.drawBitmap(c7, (deckeys+ 3* c1.getWidth()+ 3*53)/2, getHeight()- bottombarC.getHeight(), null);
                break;

            case 8:
                canvas.drawBitmap(c8, (deckeys+ 3* c1.getWidth()+ 3*53)/2, getHeight()- bottombarC.getHeight(), null);
                break;

            case 9:
                canvas.drawBitmap(c9, (deckeys+ 3* c1.getWidth()+ 3*53)/2, getHeight()- bottombarC.getHeight(), null);
                break;


        }


    }
    // dessin du jeu (fond uni, en fonction du jeu gagne ou pas, dessin du plateau et du joueur, des cartes, compteur)
    private void dessin(Canvas canvas) {
        canvas.drawRGB(50,50,50);
        paintMap(canvas);

        paintBottomBar(canvas);
        if(gameover)
        {
            paintgameover(canvas);
        }
        if(win)
        {
            paintwin(canvas);
        }


    }



    // callback sur le cycle de vie de la surfaceview
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initparameters();

    }

    public void surfaceCreated(SurfaceHolder arg0) {
        Log.i("-> FCT <-", "surfaceCreated");
    }


    public void surfaceDestroyed(SurfaceHolder arg0) {
        Log.i("-> FCT <-", "surfaceDestroyed");
        in = false ;
    }

    // fonction permettant de recuperer les retours clavier
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        keyused = true;




        if(!gameover && !win){
            if (keyCode == KeyEvent.KEYCODE_0) {
                initparameters();
            }

            if (keyCode == KeyEvent.KEYCODE_1) {
                carteVIS[0][0]= true ;
                check_carte(0, 0);
            }
            if (keyCode == KeyEvent.KEYCODE_2) {
                carteVIS[0][1]= true ;
                check_carte(0, 1);
            }
            if (keyCode == KeyEvent.KEYCODE_3) {
                carteVIS[0][2]= true ;
                check_carte(0, 2);
            }
            if (keyCode == KeyEvent.KEYCODE_4) {
                carteVIS[0][3]= true ;
                check_carte(0, 3);
            }

            if (keyCode == KeyEvent.KEYCODE_5) {
                carteVIS[1][0]= true ;
                check_carte(1, 0);
            }
            if (keyCode == KeyEvent.KEYCODE_6) {
                carteVIS[1][1]= true ;
                check_carte(1, 1);
            }
            if (keyCode == KeyEvent.KEYCODE_7) {
                carteVIS[1][2]= true ;
                check_carte(1, 2);
            }
            if (keyCode == KeyEvent.KEYCODE_8) {
                carteVIS[1][3]= true ;
                check_carte(1, 3);
            }

            if (keyCode == KeyEvent.KEYCODE_9) {
                carteVIS[2][0]= true ;
                check_carte(2, 0);
            }
            if (keyCode == KeyEvent.KEYCODE_A) {
                carteVIS[2][1]= true ;
                check_carte(2, 1);
            }
            if (keyCode == KeyEvent.KEYCODE_B) {
                carteVIS[2][2]= true ;
                check_carte(2, 2);
            }
            if (keyCode == KeyEvent.KEYCODE_C) {
                carteVIS[2][3]= true ;
                check_carte(2, 3);
            }


            if(keyCode == KeyEvent.KEYCODE_D) {
                carteVIS[3][0]= true ;
                check_carte(3, 0);
            }
            if (keyCode == KeyEvent.KEYCODE_E) {
                carteVIS[3][1]= true ;
                check_carte(3, 1);
            }
            if (keyCode == KeyEvent.KEYCODE_F) {
                carteVIS[3][2]= true ;
                check_carte(3, 2);
            }
            if (keyCode == KeyEvent.KEYCODE_G) {
                carteVIS[3][3]= true ;
                check_carte(3, 3);
            }

            if(keyCode == KeyEvent.KEYCODE_H) {
                carteVIS[4][0]= true ;
                check_carte(4, 0);
            }
            if (keyCode == KeyEvent.KEYCODE_I) {
                carteVIS[4][1]= true ;
                check_carte(4, 1);
            }
            if (keyCode == KeyEvent.KEYCODE_J) {
                carteVIS[4][2]= true ;
                check_carte(4, 2);
            }
            if (keyCode == KeyEvent.KEYCODE_K) {
                carteVIS[4][3]= true ;
                check_carte(4, 3);
            }


        }
        else
        {
            if (keyCode == KeyEvent.KEYCODE_STAR) {
                initparameters();
                gameover= false ;
                affiche_time = final_time;
            }
        }

        return true;
    }
    // fonction permettant de recuperer les evenements tactiles
    public boolean onTouchEvent (MotionEvent event) {


        if(!gameover && !win)
        {


            if (event.getY() >= mapTopAnchor+ 0*mapTileSize && event.getY() <= mapTopAnchor+ 0*mapTileSize + 46  ) {




                if (event.getX() >  mapLeftAnchor+ 0*mapTileSize && event.getX() <  mapLeftAnchor+ 0*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_1, null);
                }

                if (event.getX() >  mapLeftAnchor+ 1*mapTileSize && event.getX() <  mapLeftAnchor+ 1*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_2, null);
                }
                if (event.getX() >  mapLeftAnchor+ 2*mapTileSize && event.getX() <  mapLeftAnchor+ 2*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_3, null);
                }
                if (event.getX() >  mapLeftAnchor+ 3*mapTileSize && event.getX() <  mapLeftAnchor+ 3*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_4, null);
                }

            }



            if (event.getY() >= mapTopAnchor+ 1*mapTileSize && event.getY() <= mapTopAnchor+ 1*mapTileSize + 46  ) {




                if (event.getX() >  mapLeftAnchor+ 0*mapTileSize && event.getX() <  mapLeftAnchor+ 0*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_5, null);
                }

                if (event.getX() >  mapLeftAnchor+ 1*mapTileSize && event.getX() <  mapLeftAnchor+ 1*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_6, null);
                }
                if (event.getX() >  mapLeftAnchor+ 2*mapTileSize && event.getX() <  mapLeftAnchor+ 2*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_7, null);
                }
                if (event.getX() >  mapLeftAnchor+ 3*mapTileSize && event.getX() <  mapLeftAnchor+ 3*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_8, null);
                }




            }


            if (event.getY() >= mapTopAnchor+ 2*mapTileSize && event.getY() <= mapTopAnchor+ 2*mapTileSize + 46  ) {




                if (event.getX() >  mapLeftAnchor+ 0*mapTileSize && event.getX() <  mapLeftAnchor+ 0*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_9, null);
                }

                if (event.getX() >  mapLeftAnchor+ 1*mapTileSize && event.getX() <  mapLeftAnchor+ 1*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_A, null);
                }
                if (event.getX() >  mapLeftAnchor+ 2*mapTileSize && event.getX() <  mapLeftAnchor+ 2*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_B, null);
                }
                if (event.getX() >  mapLeftAnchor+ 3*mapTileSize && event.getX() <  mapLeftAnchor+ 3*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_C, null);
                }




            }



            if (event.getY() >= mapTopAnchor+ 3*mapTileSize && event.getY() <= mapTopAnchor+ 3*mapTileSize + 46  ) {




                if (event.getX() >  mapLeftAnchor+ 0*mapTileSize && event.getX() <  mapLeftAnchor+ 0*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_D, null);
                }

                if (event.getX() >  mapLeftAnchor+ 1*mapTileSize && event.getX() <  mapLeftAnchor+ 1*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_E, null);
                }
                if (event.getX() >  mapLeftAnchor+ 2*mapTileSize && event.getX() <  mapLeftAnchor+ 2*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_F, null);
                }
                if (event.getX() >  mapLeftAnchor+ 3*mapTileSize && event.getX() <  mapLeftAnchor+ 3*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_G, null);
                }




            }


            if (event.getY() >= mapTopAnchor+ 4*mapTileSize && event.getY() <= mapTopAnchor+ 4*mapTileSize + 46  ) {




                if (event.getX() >  mapLeftAnchor+ 0*mapTileSize && event.getX() <  mapLeftAnchor+ 0*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_H, null);
                }

                if (event.getX() >  mapLeftAnchor+ 1*mapTileSize && event.getX() <  mapLeftAnchor+ 1*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_I, null);
                }
                if (event.getX() >  mapLeftAnchor+ 2*mapTileSize && event.getX() <  mapLeftAnchor+ 2*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_J, null);
                }
                if (event.getX() >  mapLeftAnchor+ 3*mapTileSize && event.getX() <  mapLeftAnchor+ 3*mapTileSize + 46 ) {
                    onKeyDown(KeyEvent.KEYCODE_K, null);
                }




            }
        }

        else
        {
            //deckeys+ 4* keydown.getWidth()+ 4*5

            if (event.getY() >= (getHeight()- bottombarC.getHeight())/2  ) {

                if (event.getX() >  deckeys+ 4* keydown.getWidth()+ 4*5 ) {
                    onKeyDown(KeyEvent.KEYCODE_STAR, null);
                }
            }
        }
        return super.onTouchEvent(event);
    }



















}
