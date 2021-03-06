package com.egorshustov.vpoiske.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egorshustov.vpoiske.data.User
import com.egorshustov.vpoiske.databinding.ItemUserBinding
import com.egorshustov.vpoiske.pastsearch.PastSearchViewModel
import com.egorshustov.vpoiske.main.MainViewModel

class UsersAdapter(private val viewModel: ViewModel) :
    ListAdapter<User, UsersAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { holder.bind(viewModel, it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ViewModel, item: User) = with(binding) {
            when (viewModel) {
                is PastSearchViewModel -> pastsearchviewmodel = viewModel
                is MainViewModel -> mainviewmodel = viewModel
            }
            user = item
            executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User) =
        oldItem.inBlacklist == newItem.inBlacklist
                && oldItem.isFavorite == newItem.isFavorite
                && oldItem.photoMaxOrig == newItem.photoMaxOrig
}
