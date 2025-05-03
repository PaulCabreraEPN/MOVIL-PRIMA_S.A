package com.example.primasaapp_mvil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

// viewmodel/UserViewModel.kt
@HiltViewModel
class UserViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val userName = dataStoreManager.nameFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    val email = dataStoreManager.emailFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    val sales_CITY = dataStoreManager.salesCityFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    val username = dataStoreManager.usernameFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )


}
