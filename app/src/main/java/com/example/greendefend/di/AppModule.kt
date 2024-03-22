package com.example.greendefend.di

import android.content.Context
import com.example.greendefend.Constants.Companion.BaseUrlMachineLearning
import com.example.greendefend.Constants.Companion.BaseUrlServer
import com.example.greendefend.Constants.Companion.BaseUrlWeather
import com.example.greendefend.remote.ApiServiceMachineLearning
import com.example.greendefend.remote.ApiServiceServer
import com.example.greendefend.remote.ApiServiceWeather
import com.example.greendefend.remote.MyInterceptor
import com.example.greendefend.repository.DataStoreRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private var client= OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()


    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context)=DataStoreRepositoryImp(context)




    @Provides
    @Singleton
    @Named("weather")
    fun provideRetrofitWeather(): Retrofit {
        return  Retrofit.Builder().baseUrl(BaseUrlWeather)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideApiServiceWeather(@Named("weather") retrofit: Retrofit): ApiServiceWeather {
        return  retrofit.create(ApiServiceWeather::class.java)
    }


    @Provides
    @Singleton
    @Named("server")
    fun provideRetrofitServer(): Retrofit {
        return  Retrofit.Builder().baseUrl(BaseUrlServer).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideApiServiceServer(  @Named("server") retrofit: Retrofit): ApiServiceServer {
        return  retrofit.create(ApiServiceServer::class.java)
    }

    @Provides
    @Singleton
    @Named("learning")
    fun provideRetrofitMachineLearning(): Retrofit {
        return  Retrofit.Builder().baseUrl(BaseUrlMachineLearning).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideApiServiceMachineLearning(@Named("learning") retrofit: Retrofit): ApiServiceMachineLearning {
        return  retrofit.create(ApiServiceMachineLearning::class.java)
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
