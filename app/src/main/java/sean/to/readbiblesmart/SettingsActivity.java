package sean.to.readbiblesmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.settings, new SettingsFragment())
//                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initSettings();
    }
    public void initSettings(){
        boolean esv = MainActivity.settingsData.isSettings("esv");
        CheckBox esvCheck = (CheckBox)findViewById(R.id.esv);
        esvCheck.setChecked(esv);

        boolean kjv = MainActivity.settingsData.isSettings("kjv");
        CheckBox kjvCheck = (CheckBox)findViewById(R.id.kjv);
        kjvCheck.setChecked(kjv);

        boolean niv = MainActivity.settingsData.isSettings("niv");
        CheckBox nivCheck = (CheckBox)findViewById(R.id.niv);
        nivCheck.setChecked(niv);

        boolean nlt = MainActivity.settingsData.isSettings("nlt");
        CheckBox ntlCheck = (CheckBox)findViewById(R.id.nlt);
        ntlCheck.setChecked(nlt);

        boolean es = MainActivity.settingsData.isSettings("rvr");
        CheckBox esCheck = (CheckBox)findViewById(R.id.es);
        esCheck.setChecked(es);

        boolean fr = MainActivity.settingsData.isSettings("ost");
        CheckBox frCheck = (CheckBox)findViewById(R.id.fr);
        frCheck.setChecked(fr);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.esv:
                sendMessage("esv", checked);
                break;
            case R.id.kjv:
                sendMessage("kjv", checked);
                break;
            case R.id.niv:
                sendMessage("niv", checked);
                break;
            case R.id.nlt:
                sendMessage("nlt", checked);
                break;
            case R.id.es:
                sendMessage("rvr", checked);
                break;
            case R.id.fr:
                sendMessage("ost", checked);
                break;

        }
    }
    public void sendMessage(String name, boolean onoff){
//        if(checked)
            MainActivity.settingsData.setSettings(name, onoff);
    }
}