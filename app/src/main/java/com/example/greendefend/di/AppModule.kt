package com.example.greendefend.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @ActivityContext
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun createDataStore(@ApplicationContext context: Context):DataStore<Preferences>{
        return context.createDataStore("GreenDefend")
    }

//
//    @Provides
//    @Singleton
//    fun provideProjectName():SpannableString{
//        val span=SpannableString(R.string.green_defend.toString())
//        val fcBlack= ForegroundColorSpan(Color.BLACK)
//        val fcGreen= ForegroundColorSpan(getColor(R.color.green_name,Context.))
//        val size=getString(R.string.green_defend).length
//        span.setSpan(fcBlack,0,size/2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        span.setSpan(fcGreen,size/2,size, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//    }

}
