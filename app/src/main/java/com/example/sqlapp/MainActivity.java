package com.example.sqlapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        MyOpenHelper openHelper = new MyOpenHelper(this);

        EditText editTextName = findViewById(R.id.name_edit_text);
        EditText editTextBody = findViewById(R.id.body_edit_text);
        Spinner commentSpinner = findViewById(R.id.comment_spinner);
        TextView bodyDisplay = findViewById(R.id.text_body_display);

        ArrayList<Comment> arrayList = openHelper.getComments();

        ArrayAdapter<Comment> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        commentSpinner.setAdapter(adapter);


        Button crearButton = findViewById(R.id.button_crear);
        Button veureButton = findViewById(R.id.button_veure);
        Button eliminarButton = findViewById(R.id.button_eliminar);

        crearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = new Comment();
                comment.setName(editTextName.getText().toString());
                comment.setBody(editTextBody.getText().toString());
                openHelper.addComment(comment);
                adapter.notifyDataSetChanged();
            }
        });

        veureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment selectedComment = (Comment) commentSpinner.getSelectedItem();

                if (selectedComment != null) {
                    String name = selectedComment.getName();
                    String body = selectedComment.getBody();

                    bodyDisplay.setText(name + "\n" + "\n" + body);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

    }
}