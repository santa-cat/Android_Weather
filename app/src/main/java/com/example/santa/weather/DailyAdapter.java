package com.example.santa.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/1.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyHolder> {
    private ArrayList<Daily> mData;
    private Context mContext;

    public DailyAdapter(Context context, ArrayList<Daily> list) {
        mContext = context;
        mData = list;
    }


    @Override
    public DailyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dailyforecast, parent, false);
        return new DailyHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyHolder holder, int position) {
        holder.time.setText(mData.get(position).getTime());
        holder.tmpHigh.setText(mData.get(position).getTmpHigh());
        holder.tmpLow.setText(mData.get(position).getTmpLow());
        holder.weather.setImageDrawable(Utils.getDrawableSmallByWeather(mContext, mData.get(position).getWeather()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class DailyHolder extends RecyclerView.ViewHolder{
        private TextView time;
        private ImageView weather;
        private TextView tmpHigh;
        private TextView tmpLow;

        public DailyHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.daily_time);
            tmpHigh = (TextView) itemView.findViewById(R.id.daily_tmphigh);
            tmpLow = (TextView) itemView.findViewById(R.id.daily_tmplow);
            weather = (ImageView) itemView.findViewById(R.id.daily_weather);
        }
    }
}
