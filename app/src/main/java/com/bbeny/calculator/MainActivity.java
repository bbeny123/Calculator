package com.bbeny.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button simpleButton = (Button) findViewById(R.id.simpleButton);
        simpleButton.setOnClickListener(this);
        Button advancedButton = (Button) findViewById(R.id.advancedButton);
        advancedButton.setOnClickListener(this);
        Button aboutButton = (Button) findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(this);
        Button exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simpleButton: {
                Intent intent = new Intent(this, SimpleActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.advancedButton: {
                Intent intent = new Intent(this, AdvancedActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.aboutButton: {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.exitButton: {
                finish();
                break;
            }
        }
    }
}
