package org.aplumb.abpurbandictionary.api

import io.reactivex.Observable
import org.aplumb.abpurbandictionary.api.model.DefineResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UrbanDictionaryApi {
    @GET("define")
    fun define(@Query("term") term: String) : Observable<DefineResponse>
}