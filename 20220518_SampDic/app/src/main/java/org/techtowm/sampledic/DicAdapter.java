package org.techtowm.sampledic;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DicAdapter extends RecyclerView.Adapter<DicAdapter.ViewHolder>{

    ArrayList<Dic> items = new ArrayList<Dic>();

    @NonNull
    @Override
    public DicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.dic_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DicAdapter.ViewHolder viewHolder, int position) {
        Dic item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Dic item) {
        items.add(item);
    }

    public void setItems(ArrayList<Dic> items) {
        this.items = items;
    }

    public Dic getItem(int position) {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("tag", "makeRequest");
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }

        public void setItem(Dic item) {
            Log.d("tag", "makeRequest");
            textView.setText(item.id);
            textView2.setText(item.name);
        }
    }

}
