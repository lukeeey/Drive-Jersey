package lukeeey.drivejsy.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import lukeeey.drivejsy.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}