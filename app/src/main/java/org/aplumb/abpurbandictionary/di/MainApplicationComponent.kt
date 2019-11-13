package org.aplumb.abpurbandictionary.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class]
)
interface MainApplicationComponent : ApplicationComponent {
}