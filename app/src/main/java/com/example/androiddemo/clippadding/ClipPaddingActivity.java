package com.example.androiddemo.clippadding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class ClipPaddingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> srcList = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, ClipPaddingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_padding);

        for (int i = 0; i < 100; i++) {
            srcList.add(i + "");
        }

        recyclerView = findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ClipAdapter());
    }

    class ClipAdapter extends RecyclerView.Adapter<ClipHolder> {
        @NonNull
        @Override
        public ClipHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(Color.RED);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(Color.GREEN);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80);
            layoutParams.bottomMargin = 10;
            textView.setLayoutParams(layoutParams);
            ClipHolder clipHolder = new ClipHolder(textView);
            return clipHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ClipHolder holder, int position) {
            String name = srcList.get(position);
            ((TextView) holder.itemView).setText(name);

        }

        @Override
        public int getItemCount() {
            return srcList.size();
        }
    }

    class ClipHolder extends RecyclerView.ViewHolder {
        public ClipHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
