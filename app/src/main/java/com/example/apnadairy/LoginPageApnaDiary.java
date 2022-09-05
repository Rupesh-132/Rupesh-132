package com.example.apnadairy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class LoginPageApnaDiary extends AppCompatActivity {

    private boolean passwordShowing = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    com.google.firebase.auth.FirebaseAuth mAuth;
    com.google.firebase.auth.FirebaseUser mUser;

    android.widget.EditText email;
    android.widget.EditText passwordEditText;
    android.app.ProgressDialog progressDialog;

    // Initializing the objects for getting sign in option with google;
    com.google.android.gms.auth.api.signin.GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    android.widget.RelativeLayout signInWithGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page_apna_diary);

         email = findViewById(R.id.usernameEditText);
         passwordEditText = findViewById(R.id.passwordEditText);
        final android.widget.Button loginButton = findViewById(R.id.signInButton);
        final android.widget.ImageView passwordVisibility = findViewById(R.id.passwordVisibility);
        final android.widget.TextView signUpButton = findViewById(R.id.signUpButton);



        progressDialog = new android.app.ProgressDialog(this);
        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        signInWithGoogle = findViewById(com.example.apnadairy.R.id.signInWithGoogle);



        passwordVisibility.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                //checking if the password is showing or not;
                      if(passwordShowing == true){
                          passwordShowing = false;
                          passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                          passwordVisibility.setImageResource(R.drawable.password_show);

                      }else{
                          passwordShowing = true;
                          passwordEditText.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                          passwordVisibility.setImageResource(com.example.apnadairy.R.drawable.ic_baseline_visibility_off_24);
                      }
                //move the cursor at the last
                passwordEditText.setSelection(passwordEditText.length());
            }
        });



        signUpButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                startActivity(new android.content.Intent(getApplicationContext(),Register.class));
            }
        });


        loginButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                final String phoneTxt = email.getText().toString();
                final String passwordText = passwordEditText.getText().toString();
                
                if(phoneTxt.isEmpty() || passwordText.isEmpty()){
                    android.widget.Toast.makeText(com.example.apnadairy.LoginPageApnaDiary.this, "Please enter your mobile or password", android.widget.Toast.LENGTH_SHORT).show();
                }else{

                    peformLogin();

                }
            }
        });


        // for performing the google sign in in the login screen;
        gso = new com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this,gso);

        // Setting onclick listener on the google button;
        signInWithGoogle.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                signIn();
            }
        });

    }

    private void signIn() {
        android.content.Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            com.google.android.gms.tasks.Task<com.google.android.gms.auth.api.signin.GoogleSignInAccount>task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(com.google.android.gms.common.api.ApiException.class);
                navigateToSecondActivity();
            } catch (com.google.android.gms.common.api.ApiException e) {
                e.printStackTrace();
                android.widget.Toast.makeText(this, "Something went wrong", android.widget.Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void navigateToSecondActivity() {
        finish();
        android.content.Intent intent = new android.content.Intent(LoginPageApnaDiary.this,MainActivity.class);
        startActivity(intent);
    }

    private void peformLogin() {


        final String getEmailText =  email.getText().toString();

        final String passwordTxt = passwordEditText.getText().toString();




        if(!getEmailText.matches(emailPattern)){
            email.setError("Enter correct Email");
        }else if(passwordTxt.isEmpty() || passwordTxt.length()<6){
            passwordEditText.setError("Enter proper password");
        }else{

            progressDialog.setMessage("Please wait while login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(getEmailText,passwordTxt).addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                @Override
                public void onComplete(@androidx.annotation.NonNull com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        android.widget.Toast.makeText(com.example.apnadairy.LoginPageApnaDiary.this, "Login Successful", android.widget.Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        android.widget.Toast.makeText(LoginPageApnaDiary.this, ""+task.getException(), android.widget.Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}

    private void sendUserToNextActivity() {
        android.content.Intent intent = new android.content.Intent(getApplicationContext(), OTPverification.class);

        // This will restrict the user from coming back to this activity again when the Login is successfull
        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK| android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        // intent.putExtra("email", getEmailText);
        startActivity(intent);
    }
}