package com.frewing.dump.extra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.frewing.dump.R;

public class ExtraActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction("com.frewing.dump.core.DUMP_DATA");
        startActivity(intent);
    }
}
