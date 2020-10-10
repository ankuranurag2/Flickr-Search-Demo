package dev.ananurag2.flickr.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * created by ankur on 1/2/20
 */

fun String.encrypt(key: String): String {
    val secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")
    val iv = ByteArray(16)
    val charArray = key.toCharArray()
    for (i in 0 until charArray.size) {
        iv[i] = charArray[i].toByte()
    }
    val ivParameterSpec = IvParameterSpec(iv)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)

    val encryptedValue = cipher.doFinal(this.toByteArray())
    return Base64.encodeToString(encryptedValue, Base64.DEFAULT)
}

fun String.decrypt(key: String): String {
    val secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")
    val iv = ByteArray(16)
    val charArray = key.toCharArray()
    for (i in 0 until charArray.size) {
        iv[i] = charArray[i].toByte()
    }
    val ivParameterSpec = IvParameterSpec(iv)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)

    val decryptedByteValue = cipher.doFinal(Base64.decode(this, Base64.DEFAULT))
    return String(decryptedByteValue)
}