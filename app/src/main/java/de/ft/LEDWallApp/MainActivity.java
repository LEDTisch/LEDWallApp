package de.ft.LEDWallApp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public MainActivity instace = this;
    public static Button connectbutton;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(Color.RED);
        toolbar.setSubtitle("Nicht Verbunden");
        getWindow().setStatusBarColor(Color.RED);


        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_settings) {
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

                    final String ip = sharedPreferences.getString("ipadressenfeldkey", "");
                    final int port = Integer.parseInt(sharedPreferences.getString("portkey", "0"));
                    try {
                        if (port > 0) {
                            Connection.connect(ip, port, instace);
                            Connection.send("ClientConnected");
                            connectbutton.setEnabled(false);

                        }
                    } catch (Throwable e) {
                        connectbutton.setText("Verbinden");
                        setConnected(false);
                        connectbutton.setEnabled(true);
                    }

                } else {

                    Connection.end();

                    connectbutton.setText("Verbinden");
                    setConnected(false);
                    connectbutton.setEnabled(true);
                }
            }
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.licht,R.id.snake)
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

    public void setConnectbuttontext(final String text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectbutton.setText(text);
                connectbutton.setEnabled(true);
            }
        });


    }


    public void setConnected(final boolean connected) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (connected) {
                    toolbar.setBackgroundResource(R.color.colorPrimary);
                    toolbar.setSubtitle("Verbunden");
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

                } else {
                    toolbar.setBackgroundColor(Color.RED);
                    toolbar.setSubtitle("Nicht Verbunden");
                    getWindow().setStatusBarColor(Color.RED);
                }

            }

        });
    }

}