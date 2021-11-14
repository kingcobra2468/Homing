package com.github.homing.fragments;

import android.os.Bundle;
import android.text.InputType;

import com.github.homing.R;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * Settings fragment in charge for preferences.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        EditTextPreference portPreference = findPreference("ucrs_port");

        // set the ucrs port preference to have numerical input
        if (portPreference != null) {
            portPreference.setOnBindEditTextListener(
                    editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER)
            );
        }
    }

}
