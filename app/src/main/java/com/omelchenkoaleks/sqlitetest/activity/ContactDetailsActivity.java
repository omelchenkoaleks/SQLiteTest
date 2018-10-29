package com.omelchenkoaleks.sqlitetest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.omelchenkoaleks.sqlitetest.R;
import com.omelchenkoaleks.sqlitetest.database.DBHelper;
import com.omelchenkoaleks.sqlitetest.model.Contact;

public class ContactDetailsActivity extends AppCompatActivity {

    private Contact contact;
    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phoner);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int position = bundle.getInt("position");
            DBHelper db = new DBHelper(this);
            contact = db.getAllContacts().get(position);
            etName.setText(contact.getName());
            etEmail.setText(contact.getEmail());
            etPhone.setText(contact.getPhone());
        }
    }

    private void updateContact() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (!name.isEmpty() || !email.isEmpty() || !phone.isEmpty()) {

            DBHelper db = new DBHelper(this);
            Contact newContact = new Contact();
            newContact.setId(contact.getId());
            newContact.setName(etName.getText().toString());
            newContact.setEmail(etEmail.getText().toString());
            newContact.setPhone(etPhone.getText().toString());

            db.updateContact(newContact);
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
            finish();

        } else {
            Toast.makeText(this, "Заполните поля!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteContact() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (!name.isEmpty() || !email.isEmpty() || !phone.isEmpty()) {

            DBHelper db = new DBHelper(this);
            db.deleteContact(contact);
            Toast.makeText(this, "Удалено!", Toast.LENGTH_SHORT).show();
            finish();

        } else {
            Toast.makeText(this, "Заполните поля!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                updateContact();
                return true;
            case R.id.menu_delete:
                deleteContact();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
