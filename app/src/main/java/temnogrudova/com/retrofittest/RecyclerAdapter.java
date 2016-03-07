package temnogrudova.com.retrofittest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by 123 on 05.02.2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter {
    ArrayList<Post> myArray;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pattern, parent, false);
        return ItemViewHolder.newInstance(view);
    }

    public RecyclerAdapter(ArrayList<Post> myArray) {
        this.myArray = myArray;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;

        String userId = myArray.get(position).userId;
        String id= myArray.get(position).id;
        String title= myArray.get(position).title;
        String body= myArray.get(position).body;
        viewHolder.setItemText(userId,id,title,body);
    }

    @Override
    public int getItemCount() {
        return myArray == null ? 0 : myArray.size();
    }
}
