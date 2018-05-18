package com.example.vietvan.leaningenglish.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vietvan.leaningenglish.R;
import com.example.vietvan.leaningenglish.databases.DatabaseManager;
import com.example.vietvan.leaningenglish.databases.models.CategoryModel;
import com.example.vietvan.leaningenglish.databases.models.TopicModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by VietVan on 04/03/2018.
 */

public class TopicExpandableListViewAdapter extends BaseExpandableListAdapter {

    List<CategoryModel> categoryModelList;
    HashMap<String, List<TopicModel>> topicModelHashMap;
    Context context;

    public TopicExpandableListViewAdapter(Context context, List<CategoryModel> categoryModelList, HashMap<String, List<TopicModel>> topicModelHashMap) {
        this.context = context;
        this.categoryModelList = categoryModelList;
        this.topicModelHashMap = topicModelHashMap;
    }

    @Override
    public int getGroupCount() {
        return categoryModelList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return topicModelHashMap.get(categoryModelList.get(i).name).size();
    }

    @Override
    public Object getGroup(int i) {
        return categoryModelList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return topicModelHashMap.get(categoryModelList.get(i).name).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_list_category, viewGroup, false);

        CategoryModel categoryModel = (CategoryModel) getGroup(i);

        TextView tvCategory = view.findViewById(R.id.tv_category);
        TextView tvCategoryDes = view.findViewById(R.id.tv_category_des);
        ImageView ivArray = view.findViewById(R.id.iv_arrow);
        CardView cvCategory = view.findViewById(R.id.cv_category);

        cvCategory.setCardBackgroundColor(Color.parseColor(categoryModel.color));

        if(!isExpanded)
            ivArray.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        else
            ivArray.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);

        tvCategory.setText(categoryModel.name);

        String s = "";
        List<TopicModel> list = topicModelHashMap.get(categoryModel.name);
        for(int b=0;b<list.size();b++){
            s += list.get(b).name;
            if(b != list.size() - 1)
                s += ", ";
        }
        tvCategoryDes.setText(s);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_list_topic, viewGroup, false);

        TopicModel topicModel = (TopicModel) getChild(i, i1);

        TextView tvTopic = view.findViewById(R.id.tv_name_topic);
        TextView tvLastTime = view.findViewById(R.id.tv_last_time);
        ProgressBar pbTopic = view.findViewById(R.id.pb_topic);

        pbTopic.setMax(12);
        pbTopic.setProgress(DatabaseManager.getInstance(context).getNumberWordById(topicModel.id, 4));
        pbTopic.setSecondaryProgress(12 - DatabaseManager.getInstance(context).getNumberWordById(topicModel.id, 0));

        tvTopic.setText(topicModel.name);
        if(topicModel.lastTime != null)
            tvLastTime.setText(topicModel.lastTime);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void refreshList(Context context){
        // 1.change data
        topicModelHashMap.clear();
        DatabaseManager db = DatabaseManager.getInstance(context);
        topicModelHashMap.putAll(db.getHashMap(db.getListTopic(), db.getListCategory()));

        // 2.refresh: add, remove, addAll, removeAll, clear
        notifyDataSetChanged();
    }

}
