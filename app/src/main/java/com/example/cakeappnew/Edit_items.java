package com.example.cakeappnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cakeappnew.Database.DBHandler;

import java.util.List;

public class Edit_items extends AppCompatActivity {

    EditText itemCode, itemName, itemType, itemPrice;
    Button Edit, Delete,Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_items);

        itemCode = findViewById(R.id.itemCode1);
        itemName = findViewById(R.id.itemName1);
        itemType = findViewById(R.id.itemType1);
        itemPrice = findViewById(R.id.itemPrice1);
        Edit = findViewById(R.id.buttonEdit);
        Delete = findViewById(R.id.buttonDelete);
        Search = findViewById(R.id.buttonSearch);

        Search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                List item = dbHandler.readAllInfo(itemName.getText().toString());

                if (item.isEmpty()){
                    Toast.makeText(Edit_items.this, "NO ITEM!", Toast.LENGTH_SHORT).show();
                    itemName.setText(null);
                }
                else{
                    Toast.makeText(Edit_items.this, "ITEM FOUND!! item:"+item.get(0).toString(), Toast.LENGTH_SHORT).show();
                    itemCode.setText(item.get(0).toString());
                    itemName.setText(item.get(1).toString());
                    itemType.setText(item.get(2).toString());
                    itemPrice.setText(item.get(3).toString());
                }

            }

        });

        Edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());

                Boolean status = dbHandler.updateInfo(itemCode.getText().toString(), itemName.getText().toString(),
                        itemType.getText().toString(), itemPrice.getText().toString());

                if (status){
                    Toast.makeText(Edit_items.this, "Item Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Edit_items.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }

        });


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());
                dbHandler.deleteInfo(itemName.getText().toString());

                Toast.makeText(Edit_items.this,"Item Deleted", Toast.LENGTH_SHORT).show();

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