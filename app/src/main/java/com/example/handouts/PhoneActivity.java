package com.example.handouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {

    EditText t1, t2;
    Button b1, b2;

    String otp;
    public String name;
    public String pass;
    public String college;
    public String dob;
    public String email;
    public String num;
    public String type;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference ref;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        t1=findViewById(R.id.editText);
        b1=findViewById(R.id.btnlogin);
        b2=findViewById(R.id.btnlogin2);

        auth=FirebaseAuth.getInstance();
        mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(PhoneActivity.this, "Verify Using OTP", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(PhoneActivity.this, "Verification Failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken){
                super.onCodeSent(s, forceResendingToken);
                verificationCode=s;
                Toast.makeText(PhoneActivity.this,"Code Has Been Sent To Your Phone", Toast.LENGTH_SHORT).show();
            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=getIntent();
                final String num=intent.getStringExtra("contact");

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91"+num,
                        60,
                        TimeUnit.SECONDS,
                        PhoneActivity.this,
                        mCallback);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp=t1.getText().toString().trim();

                if(otp.length()==0){
                    t1.setError("Please enter the OTP");
                }

                else{
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCode, otp);

                    signinWithPhone(credential);
                }
            }
        });
    }

    private void signinWithPhone(PhoneAuthCredential credential){
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Intent intent=getIntent();
                name=intent.getStringExtra("child");
                pass=intent.getStringExtra("pass");
                college=intent.getStringExtra("college");
                dob=intent.getStringExtra("dob");
                email=intent.getStringExtra("email");
                num=intent.getStringExtra("contact");

                final Register register=new Register(name, pass, type);
                final Details details=new Details(college, dob, num, email);

                if(task.isSuccessful()){
                    database= FirebaseDatabase.getInstance();
                    ref=database.getReference();
                    ref.child(name).setValue(register);
                    ref.child(name).child("Details").setValue(details);
                    Intent intent1=new Intent(PhoneActivity.this, CommunicateActivity.class);
                    startActivity(intent1);
                }

                else{
                    Toast.makeText(PhoneActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
