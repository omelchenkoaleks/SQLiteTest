package com.omelchenkoaleks.sqlitetest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.omelchenkoaleks.sqlitetest.R;
import com.omelchenkoaleks.sqlitetest.database.DBHelper;
import com.omelchenkoaleks.sqlitetest.model.Contact;

public class AddActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        btnSave = findViewById(R.id.bt_save);

        btnSave.setOnClickListener((view) -> {saveContact();});
    }

    private void saveContact() {
        DBHelper db = new DBHelper(this);
        Contact newContact = new Contact();

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (!name.isEmpty() || !email.isEmpty() || !phone.isEmpty()) {
            newContact.setName(name);
            newContact.setEmail(email);
            newContact.setPhone(phone);
            db.insertContact(newContact);
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show();
        }
    }
}
