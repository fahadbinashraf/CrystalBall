package com.example.fahadbinashraf.crystalball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MyActivity extends Activity {

    private static final String TAG = MyActivity.class.getSimpleName();
    private CrystalBall mCrystalBall = new CrystalBall();
    private TextView mAnswerLable;
    private Button mGetAnswerButton;
    private ImageView mCrystalBallImage;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //Assigning views form the main xml file
        mAnswerLable = (TextView) findViewById(R.id.tvAnswer);
        mGetAnswerButton = (Button) findViewById(R.id.bGetAnswer);
        mCrystalBallImage = (ImageView) findViewById(R.id.imageView);

        mGetAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleNewText();

            }
        });
        ///////To use it on shaking android device
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                handleNewText();
            }
        });

        Log.d(TAG, "A log can be stored like this");
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
    }

    private void handleNewText() {
        mAnswerLable.setText(mCrystalBall.getAnAnswer());
        animateCrystalBall();
        animateText();
        playSound();
    }

    private void animateText() {
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
        fadeInAnimation.setDuration(1500);
        fadeInAnimation.setFillAfter(true);
        mAnswerLable.setAnimation(fadeInAnimation);

    }

    public void animateCrystalBall() {
        mCrystalBallImage.setImageResource(R.drawable.ball_animation);
        AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalBallImage.getDrawable();
        if (ballAnimation.isRunning()) {
            ballAnimation.stop();
        }
        ballAnimation.start();
    }

    private void playSound() {

        MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });

    }
}
