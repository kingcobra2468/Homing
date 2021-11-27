package com.github.homing.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.homing.R;
import com.github.homing.network.UcrsCallback;
import com.github.homing.network.UcrsRepository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

/**
 * Main activity for dealing with configuration options. Handles events generated by
 * preferences.
 */
public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences preferences;
    private UcrsRepository ucrsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // update ucrs with latest saved user settings
        ucrsRepository = UcrsRepository.getInstance()
                .setAddress(preferences.getString("ucrs_hostname", "127.0.0.1"))
                .setPort(preferences.getString("ucrs_port", "8080"))
                .setGatewayHost(preferences.getString("service_name", "ucrs"))
                .setGatewayKey(preferences.getString("gateway_key", ""))
                .setKeyHeaderName(preferences.getString("api_key_name", "api-key"))
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // handle the heart icon health check button
            case R.id.action_health_check:
                ucrsRepository.ping(new UcrsCallback() {
                    @Override
                    public void onSuccess() {
                        Context context = getApplicationContext();
                        CharSequence text = "Pong";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                    @Override
                    public void onError(int code, String message) {
                        Context context = getApplicationContext();
                        CharSequence text = message;
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });
                break;
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "ucrs_hostname":
                ucrsRepository.setAddress(preferences.getString("ucrs_hostname", "127.0.0.1"))
                        .build();
                break;
            case "ucrs_port":
                ucrsRepository.setPort(preferences.getString("ucrs_port", "8080"))
                        .build();
                break;
            case "service_name":
                ucrsRepository.setGatewayHost(preferences.getString("service_name", "ucrs"))
                        .build();
                break;
            case "gateway_key":
                ucrsRepository.setGatewayKey(preferences.getString("gateway_key", ""))
                        .build();
                break;
            case "api_key_name":
                ucrsRepository.setKeyHeaderName(preferences.getString("api_key_name", "api-key"));
                break;
            case "api_gateway_enabled":
                boolean apiGatewayState = preferences.getBoolean(
                        "api_gateway_enabled",
                        false);
                if (!apiGatewayState) {
                    ucrsRepository.resetHeaders();
                } else {
                    ucrsRepository.setGatewayHost(preferences.getString("service_name", "ucrs"))
                            .build();
                    ucrsRepository.setGatewayKey(preferences.getString("gateway_key", ""))
                            .build();
                }
                break;
        }
    }
}