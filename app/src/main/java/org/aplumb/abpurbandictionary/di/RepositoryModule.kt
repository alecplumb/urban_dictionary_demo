package org.aplumb.abpurbandictionary.di

import dagger.Module
import dagger.Provides
import org.aplumb.abpurbandictionary.api.UrbanDictionaryApi
import org.aplumb.abpurbandictionary.api.UrbanDictionaryRepository
import org.aplumb.abpurbandictionary.api.UrbanDictionaryRepositoryImpl
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideUrbanDictionaryRespository(
        api: UrbanDictionaryApi
    ): UrbanDictionaryRepository {
        return UrbanDictionaryRepositoryImpl(api)
    }
}