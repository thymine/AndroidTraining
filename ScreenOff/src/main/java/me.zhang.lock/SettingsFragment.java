package me.zhang.lock;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Li on 2/11/2016 10:09 PM.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

}
