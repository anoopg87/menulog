package au.com.menulog.di

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class PicassoModule {

    @Provides
    @AppScope
    fun provideOkHttpDownloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }

    @Provides
    @AppScope
    fun providePicasso(ctx: Context, okHttp3Downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(ctx)
                .downloader(okHttp3Downloader)
                .build()
    }
}