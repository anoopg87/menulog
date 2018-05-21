package au.com.menulog.di

import android.content.Context
import au.com.menulog.BuildConfig
import au.com.menulog.util.ACCEPT_LANGUAGE
import au.com.menulog.util.ACCEPT_TENANT
import au.com.menulog.util.AUTHORIZATION
import au.com.menulog.util.HOST
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@Module
class OkHttpModule {
    @Provides
    @AppScope
    fun okHttpClient(cache: Cache, interceptor: Interceptor): OkHttpClient = OkHttpClient.Builder().apply {
        cache(cache)
        addInterceptor(interceptor)
    }.build()

    @Provides
    @AppScope
    fun cache(ctx: Context): Cache = Cache(ctx.cacheDir, 10 * 1024 * 1024)

    @Provides
    @AppScope
    fun interceptor(): Interceptor = Interceptor {
        return@Interceptor it.proceed(it.request().newBuilder().apply {
            addHeader(ACCEPT_LANGUAGE, BuildConfig.Accept_Language)
            addHeader(ACCEPT_TENANT, BuildConfig.Accept_Tenant)
            addHeader(AUTHORIZATION, BuildConfig.Authorization)
            addHeader(HOST, BuildConfig.Host)
        }.build())
    }
}