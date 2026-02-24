package com.dashcam.ai.privacy

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.File
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class ClipCryptoManager {

    fun encryptFile(input: File, output: File): Boolean {
        if (!input.exists()) return false
        return runCatching {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey())
            val iv = cipher.iv

            output.outputStream().buffered().use { out ->
                out.write(iv.size)
                out.write(iv)
                javax.crypto.CipherOutputStream(out, cipher).use { cipherOut ->
                    input.inputStream().buffered().use { inputStream ->
                        inputStream.copyTo(cipherOut)
                    }
                }
            }
            true
        }.getOrDefault(false)
    }

    fun decryptFile(input: File, output: File): Boolean {
        if (!input.exists()) return false
        return runCatching {
            input.inputStream().buffered().use { inputStream ->
                val ivLength = inputStream.read()
                if (ivLength <= 0) throw IllegalStateException("Invalid IV length")
                val iv = ByteArray(ivLength)
                val read = inputStream.read(iv)
                if (read != ivLength) throw IllegalStateException("Unable to read IV")

                val cipher = Cipher.getInstance(TRANSFORMATION)
                cipher.init(Cipher.DECRYPT_MODE, getOrCreateKey(), GCMParameterSpec(TAG_BITS, iv))
                javax.crypto.CipherInputStream(inputStream, cipher).use { cipherIn ->
                    output.outputStream().buffered().use { out ->
                        cipherIn.copyTo(out)
                    }
                }
            }
            true
        }.getOrDefault(false)
    }

    private fun getOrCreateKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        val existing = keyStore.getKey(KEY_ALIAS, null) as? SecretKey
        if (existing != null) return existing

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        val spec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setRandomizedEncryptionRequired(true)
            .build()
        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    companion object {
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val KEY_ALIAS = "dashcam_clip_key_v1"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val TAG_BITS = 128
    }
}
