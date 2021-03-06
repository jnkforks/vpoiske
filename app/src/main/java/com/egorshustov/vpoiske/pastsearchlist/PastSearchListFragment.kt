package com.egorshustov.vpoiske.pastsearchlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.adapters.SearchWithUsersAdapter
import com.egorshustov.vpoiske.base.BaseFragment
import com.egorshustov.vpoiske.databinding.FragmentPastSearchListBinding
import com.egorshustov.vpoiske.util.EventObserver
import com.egorshustov.vpoiske.util.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PastSearchListFragment :
    BaseFragment<PastSearchListViewModel, FragmentPastSearchListBinding>(),
    SearchItemTouchHelper.SearchItemTouchHelperListener {

    override fun getLayoutResId(): Int = R.layout.fragment_past_search_list

    override val viewModel: PastSearchListViewModel by viewModels()

    private lateinit var searchWithUsersAdapter: SearchWithUsersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onPastSearchListFragmentViewCreated()
        setupRecyclerSearches()
        observeOpenSearch()
    }

    private fun setupRecyclerSearches() = with(binding) {
        val searchItemTouchHelper =
            SearchItemTouchHelper(0, ItemTouchHelper.LEFT, this@PastSearchListFragment)
        ItemTouchHelper(searchItemTouchHelper).attachToRecyclerView(recyclerSearches)
        searchWithUsersAdapter = SearchWithUsersAdapter(viewModel)
        recyclerSearches.adapter = searchWithUsersAdapter
    }

    private fun observeOpenSearch() {
        viewModel.openSearch.observe(viewLifecycleOwner, EventObserver {
            val action =
                PastSearchListFragmentDirections.actionPastSearchListFragmentToPastSearchFragment(it)
            findNavController().safeNavigate(action)
        })
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        searchWithUsersAdapter.getItemAtPosition(viewHolder.adapterPosition)?.search?.id?.let {
            viewModel.onSearchSwiped(it)
        }
    }
}