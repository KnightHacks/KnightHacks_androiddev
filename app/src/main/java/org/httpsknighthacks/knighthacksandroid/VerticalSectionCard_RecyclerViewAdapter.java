package org.httpsknighthacks.knighthacksandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VerticalSectionCard_RecyclerViewAdapter extends RecyclerView.Adapter<VerticalSectionCard_RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSubtitleList;
    private Context mContext;
    private boolean[] isVisible;

    public VerticalSectionCard_RecyclerViewAdapter(Context mContext,
                                                     ArrayList<String> mCardImageList,
                                                     ArrayList<String> mCardTitleList,
                                                     ArrayList<String> mCardSubtitleList) {
        this.mContext = mContext;
        this.mCardImageList = mCardImageList;
        this.mCardTitleList = mCardTitleList;
        this.mCardSubtitleList = mCardSubtitleList;
        isVisible = new boolean[mCardSubtitleList.size()];
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

            // Use the resource id to determine what drawables (if any) need to load
            int id = mContext.getResources().
                    getIdentifier(mCardImageList.get(position), "drawable", mContext.getPackageName());

            // The FAQ cards will have some differences in UI functionality since it needs to expand and collapse
            if(mCardImageList.get(position).equals("ic_faq_plus")) {
                setUpFaqIcon(id, holder, position);
            }

            else {
                Glide.with(mContext)
                        .asBitmap()
                        .load(mCardImageList.get(position))
                        .into(holder.mCardImage);
            }

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

    private void setUpFaqIcon(int id, final ViewHolder holder, int position) {
        // Without this, the answers will display upon starting the activity, which we don't want
        if(!isVisible[position])
        {
            holder.mCardSubtitle.setVisibility(View.GONE);
            isVisible[position] = true;
        }
        Glide.with(mContext)
                .asBitmap()
                .load(id)
                .into(holder.mCardImage);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(120, 120);
        holder.mCardImage.setLayoutParams(params);

        // Expand or collapse card when icon is pressed
        holder.mCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mCardSubtitle.getVisibility() == View.GONE) {
                    holder.mCardSubtitle.setVisibility(View.VISIBLE);
                    holder.mCardImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_faq_minimize));
                }

                else {
                    holder.mCardSubtitle.setVisibility(View.GONE);
                    holder.mCardImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_faq_plus));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Math.max(mCardImageList.size(), Math.max(mCardTitleList.size(), mCardSubtitleList.size()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
