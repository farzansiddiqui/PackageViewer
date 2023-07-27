package com.siddiqui.packageviewer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.siddiqui.packageviewer.R
import com.siddiqui.packageviewer.adapter.AppListAdapter
import com.siddiqui.packageviewer.databinding.ActivityMainBinding
import com.siddiqui.packageviewer.model.AppListModel
import com.siddiqui.packageviewer.viewmodel.PackageViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var packageViewModel: PackageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        packageViewModel = ViewModelProvider(this)[PackageViewModel::class.java]


        val listItem:ArrayList<AppListModel> = arrayListOf()
        listItem.add(AppListModel("appName", "PackageName", R.drawable.ic_launcher_background))
        listItem.add(AppListModel("appName", "PackageName", R.drawable.ic_launcher_background))
        listItem.add(AppListModel("appName", "PackageName", R.drawable.ic_launcher_background))
        listItem.add(AppListModel("appName", "PackageName", R.drawable.ic_launcher_background))
        listItem.add(AppListModel("appName", "PackageName", R.drawable.ic_launcher_background))
        listItem.add(AppListModel("appName", "PackageName", R.drawable.ic_launcher_background))
        listItem.add(AppListModel("appName", "PackageName", R.drawable.ic_launcher_background))
        listItem.add(AppListModel("appName", "PackageName", R.drawable.ic_launcher_background))
        packageViewModel.addList(listItem)

        binding.recyclerView.adapter = AppListAdapter(listItem)

    }
}