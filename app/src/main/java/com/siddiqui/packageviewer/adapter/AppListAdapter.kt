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
    private var filterData:ArrayList<AppListModel> = appArrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAppListViewModel {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.app_list_with_package, parent, false)
        return MyAppListViewModel(itemView)
    }

    override fun getItemCount(): Int {
        return appArrayList.size
    }

    override fun onBindViewHolder(holder: MyAppListViewModel, position: Int) {
        holder.bind(appArrayList[position])
    }

    inner class MyAppListViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val applicationName:TextView = itemView.findViewById(R.id.applicationName)
        private val packageName:TextView = itemView.findViewById(R.id.packageName)
        private val imageView:ImageView = itemView.findViewById(R.id.app_icon)

        fun bind(appListModel: AppListModel){
            applicationName.text = appListModel.applicationName
            packageName.text = appListModel.packageName
            imageView.setImageDrawable(appListModel.imageDrawable)
        }

    }
    fun filterData(query:String?){

    }

}