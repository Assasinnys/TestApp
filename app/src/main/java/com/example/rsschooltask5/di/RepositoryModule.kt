package com.example.rsschooltask5.di
/*ktlint-disable*/
import android.content.Context
import coil.ImageLoader
import com.example.rsschooltask5.api.CatsApi
import com.example.rsschooltask5.util.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
/*ktlint-enable*/

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun getCoilLoader(context: Context): ImageLoader {
        return ImageLoader(context)
    }

    @Singleton
    @Provides
    fun getCatApi(): CatsApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(CatsApi::class.java)
    }
}
