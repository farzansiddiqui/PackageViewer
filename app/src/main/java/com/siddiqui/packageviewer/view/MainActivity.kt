package com.siddiqui.packageviewer.view

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.siddiqui.packageviewer.adapter.AppListAdapter
import com.siddiqui.packageviewer.btmFragment.AppDetailsFragment
import com.siddiqui.packageviewer.databinding.ActivityMainBinding
import com.siddiqui.packageviewer.model.AppListModel
import com.siddiqui.packageviewer.viewmodel.PackageViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var packageViewModel: PackageViewModel
    val listItem: ArrayList<AppListModel> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(this)

        packageViewModel = ViewModelProvider(this)[PackageViewModel::class.java]
        onBackPressedActivity()




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
        val adapter = AppListAdapter(listItem,object :AppListAdapter.ItemClickListener{
            override fun onItemClick(itemPosition: Int) {
                val appDetailsFragment = AppDetailsFragment()
                val bundle = Bundle()
                bundle.putString("applicationName", listItem[itemPosition].applicationName)
                bundle.putString("packageName",listItem[itemPosition].packageName)
                appDetailsFragment.arguments = bundle
                appDetailsFragment.show(supportFragmentManager,"App Details BottomFragment")
            }
        })
        binding.recyclerView.adapter = adapter



        // it's for when user write the text afterwards and click the search button on keyboard
        // then performed the function.

        val searchAdapter = AppListAdapter(listItem,object :AppListAdapter.ItemClickListener{
            override fun onItemClick(itemPosition: Int) {
                val appDetailsFragment = AppDetailsFragment()
                val bundle = Bundle()
                bundle.putString("applicationName", listItem[itemPosition].applicationName)
                bundle.putString("packageName",listItem[itemPosition].packageName)
                appDetailsFragment.arguments = bundle
                appDetailsFragment.show(supportFragmentManager,"App Details BottomFragment")
            }
        })
        binding.searchRecyclerView.adapter = searchAdapter



        binding.searchView.editText.doOnTextChanged { text, _, _, _ ->
            val filteredList = listItem.filter { item->
                item.applicationName.contains(text!!,ignoreCase = true)
            }

            searchAdapter.updateList(filteredList)
        }

    }

    private fun isSystemApp(applicationInfo: ApplicationInfo): Boolean {
        return applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    private fun onBackPressedActivity() {
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

   /* override fun onItemClick(itemPosition: Int) {
        val appDetailsFragment = AppDetailsFragment()
        val bundle = Bundle()
        bundle.putString("applicationName", listItem[itemPosition].applicationName)
        bundle.putString("packageName",listItem[itemPosition].packageName)
        appDetailsFragment.arguments = bundle
        appDetailsFragment.show(supportFragmentManager,"App Details BottomFragment")
    }*/

}