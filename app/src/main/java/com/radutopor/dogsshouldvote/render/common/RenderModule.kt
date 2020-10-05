package com.radutopor.dogsshouldvote.render.common

import com.radutopor.dogsshouldvote.ui.common.ResourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RenderModule {

    @Binds
    abstract fun bindResourceRepo(androidResources: AndroidResources): ResourceRepository
}