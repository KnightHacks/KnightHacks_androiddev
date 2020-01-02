package org.httpsknighthacks.knighthacksandroid;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreenActivity extends AppCompatActivity {

/*    private static final int ALPHA_ANIMATION_DURATION = 1000;
    private static final int ROCKET_ACCELERATION_ANIMATION_DURATION = 1000;
    private static final int TRANSLATION_REPEAT_COUNT = 3;

    private boolean isPaused;
    private boolean isEndOfAnimations;
    private boolean isCloudAnimating;
    private ImageView rocket;
    private ImageView flames;
    private ImageView spaceCloud;
    private ImageView miniCloud;
    private ImageView microCloud;
    private TextView splashText;
    private ImageView blueStar;
    private ImageView redStar;
    private static Animation alphaAnimation;
    private static Animation translateBlueStar;
    private static Animation translateRedStar;
    private static Animation translateMicroCloud;
    private static Animation translateMiniCloud;
    private static Animation translateBigCloud;
    private static AnimatorSet rocketAnimatorSet;*/

    LottieAnimationView anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        anim = findViewById(R.id.animation_view);
        // TODO : ask if repeat is needed.
        anim.setRepeatCount(1);

        final Intent mainScreen = new Intent(this, MainActivity.class);

        anim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("Splash Screen", "onAnimationStart():  lottieAnimationView.isAnimating() = " + anim.isAnimating());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("Splash Screen", "onAnimationEnd():  lottieAnimationView.isAnimating() = " + anim.isAnimating());

                startActivity(mainScreen);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("Splash Screen", "onAnimationCancel():  lottieAnimationView.isAnimating() = " + anim.isAnimating());
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d("Splash Screen", "onAnimationRepeat():  lottieAnimationView.isAnimating() = " + anim.isAnimating());
            }
        });
    }
}
