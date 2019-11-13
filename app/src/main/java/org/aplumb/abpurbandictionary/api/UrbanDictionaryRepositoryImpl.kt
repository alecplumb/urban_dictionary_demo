package org.aplumb.abpurbandictionary.api

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.aplumb.abpurbandictionary.api.model.Definition

class UrbanDictionaryRepositoryImpl(val api: UrbanDictionaryApi) : UrbanDictionaryRepository {
    override fun getDefinition(term: String?): Observable<List<Definition>> {
        if (term.isNullOrBlank()) return Observable.just(emptyList())
        return api.define(term)
            .map { it.list.orEmpty() }
            .subscribeOn(Schedulers.io())
    }
}