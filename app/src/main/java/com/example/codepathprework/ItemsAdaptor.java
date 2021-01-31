package com.example.codepathprework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdaptor extends RecyclerView.Adapter<ItemsAdaptor.ViewHolder>{

    //creates an interface that MainActivity can interact with for long clicks
    public interface OnLongClickListener{
        //tells which item position was long clicked
        void onItemLongClicked(int position);
    }


    List<String> items;
    OnLongClickListener longclicklistener;

    public ItemsAdaptor(List<String> input, OnLongClickListener lcll) {
        items = input;
        longclicklistener = lcll;
    }

    //Creates each view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Use the layout inflater to inflate a view
        //using "design" of simple list item 1 which is a text view with is text1
        View todoview = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        //put inside a view holder and return

        return new ViewHolder(todoview);
    }

    //takes data from a position and puts it in a Viewholder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab an item at the position
        String item = items.get(position);
        //bind item into specified view holder
        holder.bind(item);

    }

    //number of items in data
    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvitem;

        //using text1 from simple list item 1
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            //sets the view to be a view inside of the passed in viewholder
            tvitem = itemView.findViewById(android.R.id.text1);
        }

        //update data in the holder to be this item
        public void bind(String item){
            //sets the text to item that was passed in
            tvitem.setText(item);
            //identifies if there was a click and hold on an item
            tvitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //notifies position of long click if there was one
                    longclicklistener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });

        }
    }
}
