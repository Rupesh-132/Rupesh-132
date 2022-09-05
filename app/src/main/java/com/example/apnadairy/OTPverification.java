package com.example.apnadairy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class OTPverification extends AppCompatActivity {

    private android.widget.EditText otpEt1, otpEt2, otpEt3, otpEt4,otpEt5,otpEt6;
    private android.widget.TextView resendCode;

    // we will make it true after every 60 sec;
    private boolean resendEnabled = false;

    //resend time in seconds
    private int resendTimer = 60;

    private int selectedETPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        otpEt1 = findViewById(R.id.otpET1);
        otpEt2 = findViewById(R.id.otpET2);
        otpEt3 = findViewById(R.id.otpET3);
        otpEt4 = findViewById(R.id.otpET4);
        otpEt5 = findViewById(R.id.otpET5);
        otpEt6 = findViewById(R.id.otpET6);

        resendCode = findViewById(R.id.resendButton);

        //final android.widget.TextView otpEmail = findViewById(R.id.otpEmail);
        final android.widget.TextView otpMobile = findViewById(R.id.otpMobile);
        final androidx.appcompat.widget.AppCompatButton verifyButton = findViewById(R.id.verifyButton);


        //getting email and mobile number from the register activity using intent;
        final String getEmail = getIntent().getStringExtra("email");
        final String getMobileNumber = getIntent().getStringExtra("mobile");

        //setting the email and the mobile number to the textView of otpVerification page
        //otpEmail.setText(getEmail);
        otpMobile.setText(getMobileNumber);

        //otpEt1.addTextChangedListener(textWatcher);
        //otpEt2.addTextChangedListener(textWatcher);
        //otpEt3.addTextChangedListener(textWatcher);
       // otpEt4.addTextChangedListener(textWatcher);
       // otpEt5.addTextChangedListener(textWatcher);
       // otpEt6.addTextChangedListener(textWatcher);



        //By default open keyboard at otpEt1;
        showKeyboard(otpEt1);

        // start resend countDown timer


        verifyButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                final String generateOtp = otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString();

                if(generateOtp.length() == 6){
                    // handle the verification process;

                }
            }
        });

       setupOtpInputs();
    }

    private void showKeyboard(android.widget.EditText otpET) {
        //Call this to try to give focus to a specific view or to one of its descendants.
        otpET.requestFocus();
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, inputMethodManager.SHOW_IMPLICIT);
    }



    private void setupOtpInputs(){
        otpEt1.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    otpEt2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {

            }
        });
        otpEt2.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    otpEt3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {

            }
        });
        otpEt3.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    otpEt4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {

            }
        });
        otpEt4.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    otpEt5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {

            }
        });
        otpEt5.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    otpEt6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {

            }
        });

    }


}