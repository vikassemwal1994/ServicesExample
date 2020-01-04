package com.services.multiple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.services.multiple.JobIntentService.MyJobIntentServiceActivity;
import com.services.multiple.bindingservice.BindingServiceActivity;
import com.services.multiple.forgroundservice.ForegroundServiceActivity;
import com.services.multiple.freefallservice.FreeFallService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_forground_service = findViewById(R.id.tv_forground_service);
        TextView tv_FreeFallService = findViewById(R.id.tv_FreeFallService);
        TextView tv_MyJobIntentServiceActivity = findViewById(R.id.tv_MyJobIntentServiceActivity);
        TextView tv_JobServiceActivity = findViewById(R.id.tv_JobServiceActivity);


        tv_forground_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForegroundServiceActivity.class));
            }
        });

        tv_FreeFallService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FreeFallService.class));
            }
        });

        tv_MyJobIntentServiceActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyJobIntentServiceActivity.class));
            }
        });

        tv_JobServiceActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BindingServiceActivity.class));
            }
        });


    }
}
