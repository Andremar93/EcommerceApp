package com.example.ecommerceapp.di


import com.example.ecommerceapp.domain.repository.UserRepository
import com.example.ecommerceapp.domain.use_case.user.LoginUseCase
import com.example.ecommerceapp.domain.use_case.user.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {

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

}