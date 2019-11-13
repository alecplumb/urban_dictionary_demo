package org.aplumb.abpurbandictionary

import dagger.Component
import org.aplumb.abpurbandictionary.di.ApplicationComponent
import org.aplumb.abpurbandictionary.di.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [TestMainModule::class, ViewModelModule::class])
interface TestApplicationComponent : ApplicationComponent {
}