package com.nagy.assessment.profile.presentation

sealed class ProfileEvent {
    object RequestInitialRandomUser: ProfileEvent()
}