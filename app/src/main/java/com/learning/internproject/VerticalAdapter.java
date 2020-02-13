package com.learning.internproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    List<DataObject> dataObjects = Collections.emptyList();
    DataObject current;

    public VerticalAdapter(Context context,List<DataObject> data){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.dataObjects = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        current = dataObjects.get(position);
        if(current.getTitle()!=null)
            viewHolder.title.setText(current.getTitle());

        if(current.getText()!=null) {
            viewHolder.textView.setVisibility(View.VISIBLE);
            viewHolder.textView.setText(current.getText());
        }

        if(current.media!=null){
            RecyclerView.Adapter horizontalAdapter = new HorizontalAdapter(context,current.media);
            viewHolder.innerRecyclerView.setAdapter(horizontalAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
            viewHolder.innerRecyclerView.setLayoutManager(linearLayoutManager);
        }else {
            viewHolder.innerRecyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return dataObjects.size();
    }
}

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView textView;
        RecyclerView innerRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            textView = itemView.findViewById(R.id.textView);
            innerRecyclerView = itemView.findViewById(R.id.HorizontalRV);
        }
    }
