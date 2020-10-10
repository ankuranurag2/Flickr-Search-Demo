package dev.ananurag2.flickr.utils

import android.content.Context
import android.util.Base64
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import java.nio.charset.Charset

/**
 * created by ankur on 31/1/20
 */
object AppUtils {

    fun hideKeyboard(activity: AppCompatActivity) {
        val view = activity.currentFocus
        view ?: return

        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun encodeToBase64(msg: String) =
        Base64.encodeToString(msg.toByteArray(Charset.defaultCharset()), Base64.DEFAULT)

    fun decodeBase64(encoded: String) = String(Base64.decode(encoded, Base64.DEFAULT), Charset.defaultCharset())
}