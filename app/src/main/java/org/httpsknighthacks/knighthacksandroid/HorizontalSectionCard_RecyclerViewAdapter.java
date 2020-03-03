package org.httpsknighthacks.knighthacksandroid;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HorizontalSectionCard_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Integer> mViewTypeList;
    private ArrayList<String> mSubSectionTitleList;
    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSideSubtitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardTagSubtitleList;
    private ArrayList<String> mCardBodyList;
    private ArrayList<String> mCardTimestampList;
    private ArrayList<String> mCardFooterList;
    private ArrayList<String> mCardMapEventList;
    private String tag;

    private Context mContext;
    public HorizontalSectionCard_RecyclerViewAdapter(Context mContext,
                                                     ArrayList<Integer> mViewTypeList,
                                                     ArrayList<String> mSubSectionTitleList,
                                                     ArrayList<String> mCardImageList,
                                                     ArrayList<String> mCardTitleList,
                                                     ArrayList<String> mCardSideSubtitleList,
                                                     ArrayList<String> mCardSubtitleList,
                                                     ArrayList<String> mCardTagSubtitleList,
                                                     ArrayList<String> mCardBodyList,
                                                     ArrayList<String> mCardTimestampList,
                                                     ArrayList<String> mCardFooterList,
                                                     ArrayList<String> mCardMapEventList,
                                                     String tag) {
        this.mContext = mContext;
        this.mViewTypeList = mViewTypeList;
        this.mSubSectionTitleList = mSubSectionTitleList;
        this.mCardImageList = mCardImageList;
        this.mCardTitleList = mCardTitleList;
        this.mCardSideSubtitleList = mCardSideSubtitleList;
        this.mCardSubtitleList = mCardSubtitleList;
        this.mCardTagSubtitleList = mCardTagSubtitleList;
        this.mCardBodyList = mCardBodyList;
        this.mCardTimestampList = mCardTimestampList;
        this.mCardFooterList = mCardFooterList;
        this.mCardMapEventList = mCardMapEventList;
        this.tag = tag;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ContentViewHolder.VIEW_TYPE:
                return new ContentViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.horizontal_section_card, parent, false));
            case TitleViewHolder.VIEW_TYPE:
                return new TitleViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sub_section_title_card, parent, false));
            default:
                return new TitleViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sub_section_title_card, parent, false));
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

    private void setContentViewHolderAtPosition(final ContentViewHolder holder, int position) {
        int numOfViewType = getNumOfViewTypeUntilPosition(ContentViewHolder.VIEW_TYPE, position);

        final StorageReference reference = FirebaseStorage.getInstance().getReference();
        if (numOfViewType < mCardImageList.size()) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(reference.child(mCardImageList.get(numOfViewType)))
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

        if (numOfViewType < mCardTagSubtitleList.size()) {
            holder.mCardTagSubtitle.setText(mCardTagSubtitleList.get(numOfViewType));
        } else {
            holder.mCardTagSubtitle.setVisibility(View.GONE);
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

        if (!this.tag.equals(Sponsors.TAG)) {
            holder.mCardView.setOnClickListener(v -> {

                if (mCardMapEventList.get(position) != null) {
                    Glide.with(mContext)
                            .asBitmap()
                            .load(reference.child(mCardMapEventList.get(position)))
                            .into(holder.mCardMapEvent);
                } else {
                    Toast.makeText(mContext, "No Map at the current moment.", Toast.LENGTH_SHORT).show();
                    return;
                }

                holder.showPopUpMapImage();
            });
        }
    }

    @Override
    public int getItemCount() {
        return mViewTypeList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView mCardImage;
        ImageView mCardMapEvent;
        TextView mCardTitle;
        TextView mCardSideSubtitle;
        TextView mCardSubtitle;
        TextView mCardTagSubtitle;
        TextView mCardBody;
        TextView mCardTimestamp;
        TextView mCardFooter;
        Dialog dialog;

        public static final int VIEW_TYPE = 1;

        public ContentViewHolder(final View itemView) {
            super(itemView);

            View gridLayout = itemView.findViewById(R.id.horizontal_section_grid_view);

            if (!tag.equals(Sponsors.TAG)) {
                dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.activity_event_map);

                this.mCardMapEvent = dialog.findViewById(R.id.mapImage);
            }

            this.mCardView = itemView.findViewById(R.id.horizontal_section_card_view);
            this.mCardImage = itemView.findViewById(R.id.horizontal_section_card_image);
            this.mCardTitle = itemView.findViewById(R.id.horizontal_section_card_title);
            this.mCardSideSubtitle = itemView.findViewById(R.id.horizontal_section_card_side_subtitle);
            this.mCardSubtitle = itemView.findViewById(R.id.horizontal_section_card_subtitle);
            this.mCardTagSubtitle = gridLayout.findViewById(R.id.horizontal_section_card_tag_subtitle);
            this.mCardBody = itemView.findViewById(R.id.horizontal_section_card_body);
            this.mCardTimestamp = itemView.findViewById(R.id.horizontal_section_card_timestamp);
            this.mCardFooter = itemView.findViewById(R.id.horizontal_section_card_footer);
        }

        private void showPopUpMapImage() {
            Button closeMapBtn = dialog.findViewById(R.id.closeMapBtn);

            closeMapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
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
