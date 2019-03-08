package com.billreminder.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.billreminder.R
import com.billreminder.databinding.FragmentDashboardBinding
import com.billreminder.ui.base.fragment.BaseFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DashboardFragment : BaseFragment() {
    @Inject
    lateinit var model: DashboardViewModel

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        binding.model = model
        binding.view = this

        return binding.root
    }

    // TODO DELETED FOR PRIVACY
}