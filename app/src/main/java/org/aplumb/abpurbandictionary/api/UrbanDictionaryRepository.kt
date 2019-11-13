package org.aplumb.abpurbandictionary.api

import io.reactivex.Observable
import org.aplumb.abpurbandictionary.api.model.Definition

interface UrbanDictionaryRepository {
    fun getDefinition(term: String?) : Observable<List<Definition>>
}