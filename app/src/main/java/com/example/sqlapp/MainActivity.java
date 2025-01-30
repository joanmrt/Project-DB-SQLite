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

    MyOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        openHelper = new MyOpenHelper(this);

        EditText editTextName = findViewById(R.id.name_edit_text);
        EditText editTextBody = findViewById(R.id.body_edit_text);
        Spinner commentSpinner = findViewById(R.id.comment_spinner);
        TextView bodyDisplay = findViewById(R.id.text_body_display);

        updateSpinnerData(openHelper, commentSpinner);

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
                updateSpinnerData(openHelper, commentSpinner);
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

        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment selectedComment = (Comment) commentSpinner.getSelectedItem();

                if (selectedComment != null) {

                    openHelper.removeComment(selectedComment);
                    updateSpinnerData(openHelper, commentSpinner);
                }
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

    }

    private void updateSpinnerData(MyOpenHelper openHelper, Spinner commentSpinner) {
        ArrayList<Comment> updatedList = openHelper.getComments();

        ArrayAdapter<Comment> newAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, updatedList);
        newAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        commentSpinner.setAdapter(newAdapter);
    }

}