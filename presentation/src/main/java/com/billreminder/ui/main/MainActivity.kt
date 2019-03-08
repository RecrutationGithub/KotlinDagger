package com.billreminder.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.billreminder.R
import com.billreminder.databinding.ActivityMainBinding
import com.billreminder.ui.base.activity.BaseActivity
import com.billreminder.ui.bills.BillsFragment
import com.billreminder.ui.dashboard.DashboardFragment
import com.billreminder.ui.settings.SettingsFragment
import com.google.common.base.Preconditions.checkNotNull
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject

private const val TAG_DASHBOARD = "dashboard"
private const val TAG_BILLS = "bills"
private const val TAG_SETTINGS = "settings"

class MainActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val model by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private var dashboardFragment: DashboardFragment? = null
    private var billsFragment: BillsFragment? = null
    private var settingsFragment: SettingsFragment? = null

    private val menuActions: MutableMap<Int, () -> Unit> = HashMap()

    private fun setUpBottomNavigation(binding: ActivityMainBinding) {
        menuActions[R.id.action_dashboard] = { dashboardFragment?.let { show(it, TAG_DASHBOARD) }}
        menuActions[R.id.action_bills] = { billsFragment?.let { show(it, TAG_BILLS) }}
        menuActions[R.id.action_settings] = { settingsFragment?.let { show(it, TAG_SETTINGS) } }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            menuActions[item.itemId]?.let {
                it()
                return@setOnNavigationItemSelectedListener true
            }

            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun restoreBillsFragment() {
        val bills = supportFragmentManager.findFragmentByTag(TAG_BILLS)
        bills?.let { billsFragment = it as BillsFragment }
    }

    private fun restoreDashboardFragment() {
        val dashboard = supportFragmentManager.findFragmentByTag(TAG_DASHBOARD)
        dashboard?.let { dashboardFragment = it as DashboardFragment }
    }

    private fun restoreSettingsFragment() {
        val settings = supportFragmentManager.findFragmentByTag(TAG_SETTINGS)
        settings?.let { settingsFragment = it as SettingsFragment }
    }

    private fun createFragmentsIfRequired() {
        dashboardFragment = dashboardFragment ?: DashboardFragment()
        billsFragment = billsFragment ?: BillsFragment()
        settingsFragment = settingsFragment ?: SettingsFragment()
    }

    private fun show(fragment: Fragment, tag: String) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)

        for (t in listOf(TAG_DASHBOARD, TAG_BILLS, TAG_SETTINGS)) {
            val f = fm.findFragmentByTag(t)
            f?.let {
                if (it != fragment) {
                    ft.hide(it)
                }
            }
        }

        val addedFragment = fm.findFragmentByTag(tag)
        if (addedFragment != null) {
            ft.show(addedFragment)
        } else {
            ft.add(R.id.fragmentContainer, checkNotNull(fragment), tag)
        }

        ft.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main)
        binding.model = model

        if (savedInstanceState != null) {
            restoreDashboardFragment()
            restoreBillsFragment()
            restoreSettingsFragment()
        }

        createFragmentsIfRequired()
        setUpBottomNavigation(binding)

        if (savedInstanceState == null) {
            dashboardFragment?.let { show(it, TAG_DASHBOARD) }
        }
    }
}