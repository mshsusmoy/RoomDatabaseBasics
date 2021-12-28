package com.susmoy.roomdatabasebasics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.susmoy.roomdatabasebasics.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    List<ListData> dataList = new ArrayList<>();
    RoomDBContext database;
    MainRecyclerAdapter mainRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    Button btnAdd,btnReset;
    EditText editText;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(R.layout.activity_main);

        database = RoomDBContext.getInstance(this);
        dataList = database.listDataDao().getAll();

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnReset = (Button) findViewById(R.id.btnReset);
        editText = (EditText) findViewById(R.id.edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(mainRecyclerAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Log.e("$$$","Clciked");
              Toast.makeText(getApplicationContext(), "Hello Toast", Toast.LENGTH_SHORT).show();
              String sText = editText.getText().toString().trim();
              if(!sText.equals("")){
                  ListData data = new ListData();
                  data.setText(sText);
                  database.listDataDao().insert(data);
                  editText.setText("");
                  dataList.clear();
                  dataList.addAll(database.listDataDao().getAll());
                  mainRecyclerAdapter.notifyDataSetChanged();
              }
          }
      });

        btnReset.setOnClickListener(v->{
            Log.e("$$$","Clciked");
            Toast.makeText(getApplicationContext(), "Hello Toast", Toast.LENGTH_SHORT).show();
            database.listDataDao().reset(dataList);
            dataList.clear();
            dataList.addAll(database.listDataDao().getAll());
            mainRecyclerAdapter.notifyDataSetChanged();
        });
    }
}