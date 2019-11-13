package org.aplumb.abpurbandictionary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.aplumb.abpurbandictionary.api.UrbanDictionaryRepository
import org.aplumb.abpurbandictionary.ui.main.MainViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    val repository: UrbanDictionaryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class")
    }
}