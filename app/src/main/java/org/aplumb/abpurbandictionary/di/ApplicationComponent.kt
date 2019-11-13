package org.aplumb.abpurbandictionary.di

import org.aplumb.abpurbandictionary.ui.main.MainFragment

interface ApplicationComponent {
    fun inject(fragment: MainFragment)
}