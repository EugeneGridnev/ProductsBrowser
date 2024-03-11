package com.eugeneprojects.productbrowser.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityRepository {
    val isConnected: Flow<Boolean>
}