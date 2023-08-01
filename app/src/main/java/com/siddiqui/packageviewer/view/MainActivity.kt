package com.siddiqui.packageviewer.view

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

            Log.d("TAG", "application total number of install: ${userInstallApp.size}")

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
        packageViewModel.addList(listItem)
        binding.recyclerView.adapter = AppListAdapter(listItem)

    }

    private fun isSystemApp(applicationInfo: ApplicationInfo): Boolean {
        return applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }


}