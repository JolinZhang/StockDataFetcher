package com.github.jolinzhang.stockdatafetcher;

/**
 * Created by Ru Zhang (rxz151130) on 11/7/16.
 * The recyclerView adapter class.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<String> head;
    ArrayList<ArrayList<String>> content;
    Context context;

    /**
     * Created by Ru Zhang (rxz151130) on 11/7/16.
     * The constructor method.
     */
    public RecyclerViewAdapter( ArrayList<String> head, ArrayList<ArrayList<String>> content, Context context){
        this.head = head;
        this.content = content;
        this.context = context;
    }

    /**
     * Created by Ru Zhang (rxz151130) on 11/7/16.
     * The method to create view holder.
     */
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * Created by Ru Zhang (rxz151130) on 11/7/16.
     * The constructor method.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position == 0){
            holder.date.setText(head.get(0));
            holder.open.setText(head.get(1));
            holder.high.setText(head.get(2));
            holder.low.setText(head.get(3));
            holder.close.setText(head.get(4));
            holder.volume.setText(head.get(5));
            holder.adjClose.setText(head.get(6));
        }else{
            holder.date.setText(content.get(position -1).get(0));
            holder.open.setText(content.get(position -1).get(1));
            holder.high.setText(content.get(position -1).get(2));
            holder.low.setText(content.get(position -1).get(3));
            holder.close.setText(content.get(position -1).get(4));
            holder.volume.setText(content.get(position -1).get(5));
            holder.adjClose.setText(content.get(position -1).get(6));
        }
    }

    /**
     * Created by Ru Zhang (rxz151130) on 11/7/16.
     * The delegate method for item count.
     */
    @Override
    public int getItemCount() {
        return content.size()+head.size();
    }

    /**
     * Created by Ru Zhang (rxz151130) on 11/7/16.
     * The viewHolder class.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView date;
        public TextView open;
        public TextView high;
        public TextView low;
        public TextView close;
        public TextView volume;
        public TextView adjClose;

        /**
         * Created by Ru Zhang (rxz151130) on 11/7/16.
         * The viewHolder constructor method.
         */
        public ViewHolder(View itemView){
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            open = (TextView) itemView.findViewById(R.id.open);
            high = (TextView) itemView.findViewById(R.id.high);
            low = (TextView) itemView.findViewById(R.id.low);
            close = (TextView) itemView.findViewById(R.id.close);
            volume= (TextView) itemView.findViewById(R.id.volume);
            adjClose = (TextView)itemView.findViewById(R.id.adjClose);
        }

    }
}
