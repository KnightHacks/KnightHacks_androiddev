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

import java.util.ArrayList;

public class VerticalSectionCard_RecyclerViewAdapter extends RecyclerView.Adapter<VerticalSectionCard_RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSubtitleList;

    private Context mContext;

    public VerticalSectionCard_RecyclerViewAdapter(Context mContext,
                                                     ArrayList<String> mCardImageList,
                                                     ArrayList<String> mCardTitleList,
                                                     ArrayList<String> mCardSubtitleList) {
        this.mContext = mContext;
        this.mCardImageList = mCardImageList;
        this.mCardTitleList = mCardTitleList;
        this.mCardSubtitleList = mCardSubtitleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_section_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Only load parts of the generic vertical card if needed

        if (position < mCardImageList.size()) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(mCardImageList.get(position))
                    .into(holder.mCardImage);
        } else {
            holder.mCardImage.setVisibility(View.GONE);
        }

        if (position < mCardTitleList.size()) {
            holder.mCardTitle.setText(mCardTitleList.get(position));
        } else {
            holder.mCardTitle.setVisibility(View.GONE);
        }

        if (position < mCardSubtitleList.size()) {
            holder.mCardSubtitle.setText(mCardSubtitleList.get(position));
        } else {
            holder.mCardSubtitle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(mCardImageList.size(), Math.max(mCardTitleList.size(), mCardSubtitleList.size()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        ImageView mCardImage;
        TextView mCardTitle;
        TextView mCardSubtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mCardView = itemView.findViewById(R.id.vertical_section_card_view);
            this.mCardImage = itemView.findViewById(R.id.vertical_section_card_image);
            this.mCardTitle = itemView.findViewById(R.id.vertical_section_card_title);
            this.mCardSubtitle = itemView.findViewById(R.id.vertical_section_card_subtitle);
        }
    }

}
