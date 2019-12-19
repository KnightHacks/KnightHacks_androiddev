package org.httpsknighthacks.knighthacksandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Homepage_RecyclerViewAdapter extends RecyclerView.Adapter<Homepage_RecyclerViewAdapter.ViewHolder> {

    // Properties:
    private ArrayList<String> mTextList = new ArrayList<>();
    private ArrayList<Drawable> mImages = new ArrayList<>();
    private ArrayList<Integer> mBgColors = new ArrayList<>();
    private ArrayList<Class> activityList = new ArrayList<>();

    private Context mContext;

    public Homepage_RecyclerViewAdapter(Context mContext,
                                        ArrayList<String> mTexts,
                                        ArrayList<Drawable> mImages,
                                        ArrayList<Integer> mBgColors,
                                        ArrayList<Class> activityList
                                        ) {
        this.mTextList = mTexts;
        this.mImages = mImages;
        this.mBgColors = mBgColors;
        this.mContext = mContext;
        this.activityList = activityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        activityList.add(Schedule.class);

        Glide.with(mContext)
                .asDrawable()
                .load(mImages.get(position))
                .into(holder.mImageView);

        holder.mCardView.setCardBackgroundColor((mBgColors.get(position)));
        holder.mTextView.setText(mTextList.get(position));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(mContext, activityList.get(position));
                mContext.startActivity(newActivity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        CardView mCardView;


        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.homepage_list_item_img);
            mTextView = itemView.findViewById(R.id.homepage_list_item_text_view);
            mCardView = itemView.findViewById(R.id.list_item_card_view);
        }
    }

}
