package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener {

    private RecyclerView recyclerView;
    private List<MyModel> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        // Tạo dữ liệu mẫu với ít nhất 10 items độc đáo
        dataList = new ArrayList<>();
        dataList.add(new MyModel("Alice", 25, "Software Engineer"));
        dataList.add(new MyModel("Bob", 30, "Product Manager"));
        dataList.add(new MyModel("Charlie", 22, "UX Designer"));
        dataList.add(new MyModel("Diana", 28, "Data Scientist"));
        dataList.add(new MyModel("Ethan", 35, "DevOps Engineer"));
        dataList.add(new MyModel("Fiona", 26, "Marketing Specialist"));
        dataList.add(new MyModel("George", 32, "Backend Developer"));
        dataList.add(new MyModel("Hannah", 24, "Frontend Developer"));
        dataList.add(new MyModel("Isaac", 29, "Mobile Developer"));
        dataList.add(new MyModel("Julia", 27, "Business Analyst"));
        dataList.add(new MyModel("Kevin", 31, "Cloud Architect"));
        dataList.add(new MyModel("Luna", 23, "UI/UX Designer"));

        // Thiết lập RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Thêm spacing giữa các items
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerView.addItemDecoration(new SpacingItemDecoration(spacingInPixels));

        // Khởi tạo Adapter
        CustomAdapter adapter = new CustomAdapter(this, dataList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(MyModel model) {
        String message = "✨ " + model.getName() + "\n" +
                "📋 " + model.getDescription() + "\n" +
                "🎂 Age: " + model.getAge();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Inner class để tạo spacing giữa các items
    private class SpacingItemDecoration extends RecyclerView.ItemDecoration {
        private final int spacing;

        public SpacingItemDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(android.graphics.Rect outRect, android.view.View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = spacing;
        }
    }
}