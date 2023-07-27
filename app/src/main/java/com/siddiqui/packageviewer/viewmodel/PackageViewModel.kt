package com.siddiqui.packageviewer.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.siddiqui.packageviewer.model.AppListModel

class PackageViewModel:ViewModel() {
     var itemList  = MutableLiveData<ArrayList<AppListModel>>()
    private var newList = ArrayList<AppListModel>()


    fun addList(appListModel: ArrayList<AppListModel>){
            newList.addAll(appListModel)
            itemList.value = newList
    }
}