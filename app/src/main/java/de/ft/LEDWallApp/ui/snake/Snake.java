package de.ft.LEDWallApp.ui.snake;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.ft.LEDWallApp.Connection;
import de.ft.LEDWallApp.R;

public class Snake extends Fragment {

    private SnakeViewModel mViewModel;

    public static Snake newInstance() {
        return new Snake();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.snake_fragment, container, false);
        Button hoch = view.findViewById(R.id.hoch);
        Button runter = view.findViewById(R.id.runter);
        Button links = view.findViewById(R.id.links);
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




        return view;    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SnakeViewModel.class);
        // TODO: Use the ViewModel
    }

}