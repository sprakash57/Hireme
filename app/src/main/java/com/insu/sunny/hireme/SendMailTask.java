package com.insu.sunny.hireme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class SendMailTask extends AsyncTask {
    private ProgressDialog progressDialog;
    @SuppressLint("StaticFieldLeak")
    private Activity feedActivity;

    public SendMailTask(Activity activity) {
        feedActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(feedActivity,"","Thank you for your Consideration",true, false);
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            Log.i("SendMailTask", "About to instantiate GMail...");
            publishProgress("Processing input....");
            GMail androidEmail = new GMail(args[0].toString(),
                    args[1].toString(),  args[2].toString(), args[3].toString(),
                    args[4].toString());
            publishProgress("Preparing mail message....");
            androidEmail.createEmailMessage();
            publishProgress("Sending email....");
            androidEmail.sendMail();
            publishProgress("Email Sent.");
            Log.i("SendMailTask", "Mail Sent.");

            return "Success";
            } catch (SendFailedException e) {
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    return "error1";
                }
            } catch (MessagingException e) {
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    return "error2";
                }
            } catch (UnsupportedEncodingException e) {
                publishProgress(e.getMessage());
                Log.e("SendMailTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object backgroundResult) {
        if (backgroundResult.toString().equals("Success")) {
            progressDialog.dismiss();
            Toast.makeText(feedActivity, "Message sent. I will revert shortly", Toast.LENGTH_LONG).show();
            feedActivity.finish();
        } else if (backgroundResult.toString().equals("error1")) {
            Toast.makeText(feedActivity, "Email Failure", Toast.LENGTH_LONG).show();
        } else if(backgroundResult.toString().equals("error2")) {
            Toast.makeText(feedActivity, "Email Sent problem", Toast.LENGTH_LONG).show();
        }
    }
}
