package com.example.ecommerceapp.di

import com.example.ecommerceapp.data.local.dao.UsersDao
import com.example.ecommerceapp.data.local.data_source.UsersLocalDataSourceImpl
import com.example.ecommerceapp.data.remote.UserApiService
import com.example.ecommerceapp.data.remote.data_source.UsersRemoteDataSourceImpl
import com.example.ecommerceapp.data.repository.UserRepositoryImpl
import com.example.ecommerceapp.domain.local.data_source.UsersLocalDataSource
import com.example.ecommerceapp.domain.remote.data_source.UsersRemoteDataSource
import com.example.ecommerceapp.domain.repository.UserRepository
import com.example.ecommerceapp.domain.use_case.user.GetActiveUserUseCase
import com.example.ecommerceapp.domain.use_case.user.LoginUseCase
import com.example.ecommerceapp.domain.use_case.user.RegisterUseCase
import com.example.ecommerceapp.domain.use_case.user.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UsersModule {

    @Provides
    fun provideUserRepository(
        remote: UsersLocalDataSource,
        local: UsersRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(local, remote)
    }

    @Provides
    fun provideUsersLocalDataSource(
        usersDao: UsersDao
    ): UsersLocalDataSource {
        return UsersLocalDataSourceImpl(usersDao)
    }

    @Provides
    fun provideUsersRemoteDataSource(
        apiService: UserApiService
    ): UsersRemoteDataSource {
        return UsersRemoteDataSourceImpl(apiService)
    }

    @Provides
    fun provideLoginUseCase(
        repository: UserRepository
    ): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    fun provideRegisterUseCase(
        repository: UserRepository
    ): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    fun provideGetUserUseCase(
        repository: UserRepository
    ): GetActiveUserUseCase {
        return GetActiveUserUseCase(repository)
    }

    @Provides
    fun provideUpdateUserUseCase(
        repository: UserRepository
    ): UpdateUserUseCase {
        return UpdateUserUseCase(repository)
    }

}