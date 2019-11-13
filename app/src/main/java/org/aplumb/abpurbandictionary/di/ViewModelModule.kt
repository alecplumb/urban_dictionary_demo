package org.aplumb.abpurbandictionary.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import org.aplumb.abpurbandictionary.api.UrbanDictionaryRepository
import javax.inject.Singleton


@Module
class ViewModelModule {
    @Provides
    @Singleton
    fun getViewModelFactory(repository: UrbanDictionaryRepository): ViewModelProvider.Factory {
        return ViewModelFactory(repository)
    }

}