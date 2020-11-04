package de.ft.LEDWallApp.ui.flappybird;

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

public class FlappyBird extends Fragment {

    private FlappyBirdViewModel mViewModel;

    public static FlappyBird newInstance() {
        return new FlappyBird();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.flappy_bird_fragment, container, false);
        Button jump = inflate.findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection.send("j");
            }
        });

        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FlappyBirdViewModel.class);
        // TODO: Use the ViewModel
    }

}