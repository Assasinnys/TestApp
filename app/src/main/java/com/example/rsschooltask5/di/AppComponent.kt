package com.example.rsschooltask5.di

import android.content.Context
import com.example.rsschooltask5.ui.fragments.DetailFragment
import com.example.rsschooltask5.ui.fragments.ListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, RepositoryModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(listFragment: ListFragment)
    fun inject(detailFragment: DetailFragment)
}
