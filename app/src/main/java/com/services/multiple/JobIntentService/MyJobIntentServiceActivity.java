package com.services.multiple.JobIntentService;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.services.multiple.R;

//nothing to see here, move along.

public class MyJobIntentServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myjob_intent_service);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment()).commit();
        }
    }

}
