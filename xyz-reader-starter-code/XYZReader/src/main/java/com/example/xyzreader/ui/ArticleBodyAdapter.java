package com.example.xyzreader.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xyzreader.R;

import java.util.List;

public class ArticleBodyAdapter extends RecyclerView.Adapter<ArticleBodyAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    ArticleBodyAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_article_body, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String bodyText = mData.get(position);
        holder.articleBodyTv.setText(Html.fromHtml(bodyText));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView articleBodyTv;

        ViewHolder(View itemView) {
            super(itemView);
            articleBodyTv = itemView.findViewById(R.id.article_body_tv);
        }
    }
}