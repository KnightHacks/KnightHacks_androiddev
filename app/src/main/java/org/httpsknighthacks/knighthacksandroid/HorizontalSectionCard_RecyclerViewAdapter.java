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

public class HorizontalSectionCard_RecyclerViewAdapter extends RecyclerView.Adapter<HorizontalSectionCard_RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSideSubtitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardBodyList;
    private ArrayList<String> mCardTimestampList;

    private Context mContext;

    public HorizontalSectionCard_RecyclerViewAdapter(Context mContext,
                                                     ArrayList<String> mCardImageList,
                                                     ArrayList<String> mCardTitleList,
                                                     ArrayList<String> mCardSideSubtitleList,
                                                     ArrayList<String> mCardSubtitleList,
                                                     ArrayList<String> mCardBodyList,
                                                     ArrayList<String> mCardTimestampList) {
        this.mContext = mContext;
        this.mCardImageList = mCardImageList;
        this.mCardTitleList = mCardTitleList;
        this.mCardSideSubtitleList = mCardSideSubtitleList;
        this.mCardSubtitleList = mCardSubtitleList;
        this.mCardBodyList = mCardBodyList;
        this.mCardTimestampList = mCardTimestampList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_section_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Only load parts of the generic horizontal card if needed

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

        if (position < mCardSideSubtitleList.size()) {
            holder.mCardSideSubtitle.setText(mCardSideSubtitleList.get(position));
        } else {
            holder.mCardSideSubtitle.setVisibility(View.GONE);
        }

        if (position < mCardSubtitleList.size()) {
            holder.mCardSubtitle.setText(mCardSubtitleList.get(position));
        } else {
            holder.mCardSubtitle.setVisibility(View.GONE);
        }

        if (position < mCardBodyList.size()) {
            holder.mCardBody.setText(mCardBodyList.get(position));
        } else {
            holder.mCardBody.setVisibility(View.GONE);
        }

        if (position < mCardTimestampList.size()) {
            holder.mCardTimestamp.setText(mCardTimestampList.get(position));
        } else {
            holder.mCardTimestamp.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(mCardImageList.size(), Math.max(mCardTitleList.size(), Math.max(mCardSideSubtitleList.size(), Math.max(mCardSubtitleList.size(), Math.max(mCardBodyList.size(), mCardTimestampList.size())))));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        ImageView mCardImage;
        TextView mCardTitle;
        TextView mCardSideSubtitle;
        TextView mCardSubtitle;
        TextView mCardBody;
        TextView mCardTimestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mCardView = itemView.findViewById(R.id.horizontal_section_card_view);
            this.mCardImage = itemView.findViewById(R.id.horizontal_section_card_image);
            this.mCardTitle = itemView.findViewById(R.id.horizontal_section_card_title);
            this.mCardSideSubtitle = itemView.findViewById(R.id.horizontal_section_card_side_subtitle);
            this.mCardSubtitle = itemView.findViewById(R.id.horizontal_section_card_subtitle);
            this.mCardBody = itemView.findViewById(R.id.horizontal_section_card_body);
            this.mCardTimestamp = itemView.findViewById(R.id.horizontal_section_card_timestamp);
        }
    }

}
