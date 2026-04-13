package com.dieang.energym.ui.feature.home

sealed class HomeEvent {
    object LoadData : HomeEvent()
    object OnLogoutClick : HomeEvent()
    object OnNavigateRecetas : HomeEvent()
    object OnNavigateRutinas : HomeEvent()
    object OnNavigateSesiones : HomeEvent()
}
