package de.ft.LEDWallApp.ui.PacMan;

import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import de.ft.LEDWallApp.Connection;
import de.ft.LEDWallApp.MainActivity;
import de.ft.LEDWallApp.R;

public class PacMan extends Fragment  implements View.OnTouchListener, GestureDetector.OnGestureListener, SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private PacManViewModel mViewModel;
    private GestureDetectorCompat mGestureDetector;

    public static PacMan newInstance() {
        return new PacMan();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pacman_fragment, container, false);
        view.setOnTouchListener(this);

        mGestureDetector = new GestureDetectorCompat(view.getContext(),this);

        senSensorManager = (SensorManager) view.getContext().getSystemService(view.getContext().SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PacManViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Connection.send("t");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float diff=Math.abs(e2.getX()-e1.getX());
        System.out.print(diff);
        if(Math.abs(velocityX)>=Math.abs(velocityY)){
            if(velocityX>=0){
                Connection.send("r");
            }else{
                Connection.send("l");
            }
            System.out.println("Xfling: "+ velocityX);
        }else{
            if(velocityY>=0){
                Connection.send("d");
                System.out.println("down");
            }else{
                Connection.send("u");
            }
            System.out.println("Yfling: "+velocityY);
        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}