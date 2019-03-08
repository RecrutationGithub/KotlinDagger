package com.billreminder.ui.bills

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.billreminder.R
import com.billreminder.data.model.Bill
import com.billreminder.databinding.FragmentBillsBinding
import com.billreminder.ui.base.adapter.MultiViewAdapter
import com.billreminder.ui.base.fragment.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val ADD_BILL_CODE = 101

class BillsFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var adapter: MultiViewAdapter

    lateinit var binding: FragmentBillsBinding

    private val model by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(BillsViewModel::class.java)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(activity, layoutManager.orientation)

        binding.recyclerBills.addItemDecoration(dividerItemDecoration)
        binding.recyclerBills.layoutManager = layoutManager
    }

    private fun subscribeToDataSetChangedEvents() {
        model.dataSetChangedEvent.observe(this, Observer { event ->
            event?.let {
                if (it.first == it.second) {
                    adapter.notifyItemRemoved(it.first)

                    activity?.let { manager ->
                        //TODO DELETED FOR PRIVACY
//                        if (manager is DashboardDataManager) {
//                            manager.refreshDashboard()
//                        }
                    }
                } else {
                    adapter.notifyDataSetChanged()
                }

                if (model.bills.isEmpty()) {
                    binding.viewNoBillsAvailable.root.visibility = View.VISIBLE
                } else {
                    binding.viewNoBillsAvailable.root.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun subscribeToErrorMessageEvents() {
        model.errorMessageEvent.observe(this, Observer { event ->
            event?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
        })
    }

    private fun showDeleteBillDialog(bill: Bill) {
//        alert(R.string.deleteBillMessage, R.string.deleteBillTitle) {
//            yesButton { model.delete(bill) }
//            noButton {}
//        }.show()
    }

    private fun subscribeToDeleteEvents() {
        model.deleteEvent.observe(this, Observer { bill ->
            bill?.let {
                showDeleteBillDialog(it)
            }
        })
    }

    private fun subscribeToShowDetailsEvent() {
        model.showDetailsEvent.observe(this, Observer { bill ->
            context?.let { context
                bill?.let { b ->
                    //todo DELETE FOR PRIVACY
//                    startActivityForResult(AddBillActivity.createIntent(it, b), ADD_BILL_CODE)
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidSupportInjection.inject(this)
        activity?.let { model.fetchBills() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_bills, container, false)
        binding.model = model
        binding.view = this

        binding.fabAddBill.setOnClickListener {
            //todo DELETE FOR PRIVACY
//            startActivityForResult(Intent(activity, AddBillActivity::class.java), ADD_BILL_CODE)
        }
        binding.recyclerBills.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    binding.fabAddBill.hide()
                } else {
                    binding.fabAddBill.show()
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        initRecyclerView()

        adapter = MultiViewAdapter.Builder(model.bills)
                .register(R.layout.item_bill, BillRowViewModel::class.java)
                .build()

        subscribeToErrorMessageEvents()
        subscribeToDataSetChangedEvents()
        subscribeToDeleteEvents()
        subscribeToShowDetailsEvent()

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        activity?.let {
            model.fetchBills()
            //TODO DELETED FOR PRIVACY
//            if (it is DashboardDataManager) {
//                it.refreshDashboard()
//            }
        }
    }
}