package com.siddiqui.packageviewer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siddiqui.packageviewer.R
import com.siddiqui.packageviewer.model.AppListModel

class AppListAdapter(private val appArrayList:ArrayList<AppListModel>) : RecyclerView.Adapter<AppListAdapter.MyAppListViewModel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAppListViewModel {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.app_list_with_package, parent, false)
        return MyAppListViewModel(itemView)
    }

    override fun getItemCount(): Int {
        return appArrayList.size
    }

    override fun onBindViewHolder(holder: MyAppListViewModel, position: Int) {

    }

    inner class MyAppListViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val applicationName:TextView = itemView.findViewById(R.id.applicationName)
        val packageName:TextView = itemView.findViewById(R.id.packageName)
        val imageView:ImageView = itemView.findViewById(R.id.app_icon)

    }

}