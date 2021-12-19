package sean.to.readbiblesmart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView textView = findViewById(R.id.aboutText);
        textView.setText(getResources().getString(R.string.myself)
                + getResources().getString(R.string.version)
                + getResources().getString(R.string.developer)
                + "\n\n" + getResources().getString(R.string.license_overview)
                + "\n\n" + getResources().getString(R.string.license));

        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}
