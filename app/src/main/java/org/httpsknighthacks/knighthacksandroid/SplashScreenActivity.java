package org.httpsknighthacks.knighthacksandroid;

import android.animation.Animator;
import android.animation.AnimatorInflater;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView rocket = findViewById(R.id.rocket);
        ImageView flames = findViewById(R.id.flames);
        final ImageView spaceCloud = findViewById(R.id.clouds);
        final TextView splashText = findViewById(R.id.splashText);
        final ImageView blueStar = findViewById(R.id.blue_star);
        final ImageView redStar = findViewById(R.id.red_star);
        final AnimatorSet cloudSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.translate_cloud);
        cloudSet.setTarget(spaceCloud);
        cloudSet.setDuration(2000);

        Animation translateBlueStar = AnimationUtils.loadAnimation(this, R.anim.star_translation);
        Animation translateRedStar = AnimationUtils.loadAnimation(this, R.anim.star_translation);

        // Alpha animation will fade out the views before transitioning to new activity
        final Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cloudSet.end();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

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
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

                splashText.startAnimation(alphaAnimation);
                blueStar.startAnimation(alphaAnimation);
                redStar.startAnimation(alphaAnimation);
                spaceCloud.startAnimation(alphaAnimation);
            }
        });

        // Animate flames to create the illusion of movement
        PropertyValuesHolder scalePropertyValuesHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, (float) 0.8);
        ObjectAnimator scaleFlamesAnimator = ObjectAnimator.ofPropertyValuesHolder(flames, scalePropertyValuesHolder);
        scaleFlamesAnimator.setRepeatCount(20);
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

        s.start();
        cloudSet.start();
    }

}
