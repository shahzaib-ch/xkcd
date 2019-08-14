package my.test.xkcd.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import my.test.xkcd.BuildConfig
import my.test.xkcd.utils.AppWebServices.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
 * Created by shahzaib on 8/14/2019 .
 */
object ApiFactory {


    private fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideHttpLoggingInterceptor())
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .build()
    }

    // interceptor to log all api results
    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {

        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                val gson = GsonBuilder().setPrettyPrinting().create()
                try {
                    val jsonElement = JsonParser().parse(message)
                    Timber.d(gson.toJson(jsonElement))
                } catch (exception: JsonSyntaxException) {
                    Timber.e(exception)
                }
            }
        })

        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

    // method to return ApiInterface to get
    fun provideApi(): ApiInterface {
        return provideRetrofit(BASE_URL).create(ApiInterface::class.java)
    }
}