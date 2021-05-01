package com.samblancat.pong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Main extends AppCompatActivity  {
    Context mContext;
    private mydraw gameview;
    public double x1,x2,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=this;

        gameview = new mydraw(this);
        setContentView(gameview);

  //      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        glob.cx = (glob.maxx / 2);
        glob.cy = (glob.maxy / 2);

        glob.car = new char[5];

        glob.sharedPref = getBaseContext().getSharedPreferences("POSPREFS", MODE_PRIVATE);
        //Retrouve dernière recherche
        glob.car[0] = (char)glob.sharedPref.getInt("car0", 'A');
        glob.car[1] = (char)glob.sharedPref.getInt("car1", '*');
        glob.car[2] = (char)glob.sharedPref.getInt("car2", '*');
        glob.car[3] = (char)glob.sharedPref.getInt("car3", '*');
    }

   @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.d("Proc", "onUSERLeave");
    //    finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Proc", "onPause");
      //   finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ASSURE FIN DU PROCESS !!!
        Log.d("Proc", "killPROCESS");
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //Detection du swipe sur gauche ou autre .... pour finish ?
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = ev.getX();
                y1 = ev.getY();
                x2=x1;
                y2=y1;
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                x2 = ev.getX();
                y2 = ev.getY();
                //test si FIN
                if ( (Math.abs(x1-x2)>180) && (Math.abs(y1-y2)<50) ) {
                    finish();
                }

                //Test appui 'OK' en bas écran
                if ((y1>260)&&(y2>260)&&(x1>glob.cx-40)&&(x2<glob.cx+40)) {
                    Toast.makeText( mContext, "Recherche ...", Toast.LENGTH_SHORT).show();
                    findmots();
                    return false;   //super.dispatchTouchEvent(ev);
                }
                gameview.updateData(x1, y1, x2, y2);
                break;
        }

        // Dessine Mot à chercher & test si change ?
 //       Log.d("x1x2y1y2", String.valueOf(x1)+" "+String.valueOf(y1)+" "+String.valueOf(x2)+" "+String.valueOf(y2));
 //       gameview.updateData(x1, y1, x2, y2);
        return false;   //super.dispatchTouchEvent(ev);
    }


    private void findmots() {
        int nn, size, motrouv=0;

        //Lit le fichier de la Première Lettre
        String fich="let_"+(glob.car[0])+".txt";
        String tContents = "";

        try {
            InputStream stream = mContext.getAssets().open(fich);
            size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) { }

        String[] separ = tContents.split("\r\n");
        size = separ.length;
        glob.wordsok = new ArrayList<String>();

        for (nn=0; nn<size-1; nn++) {       // mo = 'AA' 'ABACAB' ...
            String mo=separ[nn];

            //Extrait lettres 2,3 & 4 du mot listes
            char mo1= mo.charAt(0);
            char mo2= mo.charAt(1);
            char mo3 = ' ';
            if (mo.length() > 2) mo3=mo.charAt(2);
            char mo4 = ' ';
            if (mo.length() > 3) mo4=mo.charAt(3);

            //Deuxième lettre
            if ( (glob.car[1] == mo2 )||(glob.car[1]=='*') ) {
                //Troisième lettre
                if ( (glob.car[2]==mo3)||(glob.car[2]=='*') ) {
                    //Quatrième lettre
                    if ( (glob.car[3]==mo4)||(glob.car[3]=='*') ) {
                        glob.wordsok.add(mo);
                        motrouv++;
                    }
                }
            }
        }

        if (motrouv<1)
            Toast.makeText( mContext, "Aucun mot trouvé !", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText( mContext, String.format("%d mots !", motrouv), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Main.this, listefill.class);
            startActivity(intent);
        }
    }
}