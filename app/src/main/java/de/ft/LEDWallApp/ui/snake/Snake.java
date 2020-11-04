package de.ft.LEDWallApp.ui.snake;

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

public class Snake extends Fragment implements View.OnTouchListener {

    private SnakeViewModel mViewModel;
    private GestureDetectorCompat mGestureDetector;

    public static Snake newInstance() {
        return new Snake();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.snake_fragment, container, false);

        view.setOnTouchListener(this);

        mGestureDetector = new GestureDetectorCompat(view.getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
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
                int multi=1;
                float diff=Math.abs(e2.getX()-e1.getX());
                if(Math.abs(velocityX)>=Math.abs(velocityY)){
                    if(diff>500){
                        multi=2;
                    }
                    if(diff>600 && velocityX>10000){
                        multi=3;
                    }
                    if(velocityX>=0){
                        for(int i=0;i<multi;i++) {
                            Connection.send("r");
                        }
                    }else{
                        for(int i=0;i<multi;i++) {
                            Connection.send("l");
                        }
                    }
                    System.out.println("Xfling: "+ velocityX);
                }else{
                    if(velocityY>=20){
                        Connection.send("d");
                    }else if(velocityY<=-20){
                        Connection.send("h");
                    }
                    System.out.println("Yfling: "+velocityY);
                }
                return false;
            }
        });
        /*
        Button hoch = view.findViewById(R.id.drehen);
        Button runter = view.findViewById(R.id.links);
        Button links = view.findViewById(R.id.runter);
        Button rechts = view.findViewById(R.id.rechts);

        hoch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.send("h");
            }
        });
        runter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.send("d");
            }
        });
        rechts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.send("r");
            }
        });
        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.send("l");
            }
        });


         */



        return view;    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SnakeViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }
}