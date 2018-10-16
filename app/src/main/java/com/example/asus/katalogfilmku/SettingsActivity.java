package com.example.asus.katalogfilmku;

import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.MenuItem;

import com.example.asus.katalogfilmku.notifikasi.AlarmReceiver;
import com.example.asus.katalogfilmku.service.GetReleaseJobService;
import com.example.asus.katalogfilmku.utils.AppCompatPreferenceActivity;

import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

    }

    public static class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
        private int jobId = 10;

        private Context mContext;

        public void setContext(Context context) {
            mContext = context;
        }


        @BindString(R.string.key_today_reminder)
        String reminder_daily;

        @BindString(R.string.key_release_reminder)
        String reminder_upcoming;

        @BindString(R.string.key_lang)
        String setting_locale;



        private AlarmReceiver alarmReceiver = new AlarmReceiver();


        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);

            ButterKnife.bind(this, getActivity());

            findPreference(reminder_daily).setOnPreferenceChangeListener(this);
            findPreference(reminder_upcoming).setOnPreferenceChangeListener(this);
            findPreference(setting_locale).setOnPreferenceClickListener(this);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String key = preference.getKey();
            boolean isOn = (boolean) o;

            if (key.equals(reminder_upcoming)) {
                if (isOn) {
                    alarmReceiver.setOneTimeAlarm(getActivity(), alarmReceiver.TYPE_ONE_TIME, "08:00");
                } else {
                    cancelJob();
                }
                return true;
            }

            if (key.equals(reminder_daily)) {
                if (isOn) {
                   alarmReceiver.setRepeatingAlarm(getActivity(), alarmReceiver.TYPE_REPEATING, "07:00", getString(R.string.label_alarm_daily_reminder));
                } else
                    alarmReceiver.cancelAlarm(getActivity(), alarmReceiver.TYPE_REPEATING);
                return true;
            }

            return false;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            String key = preference.getKey();

            if (key.equals(setting_locale)) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;
            }

            return false;
        }

        public void startJob() {
            ComponentName mServiceComponent = new ComponentName(mContext, GetReleaseJobService.class);
            JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            builder.setRequiresDeviceIdle(false);
            builder.setRequiresCharging(false);
            builder.setPeriodic(AlarmManager.INTERVAL_DAY);
            /*builder.setPeriodic(1000);*/

            JobScheduler jobScheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());
            Log.d("startJob", "mulai");
        }

        private void cancelJob() {
            JobScheduler tm = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
            tm.cancel(jobId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
