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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
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

    public static String encodePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] passwordBytes = password.getBytes();

            byte[] hashedBytes = md.digest(passwordBytes);

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
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
                account.put("Password", encodePassword(pass));

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