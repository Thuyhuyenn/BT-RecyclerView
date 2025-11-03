package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener {

    private RecyclerView recyclerView;
    private List<MyModel> dataList;
    private List<MyModel> filteredList;
    private CustomAdapter adapter;
    private SearchView searchView;
    private ChipGroup chipGroupFilter, chipGroupSort;
    private ImageButton btnDarkMode;
    private LinearLayout filterContainer;
    private boolean isDarkMode;
    private String currentFilter = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        chipGroupFilter = findViewById(R.id.chipGroupFilter);
        chipGroupSort = findViewById(R.id.chipGroupSort);
        btnDarkMode = findViewById(R.id.btnDarkMode);
        filterContainer = findViewById(R.id.filterContainer);

        // Initialize data
        initializeData();

        // Setup RecyclerView
        setupRecyclerView();

        // Setup Search
        setupSearch();

        // Setup Filters
        setupFilters();

        // Setup Sort
        setupSort();

        // Apply theme colors
        applyTheme();
    }

    private void initializeData() {
        dataList = new ArrayList<>();
        dataList.add(new MyModel("Alice", 25, "Software Engineer", "online"));
        dataList.add(new MyModel("Bob", 30, "Product Manager", "away"));
        dataList.add(new MyModel("Charlie", 22, "UX Designer", "offline"));
        dataList.add(new MyModel("Diana", 28, "Data Scientist", "online"));
        dataList.add(new MyModel("Ethan", 35, "DevOps Engineer", "online"));
        dataList.add(new MyModel("Fiona", 26, "Marketing Specialist", "away"));
        dataList.add(new MyModel("George", 32, "Backend Developer", "online"));
        dataList.add(new MyModel("Hannah", 24, "Frontend Developer", "offline"));
        dataList.add(new MyModel("Isaac", 29, "Mobile Developer", "online"));
        dataList.add(new MyModel("Julia", 27, "Business Analyst", "away"));
        dataList.add(new MyModel("Kevin", 31, "Cloud Architect", "online"));
        dataList.add(new MyModel("Luna", 23, "UI/UX Designer", "offline"));

        // Load favorites from SharedPreferences
        for (MyModel model : dataList) {
            model.setFavorite(FavoriteManager.isFavorite(this, model.getName()));
        }

        filteredList = new ArrayList<>(dataList);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(this, filteredList, this, isDarkMode);
        recyclerView.setAdapter(adapter);

        // Setup Swipe to Delete
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                MyModel deletedItem = filteredList.get(position);

                // Remove from filtered list
                filteredList.remove(position);
                adapter.notifyItemRemoved(position);

                // Remove from original data list
                dataList.remove(deletedItem);

                Toast.makeText(MainActivity.this, deletedItem.getName() + " removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // Optional: Add visual feedback for swipe
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText, currentFilter);
                return true;
            }
        });
    }

    private void setupFilters() {
        chipGroupFilter.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = findViewById(checkedId);
            if (chip != null) {
                currentFilter = chip.getText().toString();
                filterData(searchView.getQuery().toString(), currentFilter);
            }
        });
    }

    private void setupSort() {
        chipGroupSort.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = findViewById(checkedId);
            if (chip != null) {
                String sortType = chip.getText().toString();
                sortData(sortType);
            }
        });
    }

    private void updateDarkModeIcon() {
        if (isDarkMode) {
            btnDarkMode.setImageResource(android.R.drawable.ic_dialog_info); // Replace with sun icon
        } else {
            btnDarkMode.setImageResource(android.R.drawable.ic_menu_day); // Replace with moon icon
        }
    }

    private void applyTheme() {
        View rootView = findViewById(R.id.main);
        View headerSection = findViewById(R.id.header_section);

        if (isDarkMode) {
            rootView.setBackgroundColor(getResources().getColor(android.R.color.black));
            headerSection.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        } else {
            rootView.setBackgroundColor(getResources().getColor(android.R.color.white));
            headerSection.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    private void filterData(String query, String roleFilter) {
        filteredList.clear();

        for (MyModel model : dataList) {
            boolean matchesSearch = model.getName().toLowerCase().contains(query.toLowerCase());
            boolean matchesFilter = roleFilter.equals("All") || model.getDescription().contains(roleFilter);

            if (matchesSearch && matchesFilter) {
                filteredList.add(model);
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void sortData(String sortType) {
        if (sortType.equals("Name")) {
            Collections.sort(filteredList, new Comparator<MyModel>() {
                @Override
                public int compare(MyModel o1, MyModel o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        } else if (sortType.equals("Age")) {
            Collections.sort(filteredList, new Comparator<MyModel>() {
                @Override
                public int compare(MyModel o1, MyModel o2) {
                    return Integer.compare(o1.getAge(), o2.getAge());
                }
            });
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(MyModel model) {
        String status = model.getStatus().equals("online") ? "🟢 Online" :
                model.getStatus().equals("away") ? "🟡 Away" : "⚫ Offline";

        String message = model.getName() + "\n" +
                model.getDescription() + "\n" +
                "Age: " + model.getAge() + "\n" +
                "Status: " + status;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void onFavoriteClicked(MyModel model) {
        FavoriteManager.toggleFavorite(this, model.getName());
        model.setFavorite(!model.isFavorite());
        adapter.notifyDataSetChanged();

        String message = model.isFavorite() ? "Added to favorites ⭐" : "Removed from favorites";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}