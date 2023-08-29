package com.siddiqui.packageviewer.btmFragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.siddiqui.packageviewer.R

class AppDetailsFragment:BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.app_btm_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val applicationName:TextView = view.findViewById(R.id.application_TextView)
        val playStore:RelativeLayout = view.findViewById(R.id.playStoreRelativeLayout)
        val launchApplication:RelativeLayout = view.findViewById(R.id.launchApplication)
        val shareApplication:RelativeLayout = view.findViewById(R.id.shareRelativeLayout)
        val fullDetails:ImageView = view.findViewById(R.id.settingImageView)
        val deleteApplication:ImageView = view.findViewById(R.id.deleteImageView)

        if (arguments != null){
            applicationName.text = arguments?.getString("applicationName")
            val packageName = arguments?.getString("packageName")
            playStore.setOnClickListener {
                context?.openPlayStoreApp(packageName)
                dismiss()
            }
            launchApplication.setOnClickListener {
                context?.launchApplication(packageName!!)
            }

                shareApplication.setOnClickListener {
                    context?.shareApplication(packageName!!)
                }

            fullDetails.setOnClickListener {
                context?.settingApplication(packageName!!)
            }
            deleteApplication.setOnClickListener {
                val dialog = context?.deleteDialog(requireContext(), packageName!!)
               dialog?.show()

            }

        }

    }
    private fun Context.openPlayStoreApp(pkgName:String?){
        if(!pkgName.isNullOrEmpty()) {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pkgName")))
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$pkgName")
                    )
                )
            }
        }
    }
    private fun Context.launchApplication(pkgName:String){
        if (pkgName.isNotEmpty()){
            val intent = packageManager.getLaunchIntentForPackage(pkgName)
            startActivity(intent)
        }

    }
    private fun Context.shareApplication(pkgName: String){
            if (pkgName.isNotEmpty()){
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
               shareIntent.putExtra(Intent.EXTRA_TEXT, ""+""+Uri.parse("https://play.google.com/store/apps/details?id=$pkgName"))
                startActivity(shareIntent)
            }

    }
    private fun Context.settingApplication(pkgName: String){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$pkgName")
        startActivity(intent)
    }

   private fun Context.deleteDialog(context: Context, pkgName: String):MaterialAlertDialogBuilder{
       return MaterialAlertDialogBuilder(context).
               setTitle("Delete Application!!")
           .setMessage("Do You want to Delete this application?")
           .setPositiveButton("Ok") { dialog, _ ->
               val intent = Intent(Intent.ACTION_UNINSTALL_PACKAGE)
               intent.data = Uri.parse("package:$pkgName")
               startActivity(intent)
               dialog.dismiss()
           }

   }

}