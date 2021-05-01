package com.samblancat.pong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class starter extends AppCompatActivity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=this;

        setContentView(R.layout.starter);

        Animation animFade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final ImageView iv = findViewById(R.id.startimg);
        final ImageView iv2 = findViewById(R.id.blackimg);

        iv.setVisibility(View.VISIBLE);
        iv2.setVisibility(View.GONE);

        animFade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                // The animation has ended, transition to the Main Menu screen
                Animation animFadout = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
                animFadout.reset();
                iv.clearAnimation();
                iv.startAnimation(animFadout);
            }
            public void onAnimationRepeat(Animation animation) {
            }
            public void onAnimationStart(Animation animation) {
            }
        });


        animFade.reset();
        iv.clearAnimation();
        iv.startAnimation(animFade);

        //Lance le Countdown de 10sec minimum avant lancement main...
        new CountDownTimer(2500, 500) {
            public void onTick(long millisUntilFinished) {
            }
            //Fin du compte-a-rebours
            public void onFinish() {
                //Lance le MainActivity
                Intent mainI = new Intent(starter.this, Main.class);
                startActivity(mainI);
                finish();
            }
        }.start();
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }
}
