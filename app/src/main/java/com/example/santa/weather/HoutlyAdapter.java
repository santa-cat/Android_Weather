package com.example.santa.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by santa on 16/8/1.
 */
public class HoutlyAdapter extends RecyclerView.Adapter<HoutlyAdapter.HourlyHolder> {
    private ArrayList<Hourly> mData;
    private Context mContext;

    public HoutlyAdapter(Context context, ArrayList<Hourly> list) {
        mContext = context;
        mData = list;
    }


    @Override
    public HourlyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hourlyforecast, parent, false);
        return new HourlyHolder(view);
    }

    @Override
    public void onBindViewHolder(HourlyHolder holder, int position) {
        holder.time.setText(mData.get(position).getTime());
        holder.tmp.setText(mData.get(position).getTmp());
        holder.weather.setImageDrawable(Utils.getDrawableSmallByWeather(mContext, mData.get(position).getWeather()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class HourlyHolder extends RecyclerView.ViewHolder{

        private TextView time;
        private TextView tmp;
        private ImageView weather;
        public HourlyHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.hourly_time);
            tmp = (TextView) itemView.findViewById(R.id.hourly_tmp);
            weather = (ImageView) itemView.findViewById(R.id.hourly_weather);
        }
    }
}
