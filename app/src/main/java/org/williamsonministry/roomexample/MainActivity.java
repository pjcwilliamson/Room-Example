package org.williamsonministry.roomexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

//Dependent on this: https://www.youtube.com/watch?v=Jwdty9jQN0E&list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118&index=2

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    // TODO: 6/13/2022 This is the next video https://www.youtube.com/watch?v=reSPN7mgshI 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        // Maybe new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);  (check comments on this vid: https://www.youtube.com/watch?v=JLwW5HivZg4)
        // In video he gives noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {  /*This is LiveData secret sauce*/
            @Override
            public void onChanged(List<Note> notes) {
                // TODO: 6/13/2022 update RecyclerView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_LONG).show();
            }
        });
    }
}