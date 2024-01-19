package com.example.listview2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText name_editText;
    EditText id_editText;
    Button add_button;

    ArrayList<String> dataSource;
    ArrayAdapter<String> arrayAdapter;
    AlertDialog.Builder builder;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_button = (Button) findViewById(R.id.add_button);
        name_editText = (EditText) findViewById(R.id.name_editText);
        id_editText = (EditText) findViewById(R.id.id_editText);
        listView = (ListView) findViewById(R.id.listView);

        ButtonHandler handler = new ButtonHandler();
        add_button.setOnClickListener(handler);

        OnItemClickHandler itemClickListener = new OnItemClickHandler();
        listView.setOnItemClickListener(itemClickListener);

        dataSource =  new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, dataSource);
        listView.setAdapter(arrayAdapter);


        builder = new AlertDialog.Builder(MainActivity.this);


    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View view) {
            if (name_editText.getText().toString().equals("") || id_editText.getText().toString().equals("")) {
                Toast toast = Toast.makeText(MainActivity.this, "名字和學號不能空白", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                builder.setTitle("Confirm Message");
                builder.setMessage("are you sure to add?");

                AddButtonHandler handler = new AddButtonHandler();
                builder.setPositiveButton("ok", handler);
                builder.setNegativeButton("cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

        private class AddButtonHandler implements DialogInterface.OnClickListener {
            public void onClick(DialogInterface dialog, int which) {
                dataSource.add("姓名: " + name_editText.getText().toString() + " 學號: " + id_editText.getText().toString());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

    public class OnItemClickHandler implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            builder.setTitle("Confirm Message");
            builder.setMessage("are you sure to remove?");

            DelButtonHandler handler = new DelButtonHandler(position);
            builder.setPositiveButton("ok", handler);
            builder.setNegativeButton("cancel", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        private class DelButtonHandler implements DialogInterface.OnClickListener {
            private int position;

            public DelButtonHandler(int position) {
                this.position = position;
            }

            public void onClick(DialogInterface dialog, int which) {
                dataSource.remove(position);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
}
