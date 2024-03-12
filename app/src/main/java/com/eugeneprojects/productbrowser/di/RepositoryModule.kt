package com.eugeneprojects.productbrowser.di

import com.eugeneprojects.productbrowser.network.ConnectivityRepository
import com.eugeneprojects.productbrowser.network.ConnectivityRepositoryIMPL
import com.eugeneprojects.productbrowser.repository.ProductsRepository
import com.eugeneprojects.productbrowser.repository.ProductsRepositoryIMPL
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesConnectivityRepository(repositoryIMPL: ConnectivityRepositoryIMPL) : ConnectivityRepository

    @Binds
    abstract fun providesProductsRepository(repositoryIMPL: ProductsRepositoryIMPL) : ProductsRepository
}