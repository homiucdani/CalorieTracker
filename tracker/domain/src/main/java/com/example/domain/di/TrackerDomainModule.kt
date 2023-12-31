package com.example.domain.di

import com.example.core.domain.preferences.Preferences
import com.example.domain.repository.TrackerRepository
import com.example.domain.use_case.CalculateMealNutrients
import com.example.domain.use_case.DeleteTrackedFood
import com.example.domain.use_case.GetFoodsForDate
import com.example.domain.use_case.SearchFood
import com.example.domain.use_case.TrackFood
import com.example.domain.use_case.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTrackerUseCases(
        trackerRepository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCases {
        return TrackerUseCases(
            calculateMealNutrients = CalculateMealNutrients(preferences),
            deleteTrackedFood = DeleteTrackedFood(repository = trackerRepository),
            getFoodsForDate = GetFoodsForDate(repository = trackerRepository),
            searchFood = SearchFood(repository = trackerRepository),
            trackFood = TrackFood(repository = trackerRepository),
        )
    }
}