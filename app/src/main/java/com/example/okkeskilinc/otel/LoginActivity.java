package com.example.okkeskilinc.otel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private FirebaseAuth mauth, firebaseAuth,gAuth;
    private GoogleSignInClient googleSignInClient;
    private static final int SIGN_IN_CODE=100;
    private static final String TAG=LoginActivity.class.getSimpleName();
    CallbackManager callbackManager;


    //Button glogin;
    Button login;
    Button lsignup;
    LoginButton loginButton;
    SignInButton glogin;

    TextView resetpassword;
    EditText email;
    EditText password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_flexible);

        FirebaseApp.initializeApp(this);
        glogin=(SignInButton)findViewById(R.id.btnGoogleSignIn);
        resetpassword=(TextView)findViewById(R.id.tvresetpassword);
        login=(Button)findViewById(R.id.btnlogin);
        lsignup=(Button)findViewById(R.id.btnsignup);
        email=(EditText)findViewById(R.id.emailEt);
        password=(EditText)findViewById(R.id.passwordEt);
        loginButton=(LoginButton)findViewById(R.id.btn_facebook_login);
        loginButton.setReadPermissions("email");
        mauth=FirebaseAuth.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        gAuth=FirebaseAuth.getInstance();
        callbackManager=CallbackManager.Factory.create();


        if(mauth.getCurrentUser()!=null|| firebaseAuth.getCurrentUser()!=null||gAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient=GoogleSignIn.getClient(this,gso);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signInEmail();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInFacebook();
            }
        });

        glogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=googleSignInClient.getSignInIntent();
                startActivityForResult(i,SIGN_IN_CODE);
            }
        });

        lsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email_string=email.getText().toString();
                final String password_string=password.getText().toString();

                if(TextUtils.isEmpty(email_string)){
                    Toast.makeText(getApplicationContext(),"Email Alanı Boş Kalamaz",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password_string)){
                    Toast.makeText(getApplicationContext(),"Password Girin",Toast.LENGTH_LONG).show();
                    return;
                }
                if(password_string.length()<6){
                    Toast.makeText(getApplicationContext(),"Password en az 6 haneli olmalı",Toast.LENGTH_SHORT).show();
                    return;
                }
                mauth.createUserWithEmailAndPassword(email_string,password_string)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Yetkilendirme Hatası",Toast.LENGTH_LONG).show();
                                }else {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });

    }

    private  void signInEmail(){

        String email_string=email.getText().toString();
        String password_string=password.getText().toString();

        if(TextUtils.isEmpty(email_string)){
            Toast.makeText(getApplicationContext(),"Lütfen emailinizi giriniz",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password_string)){
            Toast.makeText(getApplicationContext(),"Lütfen parolanızı giriniz",Toast.LENGTH_SHORT).show();
            return;
        }
        mauth.signInWithEmailAndPassword(email_string,password_string)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(getApplicationContext(),"EMAIL ILE GIRIS YAPILDI...",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(LoginActivity.this,MainActivity.class);
                            Log.w("loginactivity","gecis yapilamadi");
                            startActivity(i);
                        }
                        else{
                            Log.e("Hatalı Giriş",task.getException().getMessage());
                        }
                    }
                });

    }

    private void signInFacebook() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }
        });

    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential=FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ERROR",""+e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String email=authResult.getUser().getEmail();
                Toast.makeText(getApplicationContext(),"Email ile login oldunuz."+email,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SIGN_IN_CODE)
        {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            }catch (ApiException e){
                Log.w(TAG,"Google sign in failed",e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential=GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        gAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG,"signInCredential:success");
                    FirebaseUser user=gAuth.getCurrentUser();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else{
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
