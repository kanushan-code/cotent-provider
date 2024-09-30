package com.example.cotentprovider;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
        PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else {
            Intent openContactApp = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(openContactApp,123);

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==123  && resultCode == RESULT_OK ) {

            String[] columns = new String[]{
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            };
            ContentResolver resolver = getContentResolver();
            Uri dataUri = data.getData();
            Cursor cursor = resolver.query(dataUri, columns, null, null, null);
            if (cursor.moveToFirst()) {


                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(columns[0]));
                Log.i("name", "" + name);

                @SuppressLint("Range") String contact = cursor.getString(cursor.getColumnIndex(columns[1]));
                Log.i("contact", "" + contact);

                TextView tvname = findViewById(R.id.textView);
                tvname.setText(name);

                TextView tvcontact = findViewById(R.id.textView2);
                tvcontact.setText(contact);

            } else  {
                Toast.makeText(this, "Unable to load contact", Toast.LENGTH_SHORT).show();

                }


            }else {
            Toast.makeText(this, "Contact not selected", Toast.LENGTH_SHORT).show();
        }
    }

}