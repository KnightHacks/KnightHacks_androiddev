package org.httpsknighthacks.knighthacksandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    boolean isPaused;
    boolean isEndOfanimations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        isPaused = false;
        isEndOfanimations = false;
        ImageView rocket = findViewById(R.id.rocket);
        ImageView flames = findViewById(R.id.flames);
        final ImageView spaceCloud = findViewById(R.id.clouds);
        final ImageView miniCloud = findViewById(R.id.minicloud);
        final ImageView microCloud = findViewById(R.id.microcloud);
        final TextView splashText = findViewById(R.id.splashText);
        final ImageView blueStar = findViewById(R.id.blue_star);
        final ImageView redStar = findViewById(R.id.red_star);

        Animation translateBlueStar = AnimationUtils.loadAnimation(this, R.anim.star_translation);
        Animation translateRedStar = AnimationUtils.loadAnimation(this, R.anim.star_translation);

        final Animation translateMicroCloud = AnimationUtils.loadAnimation(this, R.anim.cloud_translation);
        final Animation translateMiniCloud = AnimationUtils.loadAnimation(this, R.anim.cloud_translation);
        final Animation translateBigCloud = AnimationUtils.loadAnimation(this, R.anim.cloud_translation);

        // Alpha animation will fade out the views before transitioning to new activity
        final Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setInterpolator(new LinearInterpolator());

        // Set up rocket acceleration algorithm
        Keyframe k0 = Keyframe.ofFloat(0f, 0);
        Keyframe k1 = Keyframe.ofFloat(.2f, 200f);
        Keyframe k2 = Keyframe.ofFloat(.5f, -2000f);
        PropertyValuesHolder translatePropertyValuesholder = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_Y, k0, k1, k2);
        ObjectAnimator translateFlamesAnimator = ObjectAnimator.ofPropertyValuesHolder(flames, translatePropertyValuesholder);
        ObjectAnimator translateRocketAnimator = ObjectAnimator.ofPropertyValuesHolder(rocket, translatePropertyValuesholder);
        translateFlamesAnimator.setDuration(1000);
        translateRocketAnimator.setDuration(1000);

        // Trigger transition to main activity once the rocket leaves the screen
        translateRocketAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                splashText.setVisibility(View.INVISIBLE);
                isEndOfanimations = true;

                if(!isPaused) {
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

                splashText.startAnimation(alphaAnimation);
                blueStar.startAnimation(alphaAnimation);
                redStar.startAnimation(alphaAnimation);
                spaceCloud.startAnimation(alphaAnimation);
                miniCloud.startAnimation(alphaAnimation);
                microCloud.startAnimation(alphaAnimation);
            }
        });

        // Animate flames to create the illusion of movement
        PropertyValuesHolder scalePropertyValuesHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, (float) 0.8);
        ObjectAnimator scaleFlamesAnimator = ObjectAnimator.ofPropertyValuesHolder(flames, scalePropertyValuesHolder);
        scaleFlamesAnimator.setRepeatCount(30);
        scaleFlamesAnimator.setRepeatMode(ValueAnimator.REVERSE);
        scaleFlamesAnimator.setDuration(450);

        // Set up the order at which the rocket animations will play
        AnimatorSet s = new AnimatorSet();
        s.play(scaleFlamesAnimator).before(translateFlamesAnimator);
        s.play(translateFlamesAnimator).with(translateRocketAnimator);

        translateBlueStar.setRepeatCount(3);
        translateBlueStar.setStartOffset(4000);
        blueStar.startAnimation(translateBlueStar);

        translateRedStar.setRepeatCount(3);
        redStar.startAnimation(translateRedStar);

        final boolean[] isAnimating = {false};

        translateBigCloud.setDuration(2000);
        translateBigCloud.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
               if(!isAnimating[0])
                    microCloud.startAnimation(translateMicroCloud);

                spaceCloud.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        translateMicroCloud.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimating[0] = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimating[0] = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        translateMicroCloud.setDuration(14000);
        translateMicroCloud.setStartOffset(1000);

        translateMiniCloud.setDuration(8000);
        translateBigCloud.setStartOffset(4000);
        translateMiniCloud.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                spaceCloud.startAnimation(translateBigCloud);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                miniCloud.clearAnimation();
                miniCloud.startAnimation(translateMiniCloud);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        miniCloud.startAnimation(translateMiniCloud);

        s.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isEndOfanimations)
        {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        isPaused = false;
    }

}
