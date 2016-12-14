package lucis.classy;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main_anim);


        ImageView logo = (ImageView) findViewById(R.id.logoAnim);

        Animation moveUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);

        moveUp.setStartOffset(1000);

        logo.startAnimation(moveUp);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(homeIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }


    //    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        Intent intent = new Intent(this, LoginActivity.class);
//        if(hasFocus) {
//            startActivity(intent);
//            finish();
//        }
//    }

//        @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        ImageView startPoint = (ImageView) findViewById(R.id.myanimation);
//        AnimationDrawable frameAnimation = ((AnimationDrawable) startPoint.getDrawable());
//        if(hasFocus){
//            frameAnimation.start();
//        }
//    }
}