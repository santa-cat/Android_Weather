package com.example.santa.weather;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.media.audiofx.LoudnessEnhancer;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by santa on 16/7/28.
 */
public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.BasicHolder> implements BasicInfoItemTouchHelperCallback.onDragAndMoveListener {

    private Context mContext;
    private ArrayList<BasicInfo> mData;
    private int mCheckedItem = 0;
    private BasicHolder mHolder;
    private ChooseListener mChooseListener;


    public BasicListAdapter(Context context, ArrayList<BasicInfo> list, ChooseListener listener) {
        mContext = context;
        mData = list;
        mChooseListener = listener;
    }

    public void addcity(BasicInfo basicInfo) {
        if (null == basicInfo) {
            return;
        }

        for (int i = 0 ; i<mData.size() ; i++) {
            if(mData.get(i).getCity().equals(basicInfo.getCity())) {
                return;
            }
        }

        mData.add(basicInfo);
        notifyItemInserted(mData.size()-1);
    }

    @Override
    public BasicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_basicinfo, parent, false);
        return new BasicHolder(view);
    }

    @Override
    public void onBindViewHolder(final BasicHolder holder, final int position) {
        Log.d("DEBUG", "holder = "+holder);
        holder.tmp.setText(mData.get(position).getNowFl());
        holder.city.setText(mData.get(position).getCity());
        if (mCheckedItem == position) {
            holder.tmp.setVisibility(View.VISIBLE);
            holder.layout.setBackgroundColor(Utils.getBackgroundColorByCode(mContext, mData.get(position).getCode()));
            holder.city.setTextColor(Utils.getColorByCode(mContext, mData.get(position).getCode()));
            holder.city.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            holder.tmp.setTextColor(Utils.getColorByCode(mContext, mData.get(position).getCode()));
            holder.weather.setImageDrawable(Utils.getDrawableCheckedByWeather(mContext, mData.get(position).getNowTxt()));
            updateAllInfo(mData.get(position).getCity());
        } else {
            holder.tmp.setVisibility(View.GONE);
            holder.layout.setBackgroundColor(Color.rgb(0x76 - 6 * position, 0xB1 - 10 * position, 0xD4 - 12 * position));
            holder.city.setTextColor(Color.WHITE);
            holder.weather.setImageDrawable(Utils.getDrawableByWeather(mContext, mData.get(position).getNowTxt()));
            holder.city.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);

        }
        ((SwipeLayout)holder.layout).setListener(new SwipeLayout.OperatorListener() {
            @Override
            public void onDelete() {
                deleteItem(holder.getAdapterPosition());

            }

            @Override
            public void onEdit() {

            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(mCheckedItem);
                mCheckedItem = position;
                notifyItemChanged(position);
            }
        });
    }

    private void updateAllInfo(String city) {
        if (null != mChooseListener) {
            mChooseListener.onUpdate(city);
        }

    }


    private void deleteItem(int position) {
        if (null != mChooseListener) {
            mChooseListener.onDelete(mData.get(position).getCity());
        }
        notifyItemRemoved(position);
        mData.remove(position);
        notifyItemRangeChanged(position, mData.size()-1);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public boolean onItemDrag(int fromPosition, int toPosition) {
        if(fromPosition == mCheckedItem) {
            mCheckedItem = toPosition;
        } else if(toPosition == mCheckedItem) {
            mCheckedItem = fromPosition;
        }
        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
//        notifyItemChanged(fromPosition);

        Log.d("DEBUG", "fromPosition = "+fromPosition);
        Log.d("DEBUG", "toPosition = "+toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public class BasicHolder extends RecyclerView.ViewHolder implements BasicInfoItemTouchHelperCallback.OnDragVHListener{
        private ViewGroup layout;
        private TextView city;
        private ImageView weather;
        private TextView tmp;
        private AnimatorSet upSet, downSet;

        public BasicHolder(View itemView) {
            super(itemView);
            layout = (ViewGroup) itemView.findViewById(R.id.layout);
            city= (TextView) itemView.findViewById(R.id.text_city);
            weather= (ImageView) itemView.findViewById(R.id.image_weather);
            tmp = (TextView) itemView.findViewById(R.id.text_temperature);

            //创建动画
            ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f);
            ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f);
            ObjectAnimator upAnim = ObjectAnimator.ofFloat(itemView, "translationZ", 10);
//            ObjectAnimator upColor = ObjectAnimator.ofArgb(itemView, "backgroundColor", Color.LTGRAY);
            upSet = new AnimatorSet();
            upSet.playSequentially(scaleXAnim, scaleYAnim, upAnim);
            upSet.setDuration(100);
            upSet.setInterpolator(new DecelerateInterpolator());

            ObjectAnimator downAnim = ObjectAnimator.ofFloat(itemView, "translationZ", 0);
            ObjectAnimator scaleXDownAnim = ObjectAnimator.ofFloat(itemView, "scaleX", 1.0f);
            ObjectAnimator scaleYDownAnim = ObjectAnimator.ofFloat(itemView, "scaleY", 1.0f);
//            ObjectAnimator downColor = ObjectAnimator.ofArgb(itemView, "backgroundColor", 0);
            downSet = new AnimatorSet();
            downSet.playSequentially(scaleXDownAnim, scaleYDownAnim, downAnim);
            downSet.setDuration(100);
            downSet.setInterpolator(new DecelerateInterpolator());

        }

        @Override
        public void onItemFinish() {
            downSet.start();
//            notifyItemChanged(getAdapterPosition());
            notifyDataSetChanged();
        }

        @Override
        public void onItemSelected() {
            upSet.start();
        }
    }



    public interface ChooseListener{
        void onUpdate(String city);
        void onDelete(String city);
    }
}
