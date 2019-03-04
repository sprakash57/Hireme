package com.insu.sunny.hireme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class FeedActivity extends Activity {

    private EditText editCompany, editEmail, editDescription, editLocation, editTextCarrierNumber;
    private Button sendButton;
    private CountryCodePicker ccp;

    String comp, email, phone, cont = "", desc, loc;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        initElements();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comp = editCompany.getText().toString();
                email = editEmail.getText().toString();
                desc = editDescription.getText().toString();
                loc = editLocation.getText().toString();
                phone = ccp.getFullNumberWithPlus();
                if (email.matches(emailPattern)) {
                    sendMail();
                } else {
                    Toast.makeText(FeedActivity.this,"Please enter valid email", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initElements() {
        editCompany = findViewById(R.id.editCompany);
        editEmail = findViewById(R.id.editEmail);
        editLocation = findViewById(R.id.editLocation);
        editDescription = findViewById(R.id.editDescription);
        sendButton = findViewById(R.id.sendButton);
        ccp = findViewById(R.id.ccp);
        editTextCarrierNumber = findViewById(R.id.editText_carrierNumber);
        ccp.registerCarrierNumberEditText(editTextCarrierNumber);
    }

    private void sendMail() {

        final String subject = "Sent from hireme App";
        String emailBody = "Message: ---->"
                + "<p><strong>Company</strong>: " + editCompany.getText().toString() + "</p>"
                + "<p><strong>Email</strong>: " + editEmail.getText().toString() + "</p>"
                + "<p><strong>Phone</strong>: " + phone + "</p>"
                + "<p><strong>Location</strong>: " + editLocation.getText().toString() + "</p>"
                + "<p><strong>Job</strong>: " + editDescription.getText().toString() + "</p>";
        new SendMailTask(FeedActivity.this).execute(Constants.USER, Constants.PASS, Constants.MAILTO, subject, emailBody);

    }
}
