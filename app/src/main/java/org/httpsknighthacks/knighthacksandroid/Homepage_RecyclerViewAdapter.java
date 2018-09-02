package org.httpsknighthacks.knighthacksandroid;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;



public class Homepage_RecyclerViewAdapter extends RecyclerView.Adapter<Homepage_RecyclerViewAdapter.ViewHolder> {

    // Debugging =)
    private static final String TAG = "RecyclerViewAdapter";

    // Properties:
    private ArrayList<String> mTextList = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mBgColors = new ArrayList<>();
    private Context mContext;



    public Homepage_RecyclerViewAdapter(Context mContext,
                                        ArrayList<String> mTexts,
                                        ArrayList<String> mImageUrls,
                                        ArrayList<String> mBgColors) {
        this.mTextList = mTexts;
        this.mImageUrls = mImageUrls;
        this.mBgColors = mBgColors;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called this!");

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.mImageView);

        holder.mCardView.setCardBackgroundColor(Color.parseColor(mBgColors.get(position)));
        holder.mImageView.setBackgroundResource(R.color.Image);

        holder.mTextView.setText(mTextList.get(position));

        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Clicked this! Opening a new activity: " + mTextList.get(position));
                Toast.makeText(mContext, mTextList.get(position), Toast.LENGTH_SHORT).show();
                // TODO: Add more activities here, based on position/index in array list to open the new activity
            }
        });


    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
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
