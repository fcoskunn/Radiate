package com.fc.radiate.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;

import com.fc.radiate.R;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_general);

        Preference searchPreference = findPreference("searchComponent");
        Preference limitPreference = findPreference("searchLimit");
        Preference downloadImgPref = findPreference("DownloadImages");

        searchPreference.setOnPreferenceChangeListener(this);
        limitPreference.setOnPreferenceChangeListener(this);
        downloadImgPref.setOnPreferenceChangeListener(this);


        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(limitPreference, pref.getString("searchLimit","10"));
        SharedPreferences pref0 = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(searchPreference, pref0.getString("searchComponent","Click Count"));

        /*ListPreference listPreference = (ListPreference) searchPreference;
        int  prefIndex  = listPreference.findIndexOfValue(pref0.getString("searchComponent", "Click Count"));
        if (prefIndex >= 0) {
            listPreference.setSummary(listPreference.getEntries()[prefIndex]);
        }*/
        onPreferenceChange(downloadImgPref,pref.getBoolean("DownloadImages",true));


    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int  prefIndex  = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else if (preference instanceof EditTextPreference){
            preference.setSummary(stringValue);
        }
        else{
            ((SwitchPreference) preference).setChecked(getPreferenceManager().getSharedPreferences().getBoolean("DownloadImages", true));
        }
        return true;
    }
}
