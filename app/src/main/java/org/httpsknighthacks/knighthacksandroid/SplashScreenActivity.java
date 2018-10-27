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

    private static final int ALPHA_ANIMATION_DURATION = 1000;
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
    private static AnimatorSet rocketAnimatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        initComponents();
        setUpAnimations();
        playAnimations();
    }

    private void initComponents() {
        initViews();
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(ALPHA_ANIMATION_DURATION);
        alphaAnimation.setInterpolator(new LinearInterpolator());
    }

    private void initViews() {
        rocket = findViewById(R.id.splash_screen_rocket);
        flames = findViewById(R.id.splash_screen_flames);
        spaceCloud = findViewById(R.id.splash_screen_big_cloud);
        miniCloud = findViewById(R.id.splash_screen_mini_cloud);
        microCloud = findViewById(R.id.splash_screen_micro_cloud);
        splashText = findViewById(R.id.splash_screen_text);
        blueStar = findViewById(R.id.splash_screen_blue_star);
        redStar = findViewById(R.id.splash_screen_red_star);
    }

    private void setUpAnimations() {
        setUpRocketAnimation();
        setUpStarAnimations();
        setUpCloudAnimations();
    }

    private void setUpRocketAnimation() {
        // Animate flames to create the illusion of movement
        // The second argument float value scales the image by that much. After trying different
        // values, I've decided this one produces the best result.
        PropertyValuesHolder scalePropertyValuesHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.25f);
        ObjectAnimator scaleFlamesAnimator = ObjectAnimator.ofPropertyValuesHolder(flames, scalePropertyValuesHolder);
        scaleFlamesAnimator.setRepeatCount(30);
        scaleFlamesAnimator.setRepeatMode(ValueAnimator.REVERSE);
        scaleFlamesAnimator.setDuration(450);

        // Animate rocket acceleration
        // Since this animation is a sequence of different translations, the keyframe arguments
        // denote the order and time intervals each translation should run on.
        Keyframe k0 = Keyframe.ofFloat(0f, 0);
        Keyframe k1 = Keyframe.ofFloat(.2f, 200f);
        Keyframe k2 = Keyframe.ofFloat(.5f, -2000f);
        PropertyValuesHolder translatePropertyValuesHolder = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_Y, k0, k1, k2);
        ObjectAnimator translateFlamesAnimator = ObjectAnimator.ofPropertyValuesHolder(flames, translatePropertyValuesHolder);
        ObjectAnimator translateRocketAnimator = ObjectAnimator.ofPropertyValuesHolder(rocket, translatePropertyValuesHolder);
        translateFlamesAnimator.setDuration(ROCKET_ACCELERATION_ANIMATION_DURATION);
        translateRocketAnimator.setDuration(ROCKET_ACCELERATION_ANIMATION_DURATION);
        translateRocketAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                splashText.setVisibility(View.INVISIBLE);
                isEndOfAnimations = true;

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

        rocketAnimatorSet = new AnimatorSet();
        rocketAnimatorSet.play(scaleFlamesAnimator).before(translateFlamesAnimator);
        rocketAnimatorSet.play(translateFlamesAnimator).with(translateRocketAnimator);
    }

    private void setUpStarAnimations() {
        translateBlueStar = AnimationUtils.loadAnimation(this, R.anim.star_translation);
        translateRedStar = AnimationUtils.loadAnimation(this, R.anim.star_translation);
        translateBlueStar.setRepeatCount(TRANSLATION_REPEAT_COUNT);
        translateBlueStar.setStartOffset(4000);
        translateRedStar.setRepeatCount(TRANSLATION_REPEAT_COUNT);
    }

    private void setUpCloudAnimations() {
        translateMicroCloud = AnimationUtils.loadAnimation(this, R.anim.cloud_translation);
        translateMiniCloud = AnimationUtils.loadAnimation(this, R.anim.cloud_translation);
        translateBigCloud = AnimationUtils.loadAnimation(this, R.anim.cloud_translation);
        translateBigCloud.setStartOffset(4000);
        translateBigCloud.setDuration(2000);
        translateBigCloud.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if(!isCloudAnimating) {
                    microCloud.startAnimation(translateMicroCloud);
                }

                spaceCloud.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        translateMicroCloud.setStartOffset(1000);
        translateMicroCloud.setDuration(14000);
        translateMicroCloud.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isCloudAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isCloudAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        translateMiniCloud.setDuration(8000);
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
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private void playAnimations() {
        blueStar.startAnimation(translateBlueStar);
        redStar.startAnimation(translateRedStar);
        miniCloud.startAnimation(translateMiniCloud);
        rocketAnimatorSet.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        isPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isEndOfAnimations) {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        isPaused = false;
    }
}
