package com.example.apnadairy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Register extends AppCompatActivity {



    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String MobileNumber;

    com.google.firebase.auth.FirebaseAuth mAuth;
    com.google.firebase.auth.FirebaseUser mUser;
    private boolean passwordShowing = false;
    private boolean confirmPasswordShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final android.widget.EditText name = findViewById(R.id.fullNameEditText);
        final android.widget.EditText email = findViewById(R.id.emailEditText);
        final android.widget.EditText mobile = findViewById(R.id.mobileNumberEditText);

        final android.widget.EditText passwordEt = findViewById(R.id.password);
        final android.widget.EditText confirmPassword = findViewById(R.id.confirmPassword);
        final android.widget.ImageView passwordVisibility  = findViewById(R.id.passwordVisibility);
        final android.widget.ImageView confirmPasswordVisibility = findViewById(R.id.confirmPasswordVisibility);

        final androidx.appcompat.widget.AppCompatButton signUpButton = findViewById(R.id.signUpButton);
        final android.widget.TextView signInButton = findViewById(R.id.signInButton);

        final android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(this);
        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        passwordVisibility.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {


                //checking if the password is showing or not;
                if(passwordShowing == true){
                    passwordShowing = false;
                    passwordEt.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordVisibility.setImageResource(R.drawable.password_show);

                }else{
                    passwordShowing = true;
                    passwordEt.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordVisibility.setImageResource(com.example.apnadairy.R.drawable.ic_baseline_visibility_off_24);
                }
                //move the cursor at the last
                passwordEt.setSelection(confirmPassword.length());
            }
        });

        confirmPasswordVisibility.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                //checking if the confirmpassword is showing or not;
                if(confirmPasswordShowing== true){
                    confirmPasswordShowing = false;
                    confirmPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordVisibility.setImageResource(R.drawable.password_show);

                }else{
                      confirmPasswordShowing= true;
                    confirmPassword.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordVisibility.setImageResource(com.example.apnadairy.R.drawable.ic_baseline_visibility_off_24);
                }
                //move the cursor at the last
                confirmPassword.setSelection(confirmPassword.length());
            }
        });

        signUpButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                //opening otp verification along with mobile and email


                final String getMobileText = mobile.getText().toString();
                final String getEmailText =  email.getText().toString();
                final String nameTxt = name.getText().toString();
                final String passwordTxt = passwordEt.getText().toString();
                final String conPassTxt = confirmPassword.getText().toString();

                MobileNumber = getMobileText;


                if(!getEmailText.matches(emailPattern)){
                    email.setError("Enter correct Email");
                }else if(passwordTxt.isEmpty() || passwordTxt.length()<6){
                    passwordEt.setError("Enter proper password");
                }else if(!passwordTxt.equals(conPassTxt)){
                    android.widget.Toast.makeText(com.example.apnadairy.Register.this, "Password is not matching", android.widget.Toast.LENGTH_SHORT).show();
                }else{

                    progressDialog.setMessage("Please wait");
                    progressDialog.setTitle("Registration under process");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    mAuth.createUserWithEmailAndPassword(getEmailText,passwordTxt).addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                               // sendUserToNextActivity();
                                android.widget.Toast.makeText(com.example.apnadairy.Register.this, "Registration Successful", android.widget.Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.dismiss();
                                android.widget.Toast.makeText(com.example.apnadairy.Register.this, ""+task.getException(), android.widget.Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }


                com.google.firebase.auth.PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91"+mobile.getText().toString(),
                        60,// This means once the otp is sent to your mobile number you can't get the otp in the next 60 seconds.

                        java.util.concurrent.TimeUnit.SECONDS,
                        Register.this,
                        new com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@androidx.annotation.NonNull com.google.firebase.auth.PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@androidx.annotation.NonNull com.google.firebase.FirebaseException e) {
                                android.widget.Toast.makeText(com.example.apnadairy.Register.this, e.getMessage(), android.widget.Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@androidx.annotation.NonNull String verificationId, @androidx.annotation.NonNull com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                android.content.Intent intent = new android.content.Intent(getApplicationContext(), OTPverification.class);

                                // This will restrict the user from coming back to this activity again when the registration is successfull

                               // intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK| android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("mobile", MobileNumber);
                                intent.putExtra("verificationId",verificationId);
                                // intent.putExtra("email", getEmailText);
                                startActivity(intent);
                            }
                        }

                );



            }
        });

        signInButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                finish();
            }
        });



    }

    private void sendUserToNextActivity() {
        android.content.Intent intent = new android.content.Intent(getApplicationContext(), OTPverification.class);

        // This will restrict the user from coming back to this activity again when the registration is successfull

        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK| android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("mobile", MobileNumber);
       // intent.putExtra("email", getEmailText);
        startActivity(intent);
    }
}