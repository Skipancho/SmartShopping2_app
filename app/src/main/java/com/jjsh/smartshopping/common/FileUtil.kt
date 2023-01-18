package com.jjsh.smartshopping.common

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object FileUtil {
   fun uriToFile(context: Context, uri: Uri) : File{
       val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
       val file = File(context.cacheDir, "${UUID.randomUUID()}.jpg")
       val outputStream: OutputStream = FileOutputStream(file)
       inputStream?.use { input ->
           outputStream.use { output ->
               input.copyTo(output)
           }
       }
       return file
   }
}