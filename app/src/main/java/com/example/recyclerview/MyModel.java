package com.example.recyclerview;

public class MyModel {
    private String name;
    private int age;
    // Thêm trường khác (ví dụ: mô tả, ảnh) để layout độc đáo hơn
    private String description;

    // Constructor
    public MyModel(String name, int age, String description) {
        this.name = name;
        this.age = age;
        this.description = description;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }
}
