package com.dieang.energym.ui.feature.home

sealed class HomeEvent {
    object LoadData : HomeEvent()
    object OnLogoutClick : HomeEvent()
    data class OnPesoSubmit(val peso: Float) : HomeEvent()
}
