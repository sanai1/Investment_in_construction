package com.example.investmentinconstruction.AdapterState.FinalGame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investmentinconstruction.R;

import java.util.List;

public class FinalGameAdapter extends RecyclerView.Adapter<FinalGameAdapter.ViewHolder> {

    private List<FinalGameState> pairList;

    public FinalGameAdapter(List<FinalGameState> pairList) {
        this.pairList = pairList;
    }

    @NonNull
    @Override
    public FinalGameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.final_game_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinalGameAdapter.ViewHolder holder, int position) {
        holder.update(pairList.get(position));
    }

    @Override
    public int getItemCount() {
        return pairList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName, textViewProfit;
        private ImageView picture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            picture = itemView.findViewById(R.id.imageViewPictureFinal);
            textViewName = itemView.findViewById(R.id.textViewNameFinal);
            textViewProfit = itemView.findViewById(R.id.textViewProfit);
        }

        public void update(FinalGameState finalGameState) {
            textViewName.setText(finalGameState.getName());
            textViewProfit.setText(String.valueOf(finalGameState.getFullProfit()));
            picture.setImageResource(finalGameState.getPicture());
        }
    }
}