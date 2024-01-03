package com.example.greendefend.di

import android.content.Context
import androidx.window.layout.WindowMetricsCalculator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getHight( @ApplicationContext context: Context):Float{
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
        val height = metrics.bounds.height()
        return height.toFloat()
    }
    @Provides
    @Singleton
    fun getWeight( @ApplicationContext context: Context):Float{
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
        val weight = metrics.bounds.width()
        return weight.toFloat()
    }


}