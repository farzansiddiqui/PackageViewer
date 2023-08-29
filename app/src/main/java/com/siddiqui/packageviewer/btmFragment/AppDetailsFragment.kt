package com.siddiqui.packageviewer.btmFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
        if (arguments != null){
            applicationName.text = arguments?.getString("applicationName")
        }

    }
}