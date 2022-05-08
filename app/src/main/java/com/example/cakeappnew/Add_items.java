package com.example.cakeappnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cakeappnew.Database.DBHandler;

public class Add_items extends AppCompatActivity {

    EditText itemCode, itemName, itemType, itemPrice;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        itemCode = findViewById(R.id.itemCode1);
        itemName = findViewById(R.id.itemName1);
        itemType = findViewById(R.id.itemType1);
        itemPrice = findViewById(R.id.itemPrice1);
        Submit = findViewById(R.id.button2);

        Submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                long newId = dbHandler.addInfo(itemCode.getText().toString(), itemName.getText().toString(),
                        itemType.getText().toString(), itemPrice.getText().toString());
                Toast.makeText(Add_items.this, "User Added. User ID:"+newId, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(),Edit_items.class);
                startActivity(i);

                itemCode.setText(null);
                itemName.setText(null);
                itemType.setText(null);
                itemPrice.setText(null);

            }

        });

    }
}