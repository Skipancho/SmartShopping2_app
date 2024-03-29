package com.jjsh.smartshopping.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.LruCache
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.jjsh.smartshopping.BuildConfig
import kotlinx.coroutines.*
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLDecoder

class ImageLoader(
    private val imageView: ImageView,
    private val context: Context? = null,
) {
    private var placeholder: Bitmap? = null
    private var errorImage: Bitmap? = null

    fun setPlaceHolder(id: Int): ImageLoader {
        placeholder = context?.let {
            (ContextCompat.getDrawable(it, id) as BitmapDrawable).bitmap
        }
        setImage(placeholder)
        return this
    }

    fun setErrorImage(id: Int): ImageLoader {
        errorImage = context?.let {
            (ContextCompat.getDrawable(it, id) as BitmapDrawable).bitmap
        }
        return this
    }

    fun loadImage(url: String) {
        if (url.isEmpty()) return
        val cacheImage : Bitmap? = cache[url]
        if (cacheImage != null) {
            setImage(cacheImage)
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            var urlConnection: HttpURLConnection? = null
            runCatching {
                urlConnection = getUrlConnection(url)
                val stream = urlConnection?.inputStream
                BitmapFactory.decodeStream(stream)
            }.mapCatching {
                cache.put(url,it)
                withContext(Dispatchers.Main) { setImage(it) }
            }.onFailure {
                Timber.e(it)
                withContext(Dispatchers.Main) { setImage(errorImage) }
            }.also {
                urlConnection?.disconnect()
            }
            this.cancel()
        }
    }

    private fun getUrlConnection(url: String): HttpURLConnection {
        val newUrl = if (url.first() == '/') "${BuildConfig.API_URL}$url" else url
        var conn = URL(newUrl).openConnection() as HttpURLConnection
        var redirectCount = 0
        while (conn.responseCode / 100 == 3 && redirectCount++ < maxRedirect) {
            val location = URLDecoder.decode(conn.getHeaderField("Location"), "UTF-8")
            val next = URL(conn.url, location)
            conn = next.openConnection() as HttpURLConnection
        }
        return conn
    }

    private fun setImage(bitmap: Bitmap?) {
        imageView.setImageBitmap(bitmap)
    }

    companion object {
        private const val maxRedirect = 5
        private val cacheSize = ((Runtime.getRuntime().maxMemory()/1024)/8).toInt()
        private val cache = object : LruCache<String, Bitmap>(cacheSize){
            override fun sizeOf(key: String?, value: Bitmap?): Int {
                val byteCount = value?.byteCount ?: 0
                return byteCount / 1024
            }
        }
    }
}