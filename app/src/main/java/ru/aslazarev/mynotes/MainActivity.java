package ru.aslazarev.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

import ru.aslazarev.mynotes.data.Note;
import ru.aslazarev.mynotes.fragments.NoteEditorFragment;
import ru.aslazarev.mynotes.fragments.NotesListFragment;

import static ru.aslazarev.mynotes.fragments.NotesListFragment.vha;
import static ru.aslazarev.mynotes.fragments.NotesListFragment.recyclerView;

public class MainActivity extends AppCompatActivity {

    private static final String COLLECTION_NOTES = "as.lazarev.CollectionNotes";
    FirebaseFirestore notesStore = FirebaseFirestore.getInstance();
    CollectionReference notesCollection = notesStore.collection(COLLECTION_NOTES);



    public static final ArrayList<Note> notes = new ArrayList<>();
   /* {
        notes.add(new Note("Толстой", "Пообщаться с Антоном по системе лояльности", new Date()));
        notes.add(new Note("Матрешка", "Пообщаться со Светланой по акцизам", new Date()));
        notes.add(new Note("Легенда", "Заехать на открытие", new Date()));
        notes.add(new Note("Опера", "Новые проксимити считыватели", new Date()));
        notes.add(new Note("Акварелла", "Пообщаться по вопросу интеграции мобильного приложения с системами лояльности", new Date()));
    }*/

    private void onFetchComplete(Task<QuerySnapshot> task){
        notes.clear();
        if(task.isSuccessful()){
            for(QueryDocumentSnapshot document : task.getResult()) {
                notes.add(new Note(document.getId(), document.getData()));
            }
        }
    }

    private void onFetchFailed(Exception e){
        Log.e("Failed", "Fetch failed", e);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesCollection.orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(this::onFetchComplete).addOnFailureListener(this::onFetchFailed);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout_new);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return false;
            }
        });

        if(savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_list_layout, new NotesListFragment());
            transaction.commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.add_note) {
                Note currentNote = new Note();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_list_layout, NoteEditorFragment.newInstance(currentNote));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                notes.add(0, currentNote);
                notesCollection.add(currentNote.getFields());
                vha.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
        } else if (item.getItemId() == R.id.action_search){

        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}