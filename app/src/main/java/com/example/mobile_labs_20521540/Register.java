package com.example.mobile_labs_20521540;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Register extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText editName;
    EditText editPhone;
    EditText editUser;
    EditText editPass;
    Button btnSign;

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5Password = number.toString(16);

            while (md5Password.length() < 32) {
                md5Password = "0" + md5Password;
            }

            return md5Password;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnSign =findViewById(R.id.btnSign);
        editName = findViewById(R.id.inputName);
        editPhone = findViewById(R.id.inputPhone);
        editUser = findViewById(R.id.inputUser);
        editPass = findViewById(R.id.inputPass);
        TextView txtLogin = findViewById(R.id.txtLogin);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, user, pass, phone;
                name = editName.getText().toString();
                phone = editPhone.getText().toString();
                user = editUser.getText().toString();
                pass = editPass.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    editName.setError("Không được để trống");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    editPhone.setError("Không được để trống");
                    return;
                }
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
                Map<String, Object> account = new HashMap<>();
                account.put("Fullname",name);
                account.put("Phone", phone);
                account.put("Username", user);
                account.put("Password", encryptPassword(pass));

                db.collection("users")
                        .add(account)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

    }
}