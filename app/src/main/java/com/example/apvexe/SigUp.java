package com.example.apvexe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SigUp extends AppCompatActivity {
    private TextInputLayout edtEmail,edtPassword,edtPassword2;
    private Button BtnSigUp,regToLoginBtn;
    private ProgressDialog diaLog
            ;    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_up);

        initui();
        inilistener();




    }

    private void inilistener() {
        BtnSigUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickSingUp();
            }
        });

    }

    private void onclickSingUp() {
        String strEmail = edtEmail.getEditText().getText().toString().trim();
        String strPass = edtPassword.getEditText().getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        diaLog.show();
        auth.createUserWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        diaLog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Intent intent = new Intent(SigUp.this,TrangChu.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SigUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private  void initui(){

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_pasword);
        edtPassword2 = findViewById(R.id.edt_pasword2);
        BtnSigUp = findViewById(R.id.btn_sing_up);
        diaLog = new ProgressDialog(this);

    }

}