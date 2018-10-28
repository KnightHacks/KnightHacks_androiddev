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

public class HorizontalSectionCard_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Integer> mViewTypeList;
    private ArrayList<String> mSubSectionTitleList;
    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSideSubtitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardFirstTextTagList;
    private ArrayList<String> mCardSecondTextTagList;
    private ArrayList<String> mCardBodyList;
    private ArrayList<String> mCardTimestampList;
    private ArrayList<String> mCardFooterList;

    private Context mContext;

    public HorizontalSectionCard_RecyclerViewAdapter(Context mContext,
                                                     ArrayList<Integer> mViewTypeList,
                                                     ArrayList<String> mSubSectionTitleList,
                                                     ArrayList<String> mCardImageList,
                                                     ArrayList<String> mCardTitleList,
                                                     ArrayList<String> mCardSideSubtitleList,
                                                     ArrayList<String> mCardSubtitleList,
                                                     ArrayList<String> mCardFirstTextTagList,
                                                     ArrayList<String> mCardSecondTextTagList,
                                                     ArrayList<String> mCardBodyList,
                                                     ArrayList<String> mCardTimestampList,
                                                     ArrayList<String> mCardFooterList) {
        this.mContext = mContext;
        this.mViewTypeList = mViewTypeList;
        this.mSubSectionTitleList = mSubSectionTitleList;
        this.mCardImageList = mCardImageList;
        this.mCardTitleList = mCardTitleList;
        this.mCardSideSubtitleList = mCardSideSubtitleList;
        this.mCardSubtitleList = mCardSubtitleList;
        this.mCardFirstTextTagList = mCardFirstTextTagList;
        this.mCardSecondTextTagList = mCardSecondTextTagList;
        this.mCardBodyList = mCardBodyList;
        this.mCardTimestampList = mCardTimestampList;
        this.mCardFooterList = mCardFooterList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ContentViewHolder.VIEW_TYPE:
                return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_section_card, parent, false));
            case TitleViewHolder.VIEW_TYPE:
                return new TitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_section_title_card, parent, false));
            default:
                return new TitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_section_title_card, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mViewTypeList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case ContentViewHolder.VIEW_TYPE:
                setContentViewHolderAtPosition((ContentViewHolder) holder, position);
                break;
            case TitleViewHolder.VIEW_TYPE:
                setTitleViewHolderAtPosition((TitleViewHolder) holder, position);
                break;
            default:
                break;
        }
    }

    private int getNumOfViewTypeUntilPosition(int type, int position) {
        int numOfViewType = 0;
        int len = mViewTypeList.size();

        for (int i = 0; i < position && i < len; i++) {
            if (mViewTypeList.get(i) == type) {
                numOfViewType++;
            }
        }

        return numOfViewType;
    }

    private void setTitleViewHolderAtPosition(TitleViewHolder holder, int position) {
        int numOfViewType = getNumOfViewTypeUntilPosition(TitleViewHolder.VIEW_TYPE, position);

        if (numOfViewType < mSubSectionTitleList.size()) {
            holder.mTitle.setText(mSubSectionTitleList.get(numOfViewType));
        } else {
            holder.mTitle.setVisibility(View.GONE);
        }
    }

    private void setContentViewHolderAtPosition(ContentViewHolder holder, int position) {
        int numOfViewType = getNumOfViewTypeUntilPosition(ContentViewHolder.VIEW_TYPE, position);

        if (numOfViewType < mCardImageList.size()) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(mCardImageList.get(numOfViewType))
                    .into(holder.mCardImage);
        } else {
            holder.mCardImage.setVisibility(View.GONE);
        }

        if (numOfViewType < mCardTitleList.size()) {
            holder.mCardTitle.setText(mCardTitleList.get(numOfViewType));
        } else {
            holder.mCardTitle.setVisibility(View.GONE);
        }

        if (numOfViewType < mCardSideSubtitleList.size()) {
            holder.mCardSideSubtitle.setText(mCardSideSubtitleList.get(numOfViewType));
        } else {
            holder.mCardSideSubtitle.setVisibility(View.GONE);
        }

        if (numOfViewType < mCardSubtitleList.size()) {
            holder.mCardSubtitle.setText(mCardSubtitleList.get(numOfViewType));
        } else {
            holder.mCardSubtitle.setVisibility(View.GONE);
        }

        if (numOfViewType < mCardFirstTextTagList.size()) {
            holder.mCardFirstTextTagSubtitle.setText(mCardFirstTextTagList.get(numOfViewType));
        } else {
            holder.mCardFirstTextTagSubtitle.setVisibility(View.GONE);
        }

        if (numOfViewType < mCardSecondTextTagList.size()) {
            holder.mCardSecondTextTagSubtitle.setText(mCardSecondTextTagList.get(numOfViewType));
        } else {
            holder.mCardSecondTextTagSubtitle.setVisibility(View.GONE);
        }

        if (numOfViewType < mCardBodyList.size()) {
            holder.mCardBody.setText(mCardBodyList.get(numOfViewType));
        } else {
            holder.mCardBody.setVisibility(View.GONE);
        }

        if (numOfViewType < mCardTimestampList.size()) {
            holder.mCardTimestamp.setText(mCardTimestampList.get(numOfViewType));
        } else {
            holder.mCardTimestamp.setVisibility(View.GONE);
        }

        if (numOfViewType < mCardFooterList.size()) {
            holder.mCardFooter.setText(mCardFooterList.get(numOfViewType));
        } else {
            holder.mCardFooter.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mViewTypeList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView mCardImage;
        TextView mCardTitle;
        TextView mCardSideSubtitle;
        TextView mCardSubtitle;
        TextView mCardFirstTextTagSubtitle;
        TextView mCardSecondTextTagSubtitle;
        TextView mCardBody;
        TextView mCardTimestamp;
        TextView mCardFooter;

        public static final int VIEW_TYPE = 1;

        public ContentViewHolder(View itemView) {
            super(itemView);
            this.mCardView = itemView.findViewById(R.id.horizontal_section_card_view);
            this.mCardImage = itemView.findViewById(R.id.horizontal_section_card_image);
            this.mCardTitle = itemView.findViewById(R.id.horizontal_section_card_title);
            this.mCardSideSubtitle = itemView.findViewById(R.id.horizontal_section_card_side_subtitle);
            this.mCardSubtitle = itemView.findViewById(R.id.horizontal_section_card_subtitle);
            this.mCardFirstTextTagSubtitle = itemView.findViewById(R.id.horizontal_section_card_first_tag_subtitle);
            this.mCardSecondTextTagSubtitle = itemView.findViewById(R.id.horizontal_section_card_second_tag_subtitle);
            this.mCardBody = itemView.findViewById(R.id.horizontal_section_card_body);
            this.mCardTimestamp = itemView.findViewById(R.id.horizontal_section_card_timestamp);
            this.mCardFooter = itemView.findViewById(R.id.horizontal_section_card_footer);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTitle;

        public static final int VIEW_TYPE = 2;

        public TitleViewHolder(View itemView) {
            super(itemView);
            this.mCardView = itemView.findViewById(R.id.sub_section_title_card_view);
            this.mTitle = itemView.findViewById(R.id.sub_section_title_card_title);
        }
    }
}
