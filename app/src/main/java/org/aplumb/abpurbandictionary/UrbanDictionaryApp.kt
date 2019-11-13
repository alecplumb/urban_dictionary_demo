package org.aplumb.abpurbandictionary

import android.app.Application
import org.aplumb.abpurbandictionary.di.ApplicationComponent
import org.aplumb.abpurbandictionary.di.DaggerMainApplicationComponent
import timber.log.Timber
import timber.log.Timber.DebugTree


class UrbanDictionaryApp : Application() {
    companion object {
        lateinit var app: UrbanDictionaryApp

        fun component(): ApplicationComponent {
            return app._component
        }
    }

    var _component: ApplicationComponent = DaggerMainApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
        app = this

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}