package com.egorshustov.vpoiske.userlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.adapters.UsersAdapter
import com.egorshustov.vpoiske.base.BaseFragment
import com.egorshustov.vpoiske.databinding.FragmentUserListBinding
import com.egorshustov.vpoiske.util.EventObserver

class UserListFragment : BaseFragment<UserListViewModel, FragmentUserListBinding>() {

    //todo use or kotlin ext or databinding
    override fun getLayoutResId(): Int = R.layout.fragment_user_list

    override val viewModel by activityViewModels<UserListViewModel> { viewModelFactory }

    private lateinit var gridLayoutManager: GridLayoutManager
    private var spanCount = DEFAULT_SPAN_COUNT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupListAdapter()
        observeOpenUserEvent()
        observeOpenNewSearch()
    }

    private fun setupListAdapter() {
        gridLayoutManager =
            GridLayoutManager(requireContext(), spanCount, RecyclerView.VERTICAL, false)
        binding.recyclerUsers.apply {
            layoutManager = gridLayoutManager
            adapter = UsersAdapter(viewModel)
        }
    }

    private fun observeOpenUserEvent() {
        viewModel.openUserEvent.observe(viewLifecycleOwner, EventObserver {
            openUserDetails(it)
        })
    }

    private fun observeOpenNewSearch() {
        viewModel.openNewSearch.observe(viewLifecycleOwner, EventObserver {
            val action =
                UserListFragmentDirections.actionUserListFragmentToNewSearchFragment()
            findNavController().navigate(action)
        })
    }

    private fun openUserDetails(userId: Long) {
        /*val action = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(userId)
        findNavController().navigate(action)*/
        val userUrl = "https://vk.com/id$userId"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(userUrl)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.item_change_view -> {
                gridLayoutManager.apply {
                    spanCount = (spanCount % MAX_SPAN_COUNT).inc()
                    this@UserListFragment.spanCount = spanCount
                }
                true
            }
            else -> false
        }

    companion object {
        private const val DEFAULT_SPAN_COUNT = 2
        private const val MAX_SPAN_COUNT = 3
    }
}