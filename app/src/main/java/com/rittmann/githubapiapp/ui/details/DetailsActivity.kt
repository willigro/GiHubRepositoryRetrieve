package com.rittmann.githubapiapp.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import coil.transform.CircleCropTransformation
import com.rittmann.githubapiapp.R
import com.rittmann.githubapiapp.databinding.ActivityDetailsBinding
import com.rittmann.githubapiapp.extensions.loadWithPalette
import com.rittmann.githubapiapp.extensions.setDrawable
import com.rittmann.githubapiapp.model.basic.Repository
import com.rittmann.githubapiapp.ui.base.BaseBindingActivity
import com.rittmann.githubapiapp.utils.DateUtils

class DetailsActivity : BaseBindingActivity<ActivityDetailsBinding>(R.layout.activity_details) {

    private val repository: Repository by lazy {
        intent!!.getSerializableExtra(REPOSITORY_ARGS) as Repository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            with(repository) {
                imgRepositoryAvatar.loadWithPalette(owner?.avatarUrl, containerAvatar) {
                    crossfade(true)
                    placeholder(R.drawable.img_placeholder)
                    transformations(CircleCropTransformation())
                }

                txtRepositoryName.text = name
                txtRepositoryFullName.text = fullName
                txtRepositoryDescription.text = description
                txtRepositoryDate.text = DateUtils.parse(createdAt)
                txtRepositoryStarts.text = starts.toString()

                if (isPrivate!!)
                    imgRepositoryLock.setDrawable(
                        R.drawable.baseline_lock_open_black_24,
                        getString(R.string.content_description_img_unlocked)
                    )
            }
        }
    }

    companion object {
        private const val REPOSITORY_ARGS = "argsR"

        fun getIntent(context: Context, repository: Repository): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtras(getBundle(repository))
            }
        }

        fun getBundle(repository: Repository): Bundle {
            return bundleOf(
                Pair(REPOSITORY_ARGS, repository)
            )
        }
    }
}