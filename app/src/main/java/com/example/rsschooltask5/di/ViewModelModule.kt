package com.example.rsschooltask5.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rsschooltask5.viewmodel.DetailFragmentViewModel
import com.example.rsschooltask5.viewmodel.ListFragmentViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Binds
    @IntoMap
    @ViewModelKey(ListFragmentViewModel::class)
    abstract fun mainViewModel(mainViewModel: ListFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailFragmentViewModel::class)
    abstract fun settingsViewModel(settingsViewModel: DetailFragmentViewModel): ViewModel

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

class ViewModelFactory @Inject constructor(
    val providerMap: Map<Class<out ViewModel>,
    @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = providerMap[modelClass]
        if (creator == null) {
            for ((key, value) in providerMap) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class: $modelClass")
        }
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
