package ru.aslazarev.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.Date;

import ru.aslazarev.mynotes.data.Note;
import static ru.aslazarev.mynotes.fragments.NotesListFragment.vha;
import static ru.aslazarev.mynotes.fragments.NotesListFragment.recyclerView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    public static final ArrayList<Note> notes = new ArrayList<>();
    {
        notes.add(new Note("Толстой", "Пообщаться с Антоном по системе лояльности", new Date()));
        notes.add(new Note("Матрешка", "Пообщаться со Светланой по акцизам", new Date()));
        notes.add(new Note("Легенда", "Заехать на открытие", new Date()));
        notes.add(new Note("Опера", "Новые проксимити считыватели", new Date()));
        notes.add(new Note("Акварелла", "Пообщаться по вопросу интеграции мобильного приложения с системами лояльности", new Date()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                int id = item.getItemId();
                if (navigateFragment(id)){
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });


    }






    //шаблон
    private boolean navigateFragment(int id) {

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.add_note) {
                notes.add(0, new Note("Занзибар", "Подготовить летник", new Date()));
                vha.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
        } else if (item.getItemId() == R.id.action_search){

        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    public boolean onContextItemSelected(@NonNull MenuItem item){
        return super.onContextItemSelected(item);
    }
}