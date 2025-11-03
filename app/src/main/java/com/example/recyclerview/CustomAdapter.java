package com.example.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private final Context context;
    private final List<MyModel> list;
    private final SelectListener listener;
    private final boolean isDarkMode;

    private final String[] avatarColors = {
            "#3B82F6", "#10B981", "#F59E0B", "#EF4444",
            "#8B5CF6", "#EC4899", "#14B8A6", "#F97316",
            "#06B6D4", "#84CC16"
    };

    public CustomAdapter(Context context, List<MyModel> list, SelectListener listener, boolean isDarkMode) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.isDarkMode = isDarkMode;
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

        // Set initial
        String initial = model.getName().substring(0, 1).toUpperCase();
        holder.nameInitial.setText(initial);

        // Set avatar color
        String avatarColor = avatarColors[position % avatarColors.length];
        View avatarBg = holder.itemView.findViewById(R.id.avatar_bg);
        GradientDrawable drawable = (GradientDrawable) avatarBg.getBackground();
        drawable.setColor(Color.parseColor(avatarColor));

        // Set data
        holder.name.setText(model.getName());
        holder.age.setText(model.getAge() + " years");
        holder.description.setText(model.getDescription());

        // Set status indicator color
        View statusIndicator = holder.itemView.findViewById(R.id.status_indicator);
        GradientDrawable statusDrawable = (GradientDrawable) statusIndicator.getBackground();

        switch (model.getStatus()) {
            case "online":
                statusDrawable.setColor(Color.parseColor("#10B981")); // Green
                break;
            case "away":
                statusDrawable.setColor(Color.parseColor("#F59E0B")); // Amber
                break;
            case "offline":
                statusDrawable.setColor(Color.parseColor("#6B7280")); // Gray
                break;
        }

        // Set favorite icon
        if (model.isFavorite()) {
            holder.btnFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            holder.btnFavorite.setImageResource(android.R.drawable.btn_star_big_off);
        }

        // Apply dark mode
        if (isDarkMode) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#1E293B"));
            holder.name.setTextColor(Color.parseColor("#F1F5F9"));
            holder.description.setTextColor(Color.parseColor("#94A3B8"));
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.name.setTextColor(Color.parseColor("#1E293B"));
            holder.description.setTextColor(Color.parseColor("#64748B"));
        }

        // Click listeners
        holder.cardView.setOnClickListener(view -> {
            view.animate()
                    .scaleX(0.97f)
                    .scaleY(0.97f)
                    .setDuration(100)
                    .withEndAction(() -> {
                        view.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100)
                                .start();
                    })
                    .start();

            listener.onItemClicked(model);
        });

        holder.btnFavorite.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                ((MainActivity) context).onFavoriteClicked(model);
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
        public ImageButton btnFavorite;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            age = itemView.findViewById(R.id.text_age);
            description = itemView.findViewById(R.id.text_description);
            nameInitial = itemView.findViewById(R.id.text_name_initial);
            cardView = itemView.findViewById(R.id.unique_item_container);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
        }
    }
}