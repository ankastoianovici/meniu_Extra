package com.example.meniu_extra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private LinearLayout editTextContainer;
    private AlertDialog imageSelectDialog; // Store the AlertDialog reference

    private EditText currentEditText;
    private int offsetX, offsetY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editTextContainer = findViewById(R.id.editTextContainer);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSelectDialog();
            }
        });
    }

    private void showImageSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select an Image");

        // Inflate the custom layout for the AlertDialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.image_list_layout, null);

        // Set the custom layout to the builder
        builder.setView(dialogLayout);

        // Define the click listeners for the images
        ImageView image1 = dialogLayout.findViewById(R.id.image1);


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createEditText(R.drawable.a); // Pass the selected image resource
                createEditText();
                imageSelectDialog.dismiss(); // Dismiss the AlertDialog
            }
        });


        imageSelectDialog = builder.create(); // Create the AlertDialog
        imageSelectDialog.show(); // Show the AlertDialog
    }


    private void createEditText() {
        final EditText newEditText = new EditText(this);

        // Set dimensions for the EditText
        int width = getResources().getDimensionPixelSize(R.dimen.edit_text_width); // Replace with your dimension resource
        int height = getResources().getDimensionPixelSize(R.dimen.edit_text_height); // Replace with your dimension resource
        newEditText.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        newEditText.setHint("Enter text");

        newEditText.setTextColor(getResources().getColor(R.color.black));
        newEditText.setBackgroundResource(R.drawable.border_rows);
        newEditText.setTextSize(16);  // Text size in sp

        newEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentEditText = newEditText;
                        offsetX = (int) event.getX();
                        offsetY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (currentEditText != null) {
                            int x = (int) event.getRawX() - offsetX;
                            int y = (int) event.getRawY() - offsetY;
                            currentEditText.setX(x);
                            currentEditText.setY(y);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        currentEditText = null;
                        break;
                }
                return true;
            }
        });

        editTextContainer.addView(newEditText);
    }
}