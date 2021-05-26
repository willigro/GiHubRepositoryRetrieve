package com.rittmann.githubapiapp.ui.list

import android.view.View
import androidx.databinding.ViewDataBinding
import coil.transform.CircleCropTransformation
import com.rittmann.androidtools.log.log
import com.rittmann.githubapiapp.R
import com.rittmann.githubapiapp.databinding.GithubAdapterBinding
import com.rittmann.githubapiapp.extensions.loadWithPalette
import com.rittmann.githubapiapp.model.basic.Repository
import com.rittmann.githubapiapp.ui.base.DataBindingBaseAdapter

class RepositoryAdapter(repositories: ArrayList<Repository>) :
    DataBindingBaseAdapter<Repository>(repositories) {

    override fun getItemLayout(viewType: Int): Int = R.layout.github_adapter

    override fun createViewHolder(viewBinding: ViewDataBinding)
            : DataBindingBaseViewHolder<Repository> =
        RepositoryHolder(viewBinding)

    class RepositoryHolder(private val viewBinding: ViewDataBinding) :
        DataBindingBaseAdapter.DataBindingBaseViewHolder<Repository>(viewBinding) {

        override fun bind(
            item: Repository,
            position: Int,
            itemClickListener: ((View, Int, Repository) -> Unit)?
        ) {
            (viewBinding as GithubAdapterBinding).apply {
                item.also {
                    itemAdapter.setOnClickListener {
                        itemClickListener?.invoke(it, position, item)
                    }
                    txtRepositoryName.text = item.name
                    txtRepositoryFullName.text = item.fullName

                    imgRepositoryAvatar.loadWithPalette(item.owner?.avatarUrl, containerAvatar) {
                        crossfade(true)
                        placeholder(R.drawable.img_placeholder)
                        transformations(CircleCropTransformation())
                    }
                }
            }
        }
    }
}