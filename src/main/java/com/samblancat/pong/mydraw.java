package com.samblancat.pong;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class mydraw extends View {
    public Paint paint;
    public Double ddx, ddy;
    public int cursx;
    public int incred=0;
    public CountDownTimer cTimer = null;
    public mydraw(Context context) {
        super(context);
        init();
    }

    private void init() {

        this.setDrawingCacheEnabled(true);

        cursx = 45;

        ddx=0.0;
        ddy=0.0;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        this.buildDrawingCache();
     //   Bitmap bitmap = this.getDrawingCache(true);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        for (int x0=0; x0<4; x0++) {
            paint.setColor(Color.LTGRAY);
            canvas.drawRect(25 + (x0 * 70), (float) (glob.cy - 60), 85 + (x0 * 70), (float) (glob.cy + 40), paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(74);
            canvas.drawText(Character.toString(glob.car[x0]), 34 + (x0 * 70), Math.round(glob.cy)+20, paint);
        }

        //Bouton vert en bas
        paint.setColor(Color.GREEN);
        canvas.drawRect(0 ,(float) (265), glob.maxx, glob.maxy, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);
        canvas.drawText("CHERCHER", (float)(glob.cx- 60), 292, paint);
    }


    public void updateData(double x1, double y1, double x2, double y2) {
       ddx=x2-x1;
       ddy=y1-y2;
       int dcx= (int) ((x1+x2)/2);

       if (incred==0) {
           for (int x0=0; x0<4; x0++) {
               int lgau=25 + (x0 * 70);
               int ldroit=85 + (x0 * 70);
               if ( (dcx > lgau-5) && (dcx < ldroit+5) && (Math.abs((y1-y2))>75) ) {

                   // CHAR + 1
                   if (ddy > 45) {
                       glob.car[x0]++;
                       incred = 1;
                       cTimer = new CountDownTimer(100, 50) {
                           @Override
                           public void onTick(long millisUntilFinished) {
                           }
                           public void onFinish() {
                               incred = 0;
                           }
                       }.start();

                       //Traitement spécial pour première lettre de gauche
                       if (x0==0) {
                           if (glob.car[x0] > 'Z') glob.car[x0] = 'A';
                       }
                       //Sinon les 3 autres '*' et ' '
                       else {
                           if (glob.car[x0] == '+') glob.car[x0] = 'A';
                           if (glob.car[x0] == 33) glob.car[x0] = '*';
                           if (glob.car[x0] > 'Z') glob.car[x0] = ' ';
                       }
                   }

                   // CHAR - 1
                   if (ddy < -45) {
                       glob.car[x0]--;
                       incred = 1;
                       cTimer = new CountDownTimer(100, 50) {
                           @Override
                           public void onTick(long millisUntilFinished) {
                           }
                           public void onFinish() {
                               incred = 0;
                           }
                       }.start();

                       //Traitement spécial pour première lettre de gauche
                       if (x0==0) {
                           if (glob.car[x0] < 'A') glob.car[x0] = 'Z';
                       }
                       //Sinon les 3 autres '*' et ' '
                       else {
                           if (glob.car[x0] == '@') glob.car[x0] = '*';
                           if (glob.car[x0] < ' ') glob.car[x0] = 'Z';
                           if (glob.car[x0] < '*') glob.car[x0] = ' ';
                       }
                   }
               }
           }

           SharedPreferences.Editor editor = glob.sharedPref.edit();
           editor.putInt("car0", glob.car[0]);
           editor.putInt("car1", glob.car[1]);
           editor.putInt("car2", glob.car[2]);
           editor.putInt("car3", glob.car[3]);
           editor.apply();
        }
        invalidate();
    }

}
