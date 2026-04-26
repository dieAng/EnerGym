package com.dieang.energym.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    // Los ViewModels se inyectan automáticamente mediante @HiltViewModel y @Inject constructor
}
