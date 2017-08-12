package com.frewing.dump.core.mail;

import android.os.AsyncTask;
import android.util.Log;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

/**
 * Created by fujianguo on 17-8-12.
 */
public class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
    public Mail m;
    public IMessage activity;
    
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            if (m.send()) {
                activity.displayMessage("Email sent.");
            } else {
                activity.displayMessage("Email failed to send.");
            }

            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
            e.printStackTrace();
            activity.displayMessage("Authentication failed.");
            return false;
        } catch (MessagingException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Email failed");
            e.printStackTrace();
            activity.displayMessage("Email failed to send.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            activity.displayMessage("Unexpected error occured.");
            return false;
        }
    }
}
