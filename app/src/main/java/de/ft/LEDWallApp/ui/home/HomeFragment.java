package de.ft.LEDWallApp.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;

import de.ft.LEDWallApp.R;

public class HomeFragment extends Fragment implements SurfaceHolder.Callback{

    private HomeViewModel homeViewModel;
    Camera camera;

    Camera.PictureCallback jpegCallback;

    SurfaceView previewBox;
    SurfaceHolder surfaceHolder;

    final int CAMERA_REQUEST_CODE = 1;

    Button capturebutton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        previewBox = (SurfaceView)root.findViewById(R.id.previewBox);
        surfaceHolder = previewBox.getHolder();
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }else{
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(surfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        capturebutton=root.findViewById(R.id.Capture);

        capturebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
                System.out.println("Capture Image pressed");
            }
        });

        jpegCallback = new Camera.PictureCallback(){

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                System.out.println("wenn du das siest freu dich");
                Bitmap decodeBitmap= BitmapFactory.decodeByteArray(data, 0, data.length);

            }
        };



        // open the camera

        return root;

    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        camera = Camera.open();
        Camera.Parameters parameters;
        parameters = camera.getParameters();

        camera.setDisplayOrientation(90);
        parameters.setPreviewFrameRate(30);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    private void captureImage(){
        camera.takePicture(null, null, jpegCallback);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    surfaceHolder.addCallback(this);
                    surfaceHolder.setType(surfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                }else{
                    Toast.makeText(getContext(), "Please provide the permission", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}