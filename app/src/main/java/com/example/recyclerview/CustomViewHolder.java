package com.example.recyclerview;

// BẮT BUỘC: Thêm các import sau
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View; // Import cho View
import android.widget.TextView; // Import cho TextView
import androidx.cardview.widget.CardView; // Import cho CardView

public class CustomViewHolder extends RecyclerView.ViewHolder {

    public TextView name, age, description;
    public CardView cardView; // Hoặc kiểu layout container chính của bạn

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView); // Gọi constructor của lớp cha

        // Ánh xạ các views
        name = itemView.findViewById(R.id.text_name);
        age = itemView.findViewById(R.id.text_age);
        description = itemView.findViewById(R.id.text_description);
        cardView = itemView.findViewById(R.id.unique_item_container); // ID container để bắt sự kiện
    }
}