package com.mbeyaztas.csv.file.viewer.commons.di

import android.content.Context
import com.mbeyaztas.csv.file.viewer.commons.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ResourceProviderModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext appContext: Context)
            : ResourceProvider = ResourceProvider(appContext)

}
