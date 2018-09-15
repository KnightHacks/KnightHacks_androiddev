package org.httpsknighthacks.knighthacksandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.Constraints.TAG;

public class SharedFilterSearchComponent_RecyclerViewAdapter extends
        RecyclerView.Adapter<SharedFilterSearchComponent_RecyclerViewAdapter.ViewHolder> {
    // Properties:
    private ArrayList<String> mTextList = new ArrayList<>();
    private ArrayList<String> mImageList = new ArrayList<>();
    private Context mContext;

    public SharedFilterSearchComponent_RecyclerViewAdapter(Context mContext,
                                                           ArrayList<String> mTextList,
                                                           ArrayList<String> mImageList) {
        this.mContext = mContext;
        this.mTextList = mTextList;
        this.mImageList = mImageList;
    }

    @NonNull
    @Override
    public SharedFilterSearchComponent_RecyclerViewAdapter.ViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.shared_filter_search_component_list_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SharedFilterSearchComponent_RecyclerViewAdapter.ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImageList.get(position))
                .into(holder.mImageView);

        holder.mTextView.setText(mTextList.get(position));

        if(position == (mImageList.size()-1)) {
            holder.mImageView.setBackgroundResource(R.drawable.round_border);
        }
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.shared_filter_search_img_view);
            mTextView = itemView.findViewById(R.id.shared_filter_search_text_view);
        }
    }
}
