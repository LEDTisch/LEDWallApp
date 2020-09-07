package de.ft.LEDWallApp.ui.tetris;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.ft.LEDWallApp.Connection;
import de.ft.LEDWallApp.R;

public class Tetris extends Fragment {

    private TetrisViewModel mViewModel;

    public static Tetris newInstance() {
        return new Tetris();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.tetris_fragment, container, false);

        Button rechts= view.findViewById(R.id.rechts);
        Button links= view.findViewById(R.id.links);
        Button drehen= view.findViewById(R.id.drehen);
        Button runter= view.findViewById(R.id.runter);
        Button neuesspiel= view.findViewById(R.id.neuesspiel);

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
        drehen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.send("t");
            }
        });
        runter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.send("d");
            }
        });
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

}