package com.tech.geeks.techgeeks;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            // Finding preferences to add summary when the app launches or when SettingsActivity is created
            Preference page_size = findPreference(getString(R.string.setting_page_size_key));
            setSummaryToPreference(page_size);

            Preference section = findPreference(getString(R.string.setting_section_key));
            setSummaryToPreference(section);

            Preference order_by = findPreference(getString(R.string.setting_order_by_key));
            setSummaryToPreference(order_by);

        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String value = newValue.toString();
            // If the preference is a ListPreference then we need to find the current value
            // and set that as summary
            if(preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(value);

                if(prefIndex>=0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            }
            else {
                preference.setSummary(value);
            }

            return true;
        }

        /**
         * Sets summary for preference in SettingsActivity
         */
        private void setSummaryToPreference(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences shrdPref = PreferenceManager.getDefaultSharedPreferences(preference.getContext());

            String value = shrdPref.getString(preference.getKey(),"");
            onPreferenceChange(preference,value);
        }
    }
}
