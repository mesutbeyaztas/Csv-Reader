package com.mbeyaztas.csv.file.viewer.commons.di.local

import android.content.Context
import com.mbeyaztas.csv.file.viewer.data.local.LocalFileReaderRepository
import com.mbeyaztas.csv.file.viewer.domain.FileReaderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ActivityRetainedComponent::class)
object FileReaderModule {

    @Provides
    @ActivityRetainedScoped
    fun provideFileRepository(
        dispatcher: CoroutineDispatcher,
        @ApplicationContext appContext: Context
    ): FileReaderRepository {
        return LocalFileReaderRepository(
            dispatcher,
            appContext
        )
    }

}