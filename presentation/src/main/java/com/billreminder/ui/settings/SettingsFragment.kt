package com.billreminder.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.billreminder.R
import com.billreminder.databinding.FragmentSettingsBinding
import com.billreminder.ui.base.fragment.BaseFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SettingsFragment : BaseFragment() {
    @Inject
    lateinit var model: SettingsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentSettingsBinding>(inflater,
                R.layout.fragment_settings, container, false)
        binding.model = model

        return binding.root
    }

    //TODO DELETED FOR PRIVACY
}