package org.williamsonministry.roomexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "org.williamsonministry.roomexample.EXTRA_TITLE";
    public static final String EXTRA_DESC =
            "org.williamsonministry.roomexample.EXTRA_DESC";
    public static final String EXTRA_PRIORITY =
            "org.williamsonministry.roomexample.EXTRA_PRIORITY";
    public static final String EXTRA_ID =
            "org.williamsonministry.roomexample.EXTRA_ID";

    private EditText etTitle, etDesc;
    private NumberPicker numberPickerPriority;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        numberPickerPriority = findViewById(R.id.numberPickerPriority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            id = intent.getIntExtra(EXTRA_ID, -1);
            setTitle("Edit Note");
            etTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            etDesc.setText(intent.getStringExtra(EXTRA_DESC));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }
    }

    private void saveNote() {
        String title = etTitle.getText().toString();
        String desc = etDesc.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        //Could make a ViewModel just for this activity, which doesn't need all the things like GetAllNotes. But for now, we're going to send all this data to the Main Activity
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESC, desc);
        data.putExtra(EXTRA_PRIORITY, priority);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data); //This goes back to 'startActivityForResult()' in main (processed in 'onResult')
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}