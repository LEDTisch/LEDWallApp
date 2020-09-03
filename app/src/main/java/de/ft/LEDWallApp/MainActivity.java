package de.ft.LEDWallApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceManager;

import java.io.IOException;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public MainActivity instace = this;
public static Button connectbutton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);




        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.action_settings)
                {
                    Intent intent = new Intent(instace, SettingsActivity.class);
                    startActivity(intent);
                }

                return false;
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        connectbutton = navigationView.getHeaderView(0).findViewById(R.id.connect);

        connectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectbutton.getText().toString().contentEquals("Verbinden")) {
                    final String ip  =  sharedPreferences.getString("ipadressenfeldkey","");
                    final int port = Integer.parseInt(sharedPreferences.getString("portkey", "0"));
                    try {
                        if (port > 0) {
                            Connection.connect(ip, port,instace);
                            Connection.send("ClientConnected");
                            connectbutton.setEnabled(false);

                        }
                    } catch (Throwable e) {
                        connectbutton.setText("Verbinden");
                        connectbutton.setEnabled(true);
                    }

                }else{

                    Connection.end();

                    connectbutton.setText("Verbinden");
                    connectbutton.setEnabled(true);
                }
            }
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.licht)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.d("Switched to: ", (String) destination.getLabel());
                Connection.send("switchTo:" + (String) destination.getLabel() + "#");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public  void setConnectbuttontext(final String text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectbutton.setText(text);
                connectbutton.setEnabled(true);
            }
        });


    }
}