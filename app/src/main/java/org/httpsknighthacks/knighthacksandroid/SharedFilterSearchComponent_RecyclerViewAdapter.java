package org.httpsknighthacks.knighthacksandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.httpsknighthacks.knighthacksandroid.Models.Enums.ScheduleEventTypes;
import org.httpsknighthacks.knighthacksandroid.Models.ScheduleEvent;
import org.httpsknighthacks.knighthacksandroid.Resources.SearchFilterListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SharedFilterSearchComponent_RecyclerViewAdapter extends
        RecyclerView.Adapter<SharedFilterSearchComponent_RecyclerViewAdapter.ViewHolder> {
    // Properties:
    private ArrayList<String> mTextList = new ArrayList<>();
    private ArrayList<String> mImageList = new ArrayList<>();
    private ArrayList<ScheduleEventTypes> mEventTypes;
    private Context mContext;
    private ViewHolder previousHolder;
    private SearchFilterListener mListener;

    public SharedFilterSearchComponent_RecyclerViewAdapter(Context mContext,
                                                           ArrayList<String> mTextList,
                                                           ArrayList<String> mImageList,
                                                           ArrayList<ScheduleEventTypes> mEventTypes,
                                                           SearchFilterListener mListener) {
        this.mContext = mContext;
        this.mTextList = mTextList;
        this.mImageList = mImageList;
        this.mEventTypes = mEventTypes;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public SharedFilterSearchComponent_RecyclerViewAdapter.ViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.shared_filter_search_component_list_item,
                parent, false));
    }

    public void setOnBindViewHolder(SharedFilterSearchComponent_RecyclerViewAdapter.ViewHolder holder, int position, View.OnClickListener onClickListener) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImageList.get(position))
                .into(holder.mImageView);

        holder.mTextView.setText(mTextList.get(position));
        holder.setEventType(mEventTypes.get(position));

        if(position == (mImageList.size()-1)) {
            holder.mImageView.setBackgroundResource(R.drawable.filter_search_component_list_item_round_border);
            setPreviousHolder(holder);
        }

        holder.mCardView.setOnClickListener(onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final SharedFilterSearchComponent_RecyclerViewAdapter.ViewHolder holder, final int position) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousHolder.mImageView.setBackgroundResource(0);
                holder.mImageView.setBackgroundResource(R.drawable.filter_search_component_list_item_round_border);

                setPreviousHolder(holder);
                mListener.setSearchFilters(holder, position);
            }
        };

        setOnBindViewHolder(holder, position, onClickListener);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTextView;
        CardView mCardView;
        ScheduleEventTypes mEventType;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.shared_filter_search_img_view);
            mTextView = itemView.findViewById(R.id.shared_filter_search_text_view);
            mCardView = itemView.findViewById(R.id.shared_filter_search_parent_card_view_container);
        }

        public void setEventType(ScheduleEventTypes type) {
            mEventType = type;
        }
    }

    public void setPreviousHolder(ViewHolder previousHolder) {
        this.previousHolder = previousHolder;
    }
}
