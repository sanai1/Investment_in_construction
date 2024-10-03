package com.example.investmentinconstruction.AdapterState;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investmentinconstruction.R;

import java.util.List;

public class ConstructionAdapter extends RecyclerView.Adapter<ConstructionAdapter.ViewHolder> {

    public interface OnStateHouseClickListener {

        void onStateHouseClick(String tag, ConstructionState homeState, int position);
    }

    private final List<ConstructionState> constructionStateList;
    private final OnStateHouseClickListener onStateHouseClickListener;

    public ConstructionAdapter(Context context, List<ConstructionState> constructionStateList, OnStateHouseClickListener onStateHouseClickListener) {
        this.constructionStateList = constructionStateList;
        this.onStateHouseClickListener = onStateHouseClickListener;
    }

    @NonNull
    @Override
    public ConstructionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConstructionAdapter.ViewHolder holder, int position) {
        holder.update(constructionStateList.get(position));
        holder.click(position);
    }

    @Override
    public int getItemCount() {
        return constructionStateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewType, textViewProgress;
        private ImageView picture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            picture = itemView.findViewById(R.id.imagePicture);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewProgress = itemView.findViewById(R.id.textViewProgress);
        }

        public void click(int position) {
            textViewProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStateHouseClickListener.onStateHouseClick(constructionStateList.get(position).getCid(), constructionStateList.get(position), position);
                }
            });
        }

        public void update(ConstructionState constructionState) {
            textViewType.setText(constructionState.getType() + " (" + constructionState.getCid() + ")");
            textViewProgress.setText(constructionState.getProgressBuild());
            picture.setImageResource(constructionState.getPicture());
        }
    }
}