package com.example.taskapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskapp.R;
import com.example.taskapp.adapters.TaskAdapter;
import com.example.taskapp.database.TaskDbHelper;
import java.util.Collections;

public class ScheduleFragment extends Fragment {
    private TaskDbHelper dbHelper;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        dbHelper = new TaskDbHelper(getContext());
        
        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskAdapter(Collections.emptyList());
        recyclerView.setAdapter(adapter);
        
        // Load future tasks
        loadFutureTasks();
        return view;
    }

    private void loadFutureTasks() {
        adapter.updateTasks(dbHelper.getFutureTasks(System.currentTimeMillis()));
    }
}
