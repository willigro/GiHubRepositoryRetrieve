package com.rittmann.githubapiapp.extensions

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import coil.imageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import com.rittmann.githubapiapp.R

fun View.setBackgroundByBitmapPalette(bitmap: Bitmap?) {
    if (bitmap == null) return

    val builder = Palette.Builder(bitmap)
    builder.generate { palette: Palette? ->
        palette?.also {
            setBackgroundColor(
                palette.getVibrantColor(
                    androidx.core.content.ContextCompat.getColor(
                        context,
                        com.rittmann.githubapiapp.R.color.white
                    )
                )
            )
        }
    }
}

fun ImageView.loadWithPalette(
    url: String?,
    paletteTarget: View? = null,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable? {
    if (url == null) return null

    val loader = context.imageLoader
    val request = ImageRequest.Builder(context)
        .data(url)
        .target(
            onStart = { placeholder ->
                setImageDrawable(placeholder)
            },
            onSuccess = { result ->
                val bitmap = (result as BitmapDrawable).bitmap
                if (paletteTarget == null)
                    setBackgroundByBitmapPalette(bitmap)
                else
                    paletteTarget.setBackgroundByBitmapPalette(bitmap)
                setImageDrawable(result)
            },
            onError = { error ->
                setImageDrawable(error)
            }
        )
        .apply(builder)
        .build()

    return loader.enqueue(request)
}

fun ImageView.setDrawable(resId: Int, contentDescriptionValue: String? = null) {
    setImageDrawable(
        ContextCompat.getDrawable(
            context,
            R.drawable.baseline_lock_open_black_24
        )
    )
    contentDescriptionValue?.also {
        contentDescription = it
    }
}