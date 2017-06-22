package com.baranov.translator.ui.adapters;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baranov.translator.R;
import com.baranov.translator.model.Favorite;
import com.baranov.translator.ui.InterfaceRemover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Baranov on 22.06.2017 to Translator.
 */

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder> {

    private ArrayList<Favorite> items = new ArrayList<>();
    private ArrayList<Favorite> itemsPendingRemoval = new ArrayList<>();
    private boolean undoOn;
    private static final int PENDING_REMOVAL_TIMEOUT = 2000;
    private Handler handler = new Handler();
    private HashMap<Favorite, Runnable> pendingRunnables = new HashMap<>();
    private InterfaceRemover mInterfaceRemover;

    public FavoriteListAdapter(InterfaceRemover interfaceRemover) {
        mInterfaceRemover = interfaceRemover;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Favorite item = items.get(position);
        if (itemsPendingRemoval.contains(item)) {
            holder.itemView.setBackgroundColor(Color.RED);
            holder.undoButton.setVisibility(View.VISIBLE);
            holder.undoButton.setOnClickListener(v -> {
                Runnable pendingRemovalRunnable = pendingRunnables.get(item);
                pendingRunnables.remove(item);
                if (pendingRemovalRunnable != null)
                    handler.removeCallbacks(pendingRemovalRunnable);
                itemsPendingRemoval.remove(item);
                notifyItemChanged(items.indexOf(item));
            });
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.textOriginal.setText(item.getOriginal());
            holder.textTranslate.setText(item.getTranslate());
            holder.undoButton.setVisibility(View.GONE);
            holder.undoButton.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addAll(List<Favorite> items) {
        final int size = this.items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(size, items.size());
    }

    public void pendingRemoval(int position) {
        final Favorite item = items.get(position);
        if (!itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.add(item);
            notifyItemChanged(position);
            Runnable pendingRemovalRunnable = () -> remove(items.indexOf(item));
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(item, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        Favorite item = items.get(position);
        if (itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.remove(item);
        }
        if (items.contains(item)) {
            items.remove(position);
            notifyItemRemoved(position);
        }
        mInterfaceRemover.deletePos(item);
    }

    public boolean isPendingRemoval(int position) {
        Favorite item = items.get(position);
        return itemsPendingRemoval.contains(item);
    }

    public void setUndoOn(boolean undoOn) {
        this.undoOn = undoOn;
    }

    public boolean isUndoOn() {
        return undoOn;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemOriginal)
        TextView textOriginal;
        @BindView(R.id.itemTranslate)
        TextView textTranslate;
        @BindView(R.id.undo_button)
        Button undoButton;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
