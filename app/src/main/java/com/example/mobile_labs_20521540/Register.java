package com.example.mobile_labs_20521540;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    EditText editName,editPhone, editPass, editUser;
    TextView txtLogin;
    Button btnSign;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editName = (EditText) findViewById(R.id.inputName);
        editPhone = (EditText) findViewById(R.id.inputPhone);
        editUser = (EditText) findViewById(R.id.inputUser);
        editPass = (EditText) findViewById(R.id.inputPass);
        btnSign = (Button) findViewById(R.id.btnSign);
        txtLogin = (TextView) findViewById(R.id.txtLogin);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, user, pass, phone;
                name = String.valueOf(editName.getText());
                phone = String.valueOf(editPass.getText());
                pass = String.valueOf(editPass.getText());
                user = String.valueOf(editUser.getText());

                if (user.length() < 6) {
                    editUser.setError("Mật khẩu phải có ít nhất " + 6 + " kí tự");
                    return;
                }
                if (pass.length() < 6) {
                    editPass.setError("Mật khẩu phải có ít nhất " + 6 + " kí tự");
                    return;
                }

                if (user.matches(".*\\d.*")) {
                    editUser.setError("Username không được chứa kí tự số");
                    return;
                }

                HelperClass helperClass = new HelperClass(name, phone, user, pass);
                SupportClass sp = new SupportClass();

                databaseReference.child(user).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            DataSnapshot snapshot = task.getResult();
                            if (snapshot.exists()){
                                Toast.makeText(getApplicationContext(), "Username da ton tai", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                    helperClass.setPass(sp.PasswordHash(pass));
                                    databaseReference.child(helperClass.getUser()).setValue(helperClass);
                                    Toast.makeText(getApplicationContext(),"Dang ki thanh cong", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(view.getContext(),Login.class);
                                    startActivity(intent);
                                }
                            }

                        }
                });

            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }
}