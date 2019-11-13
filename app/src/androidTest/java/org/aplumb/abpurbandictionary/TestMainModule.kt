package org.aplumb.abpurbandictionary

import dagger.Module
import dagger.Provides
import org.aplumb.abpurbandictionary.api.UrbanDictionaryRepository
import javax.inject.Singleton

@Module
class TestMainModule(val repository: UrbanDictionaryRepository) {
    @Provides
    @Singleton
    fun provideUrbanDictionaryRepository(): UrbanDictionaryRepository {
        return repository
    }
}