package com.mv.badrecs_badmintonrecords;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeRecyclerViewHolder> {

    private List<String> titles;
    private List<Long> times;
    private List<String> scores;
    private List<Integer> wins;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    HomeRecyclerAdapter(Context context, List<String> titles, List<Long> times, List<String> scores, List<Integer> wins) {
        this.mInflater = LayoutInflater.from(context);
        this.titles = titles;
        this.times = times;
        this.scores = scores;
        this.wins = wins;
    }

    // inflates the row layout from xml when needed
    @Override
    public HomeRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.home_recyclerrow, parent, false);
        return new HomeRecyclerViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(HomeRecyclerViewHolder holder, int position) {
        String animal = titles.get(position);
        holder.recyclerRowTitle.setText(animal);
        Long selectedTime = times.get(position);
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(selectedTime), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        holder.recyclerRowDate.setText(date.format(formatter));
        holder.recyclerRowScore.setText(scores.get(position));
        if(wins.get(position) == MatchClass.MATCH_LOSE){ // LOSS
            holder.recyclerHomeCardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF7979")));
        }
        else if(wins.get(position) == MatchClass.MATCH_WIN){ // WIN
            holder.recyclerHomeCardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#96FF79")));
            Log.d("QWER", position + " Match Win!");
        }
        else { // ELSE
            holder.recyclerHomeCardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CACACA")));
        }
        Log.d("QWER", "Title : " + titles.get(position));

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return titles.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class HomeRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recyclerRowTitle;
        TextView recyclerRowDate;
        TextView recyclerRowScore;
        CardView recyclerHomeCardView;

        HomeRecyclerViewHolder(View itemView) {
            super(itemView);
            recyclerRowTitle = itemView.findViewById(R.id.recyclerRowTitle);
            recyclerRowDate = itemView.findViewById(R.id.recyclerRowDate);
            recyclerRowScore = itemView.findViewById(R.id.recyclerRowScore);
            recyclerHomeCardView = itemView.findViewById(R.id.recyclerHomeCardView);
            //recyclerRowTitle.setMovementMethod(new ScrollingMovementMethod());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return titles.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
