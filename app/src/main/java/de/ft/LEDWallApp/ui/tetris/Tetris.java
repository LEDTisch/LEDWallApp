package de.ft.LEDWallApp.ui.tetris;

import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.ft.LEDWallApp.Connection;
import de.ft.LEDWallApp.R;

public class Tetris extends Fragment implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private TetrisViewModel mViewModel;
    private GestureDetectorCompat mGestureDetector;
    public static Tetris newInstance() {
        return new Tetris();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.tetris_fragment, container, false);


        //Button drehen= view.findViewById(R.id.drehen);
      //  Button runter= view.findViewById(R.id.runter);
        Button neuesspiel= view.findViewById(R.id.neuesspiel);
        view.setOnTouchListener(this);

        mGestureDetector = new GestureDetectorCompat(view.getContext(),this);

    /*    drehen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.send("t");
            }
        });
        runter.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        Connection.send("d");
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        Connection.send("?");
                        // RELEASED
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });*/

        neuesspiel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.send("n");
            }
        });






        return view;    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TetrisViewModel.class);
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
        if(Math.abs(velocityX)>=Math.abs(velocityY)){
            if(velocityX>=0){
                Connection.send("r");
            }else{
                Connection.send("l");
            }
            System.out.println("Yfling");
        }else{
            if(velocityY>=0){
                Connection.send("d");
            }else{
            }
            System.out.println("Xfling");
        }
        return false;
    }
}