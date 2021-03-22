package ru.aslazarev.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final ArrayList<Note> notes = new ArrayList<>();
    {
        notes.add(new Note("Толстой", "Пообщаться с Антоном по системе лояльности", new Date()));
        notes.add(new Note("Матрешка", "Пообщаться с Светланой по акцизам", new Date()));
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}