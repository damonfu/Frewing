package com.frewing.dump.core;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.frewing.dump.core.mail.IMessage;
import com.frewing.dump.core.mail.Mail;
import com.frewing.dump.core.mail.SendEmailAsyncTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Activity to export all app data files as a zip file on sdcard, and send it via email.
 *
 * Usage:
 * adb shell am start -a com.frewing.dump.core.DUMP_DATA
 */
public class DumpActivity extends Activity implements OnClickListener, IMessage {
    private static String TAG = "DumpActivity";
    private Button mConfirmButton;
    private Button mCancelButton;
    private Button mDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_LEFT_ICON);

        setContentView(R.layout.activity_dump);

        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                android.R.drawable.ic_dialog_alert);

        mConfirmButton = (Button) findViewById(R.id.confirm);
        mCancelButton = (Button) findViewById(R.id.cancel);
        mDeleteButton = (Button) findViewById(R.id.delete);
        updateDeleteButton();
    }

    private void updateDeleteButton() {
        mDeleteButton.setEnabled(DataExporter.dumpFileExists(this));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.confirm) {
            mConfirmButton.setEnabled(false);
            mCancelButton.setEnabled(false);
            new DumpDbTask().execute();
        } else if (i == R.id.delete) {
            cleanup();
            updateDeleteButton();

        } else if (i == R.id.cancel) {
            finish();

        }
    }

    private void cleanup() {
        DataExporter.removeDumpFiles(this);
    }

    private class DumpDbTask extends AsyncTask<Void, Void, Uri> {
        /**
         * Starts spinner while task is running.
         */
        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Uri doInBackground(Void... params) {
            try {
                return DataExporter.exportData(getApplicationContext());
            } catch (IOException e) {
                Log.e(TAG, "Failed to export", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Uri uri) {
            if (uri != null) {
                //emailFile(uri);
                sendMail(uri);
            }
        }
    }

    private void emailFile(Uri uri) {
        Log.i(TAG, "Drafting email");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.debug_dump_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.debug_dump_email_body));
        intent.setType(DataExporter.ZIP_MIME_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivityForResult(Intent.createChooser(intent,
                getString(R.string.debug_dump_email_sender_picker)), 0);
    }

    private void sendMail(Uri uri) {
        try {
            String[] recipients = {"15889726917@163.com"};
            SendEmailAsyncTask email = new SendEmailAsyncTask();
            email.activity = this;
            email.m = new Mail("15889726917@163.com", "560131fjg");
            email.m.set_host("smtp.163.com");
            email.m.set_port("465");
            email.m.set_from("15889726917@163.com");
            email.m.setBody("test");
            email.m.set_to(recipients);
            email.m.set_subject("test2");
            File file = new File(new URI(uri.toString()));
            email.m.addAttachment(file.getAbsolutePath());
            email.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayMessage(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DumpActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateDeleteButton();
        mConfirmButton.setEnabled(true);
        mCancelButton.setEnabled(true);
    }
}
