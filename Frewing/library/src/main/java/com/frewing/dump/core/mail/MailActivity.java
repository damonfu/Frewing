package com.frewing.dump.core.mail;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.frewing.dump.core.R;

public class MailActivity extends Activity implements View.OnClickListener, IMessage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        findViewById(R.id.btn_mail).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String[] recipients = { "15889726917@163.com" };
        SendEmailAsyncTask email = new SendEmailAsyncTask();
        email.activity = this;
        email.m = new Mail("15889726917@163.com", "560131fjg");
        email.m.set_host("smtp.163.com");
        email.m.set_port("465");
        email.m.set_from("15889726917@163.com");
        email.m.setBody("test");
        email.m.set_to(recipients);
        email.m.set_subject("test2");
        email.execute();
    }

    @Override
    public void displayMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
