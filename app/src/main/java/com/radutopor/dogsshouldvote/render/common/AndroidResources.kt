package com.radutopor.dogsshouldvote.render.common

import android.content.Context
import com.radutopor.dogsshouldvote.ui.common.ResourceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidResources @Inject constructor(
    @ApplicationContext appContext: Context,
) : ResourceRepository {
    
    private val resources = appContext.resources

    override fun getString(resId: Int) = resources.getString(resId)
}