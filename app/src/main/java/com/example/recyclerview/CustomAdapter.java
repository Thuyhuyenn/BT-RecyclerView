package com.example.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private final Context context;
    private final List<MyModel> list;
    private final SelectListener listener;

    // Mảng màu pastel nhẹ nhàng cho avatar
    private final String[] avatarColors = {
            "#3B82F6", // Blue
            "#10B981", // Green
            "#F59E0B", // Amber
            "#EF4444", // Red
            "#8B5CF6", // Purple (nhạt)
            "#EC4899", // Pink
            "#14B8A6", // Teal
            "#F97316", // Orange
            "#06B6D4", // Cyan
            "#84CC16"  // Lime
    };

    public CustomAdapter(Context context, List<MyModel> list, SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_unique, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        final MyModel model = list.get(position);

        // Hiển thị chữ cái đầu tiên của tên trong avatar
        String initial = model.getName().substring(0, 1).toUpperCase();
        holder.nameInitial.setText(initial);

        // Đổi màu avatar theo position
        String avatarColor = avatarColors[position % avatarColors.length];
        View avatarBg = holder.itemView.findViewById(R.id.avatar_bg);
        GradientDrawable drawable = (GradientDrawable) avatarBg.getBackground();
        drawable.setColor(Color.parseColor(avatarColor));

        // Hiển thị thông tin
        holder.name.setText(model.getName());
        holder.age.setText(model.getAge() + " years");
        holder.description.setText(model.getDescription());

        // Thiết lập sự kiện click với animation
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Animation nhẹ nhàng
                view.animate()
                        .scaleX(0.97f)
                        .scaleY(0.97f)
                        .setDuration(100)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                view.animate()
                                        .scaleX(1f)
                                        .scaleY(1f)
                                        .setDuration(100)
                                        .start();
                            }
                        })
                        .start();

                listener.onItemClicked(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView name, age, description, nameInitial;
        public CardView cardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            age = itemView.findViewById(R.id.text_age);
            description = itemView.findViewById(R.id.text_description);
            nameInitial = itemView.findViewById(R.id.text_name_initial);
            cardView = itemView.findViewById(R.id.unique_item_container);
        }
    }
}