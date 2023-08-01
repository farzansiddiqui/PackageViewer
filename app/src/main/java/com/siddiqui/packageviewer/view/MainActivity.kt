package com.siddiqui.packageviewer.view

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
        onBackPressedActivity()
        

        val listItem: ArrayList<AppListModel> = arrayListOf()
        val userInstallApp = ArrayList<PackageInfo>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val applicationList: List<PackageInfo> =
                packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(0))

            for (packageInfo in applicationList) {
                listItem.clear()
                if (!isSystemApp(packageInfo.applicationInfo) && (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
                    userInstallApp.add(packageInfo)
                }
            }
            for (packageInfo in userInstallApp) {
                val appIcon = packageInfo.applicationInfo.loadIcon(packageManager)
                listItem.add(
                    AppListModel(
                        packageInfo.applicationInfo.loadLabel(packageManager).toString(),
                        packageInfo.applicationInfo.packageName,
                        appIcon
                    )
                )
            }


        } else {
            val applicationList: List<PackageInfo> = packageManager.getInstalledPackages(0)
            for (packageInfo in applicationList) {
                listItem.clear()
                if (!isSystemApp(packageInfo.applicationInfo) && (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
                    userInstallApp.add(packageInfo)
                }
            }
            for (packageInfo in userInstallApp) {
                val appIcon = packageInfo.applicationInfo.loadIcon(packageManager)
                listItem.add(
                    AppListModel(
                        packageInfo.applicationInfo.loadLabel(packageManager).toString(),
                        packageInfo.applicationInfo.packageName,
                        appIcon
                    )
                )
            }
        }
        Log.d("TAG", "application total number of install: ${userInstallApp.size}")
        packageViewModel.addList(listItem)
        binding.recyclerView.adapter = AppListAdapter(listItem)

        binding.searchView.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                val query = binding.searchView.text.toString()
                performSearch(query)
                true
            }else{
                false
            }

        }

    }

    private fun performSearch(query: String) {

    }

    private fun isSystemApp(applicationInfo: ApplicationInfo): Boolean {
        return applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    private fun onBackPressedActivity(){
        onBackPressedDispatcher.addCallback(this, object :OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.searchView.isShowing) {
                    binding.searchView.hide()
                }else {
                    finish()
                }
            }

        })
    }

}