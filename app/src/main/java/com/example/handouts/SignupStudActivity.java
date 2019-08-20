package com.example.handouts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupStudActivity extends AppCompatActivity {

    EditText t1,t2,t3;
    Button b1;

    FirebaseDatabase database;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_stud);

        Intent intent=getIntent();
        final String name=intent.getStringExtra("child");
        final String pass=intent.getStringExtra("pass");
        final String admin=intent.getStringExtra("type");

        t1=(EditText) findViewById(R.id.editText);
        t2=(EditText) findViewById(R.id.editText2);
        t3=(EditText) findViewById(R.id.editText3);
        b1=(Button) findViewById(R.id.btnlogin);

        b1.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                addDetails(name, pass, admin);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addDetails(String name, String pass, String admin) {
        final String email = t1.getText().toString().trim();
        final String phone = t2.getText().toString().trim();
        final String dept = t3.getText().toString().trim();

        //final Details details=new Details(college, dob, phone, email);

        if (isInternetConnection()) {
            if (phone.length() == 10) {
                if (dept.length() != 0) {
                    if (isValidEmail(email)) {
                        final ProgressDialog Dialog = new ProgressDialog(SignupStudActivity.this);
                        Dialog.setMessage("Loading");
                        Dialog.show();
                        Intent intent = new Intent(SignupStudActivity.this, PhoneActivity.class);
                        intent.putExtra("contact", phone);
                        intent.putExtra("child", name);
                        intent.putExtra("pass", pass);
                        intent.putExtra("college", email);
                        intent.putExtra("dob", phone);
                        intent.putExtra("email", dept);
                        startActivity(intent);
                        Dialog.hide();
                    }

                    else {
                        t1.setError("Enter correct email");
                    }
                }

                else {
                    t3.setError("This field is required");
                }
            }

            else {
                t2.setError("Enter correct mobile number");
            }
        }

        else{
            Toast toast = Toast.makeText(SignupStudActivity.this, "Please check your internet connction", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static boolean isValidEmail(CharSequence email){
        if(email==null){
            return false;
        }

        else{
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public boolean isInternetConnection()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED)
        {
            return true;
        }

        else
        {
            return false;
        }
    }
}
