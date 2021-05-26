package com.rittmann.githubapiapp.ui.details

import com.rittmann.githubapiapp.R
import com.rittmann.githubapiapp.model.mock.Mock
import com.rittmann.githubapiapp.support.ActivityTest
import com.rittmann.githubapiapp.support.ExpressoUtil.checkValue
import com.rittmann.githubapiapp.support.ExpressoUtil.hasBackground
import com.rittmann.githubapiapp.support.ExpressoUtil.viewIsDisplayed
import com.rittmann.githubapiapp.utils.DateUtils
import org.junit.Test

class DetailsActivityTest : ActivityTest() {

    @Test
    fun showRepositoryValues_FromNonPrivate() {
        /*
        * Mock
        * */
        val repository = Mock.getRepository(isPrivate = false)

        /*
        * Open screen
        * */
        getActivity<DetailsActivity>(
            DetailsActivity.getIntent(context, repository)
        )

        /*
        * Testing the screen
        * */
        checkValue(R.id.txt_repository_full_name, repository.fullName)
        checkValue(R.id.txt_repository_name, repository.name)
        checkValue(R.id.txt_repository_description, repository.description)
        checkValue(R.id.txt_repository_date, DateUtils.parse(repository.createdAt))
        checkValue(R.id.txt_repository_starts, repository.starts)
        hasBackground(R.id.img_repository_avatar, R.drawable.img_placeholder)
        viewIsDisplayed(R.id.img_repository_stars)
        hasBackground(R.id.img_repository_lock, R.drawable.baseline_lock_open_black_24)
    }

    @Test
    fun showRepositoryValues_FromPrivate() {
        /*
        * Mock
        * */
        val repository = Mock.getRepository(isPrivate = true)

        /*
        * Open screen
        * */
        getActivity<DetailsActivity>(
            DetailsActivity.getIntent(context, repository)
        )

        /*
        * Testing the screen
        * */
        checkValue(R.id.txt_repository_full_name, repository.fullName)
        checkValue(R.id.txt_repository_name, repository.name)
        checkValue(R.id.txt_repository_description, repository.description)
        checkValue(R.id.txt_repository_date, DateUtils.parse(repository.createdAt))
        checkValue(R.id.txt_repository_starts, repository.starts)
        hasBackground(R.id.img_repository_avatar, R.drawable.img_placeholder)
        viewIsDisplayed(R.id.img_repository_stars)
        hasBackground(R.id.img_repository_lock, R.drawable.baseline_lock_black_24)
    }
}