package org.aplumb.abpurbandictionary.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.aplumb.abpurbandictionary.BuildConfig
import org.aplumb.abpurbandictionary.api.UrbanDictionaryApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {
    @Provides
    @Singleton
    @Named("urban-dictionary")
    fun provideUrbanDictionaryGson(): Gson {
        val builder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @Provides
    @Singleton
    @Named("urban-dictionary")
    fun provideUrbanDictionaryClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("x-rapidapi-host", BuildConfig.RAPID_API_HOST)
                .header("x-rapidapi-key", BuildConfig.RAPID_API_KEY)
                .build()
            chain.proceed(request)
        }
            .addInterceptor(HttpLoggingInterceptor { message -> Timber.v(message) }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }


    @Provides
    @Singleton
    @Named("urban-dictionary")
    fun provideUrbanDictionaryRetrofit(
        @Named("urban-dictionary") gson: Gson,
        @Named("urban-dictionary") okHttpClient: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.RAPID_API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideUrbanDictionaryApi(
        @Named("urban-dictionary") retrofit: Retrofit
    ): UrbanDictionaryApi {
        return retrofit.create(UrbanDictionaryApi::class.java)
    }

}