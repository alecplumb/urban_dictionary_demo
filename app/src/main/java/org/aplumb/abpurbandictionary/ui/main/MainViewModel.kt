package org.aplumb.abpurbandictionary.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.aplumb.abpurbandictionary.api.UrbanDictionaryRepository
import org.aplumb.abpurbandictionary.api.model.Definition
import timber.log.Timber


class MainViewModel(val repository: UrbanDictionaryRepository) : ViewModel() {
    val definitions = MutableLiveData<List<Definition>>(emptyList())
    val isLoading = MutableLiveData<Boolean>(false)

    private var disposable: Disposable? = null
    private var numLoaders = 0

    fun lookupDefinition(term: String?, sortThumbsUp: Boolean) {
        incrementLoaders()
        disposable?.dispose()
        disposable =
            repository.getDefinition(term)
                .observeOn(AndroidSchedulers.mainThread())
                .map { definitions -> definitions.sortedByDescending { if (sortThumbsUp) it.thumbsUp else it.thumbsDown } }
                .doFinally { decrementLoaders() }
                .subscribe({ result ->
                    definitions.value = result
                    isLoading.value = false
                }, { error ->
                    // TODO: user error notification
                    Timber.v(error, "Failed to lookup definition")
                    isLoading.value = false
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    private fun incrementLoaders() {
        numLoaders++
        isLoading.value = numLoaders > 0
    }

    private fun decrementLoaders() {
        numLoaders--
        isLoading.value = numLoaders > 0
    }
}
