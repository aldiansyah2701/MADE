package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.notification;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.R;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.ResultsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SettingMenu extends AppCompatPref{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        List<ResultsItem> mNotifList;
        MovieDailyReceiver mMovieDailyReceiver = new MovieDailyReceiver();
        MovieUpcomingReceiver mMovieUpcomingReceiver = new MovieUpcomingReceiver();
        SwitchPreference mSwitchReminder;
        SwitchPreference mSwitchToday;

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean value = (boolean) newValue;
            if (key.equals(getString(R.string.today_reminder))) {
                if (value) {
                    mMovieDailyReceiver.setAlarm(getActivity());
                } else {
                    mMovieDailyReceiver.cancelAlarm(getActivity());
                }
            } else {
                if (value) {

                    mMovieUpcomingReceiver.setAlarm(getActivity(), mNotifList);
                } else {
                    mMovieUpcomingReceiver.cancelAlarm(getActivity());
                }
            }
            return true;
        }


        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.main_pref);
            mNotifList = new ArrayList<>();
            mSwitchReminder = (SwitchPreference) findPreference(getString(R.string.today_reminder));
            mSwitchReminder.setOnPreferenceChangeListener(this);
            mSwitchToday = (SwitchPreference) findPreference(getString(R.string.key_release_reminder));
            mSwitchToday.setOnPreferenceChangeListener(this);
            Preference myPref = findPreference(getString(R.string.key_lang));
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    return true;
                }
            });
        }
    }

}
