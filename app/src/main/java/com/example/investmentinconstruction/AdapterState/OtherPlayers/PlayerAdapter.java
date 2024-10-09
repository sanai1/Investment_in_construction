package com.example.investmentinconstruction.AdapterState.OtherPlayers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investmentinconstruction.R;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private final List<PlayerState> playerStateList;

    public PlayerAdapter(List<PlayerState> playerStateList) {
        this.playerStateList = playerStateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.update(playerStateList.get(position));
    }

    @Override
    public int getItemCount() {
        return playerStateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewType, textViewProgress;
        private ImageView picture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            picture = itemView.findViewById(R.id.imagePicture);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewProgress = itemView.findViewById(R.id.textViewProgress);
        }

        public void update(PlayerState playerState) {
            if (playerState.getType().equals("Brick") || playerState.getType().equals("Panel") || playerState.getType().equals("Monolithic")) {
                textViewType.setText(playerState.getType() + " (" + playerState.getCid() + ")" + " sold:" + playerState.getSoldApartment());
            } else {
                textViewType.setText(playerState.getType() + " (" + playerState.getCid() + ")");
            }
            textViewProgress.setText(playerState.getProgressBuild());
            picture.setImageResource(playerState.getPicture());
        }
    }
}