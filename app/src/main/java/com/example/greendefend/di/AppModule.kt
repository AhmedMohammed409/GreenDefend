package com.example.greendefend.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.greendefend.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val Context.appDataStore by preferencesDataStore(Constants.APP_DATA_STORE_NAME)



    @Provides
    @ActivityContext
    fun provideContext(application: Application): Context {
        return application
    }
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.appDataStore
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
