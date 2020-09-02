package de.ft.LEDWallApp.ui.light;

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

public class Licht extends Fragment {

    private LichtViewModel mViewModel;

    public static Licht newInstance() {
        return new Licht();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


      View view =  inflater.inflate(R.layout.licht_fragment, container, false);
        Button lichtschalter = view.findViewById(R.id.lightswitch);
        lichtschalter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test","blub");
                Connection.send("LichtSchalter");
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LichtViewModel.class);

        // TODO: Use the ViewModel
    }

}