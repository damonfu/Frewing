package com.frewing.dump;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.frewing.dump.core.mail.MailActivity;

public class MainActivity extends Activity implements View.OnClickListener {

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbHelper dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        startActivity(new Intent(this, MailActivity.class));
        database.execSQL("INSERT INTO dump(prop,message) VALUES(?, ?)", new String[]{"text1", "message1"});
    }
}
