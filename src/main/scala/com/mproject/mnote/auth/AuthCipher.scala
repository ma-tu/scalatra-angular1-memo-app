package com.mproject.mnote.auth

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Base64

class PasswordAuthCipher() extends AuthCipher{
  @Override
  val secretKey = "39LnammdkwVwCqkg"
}

class CookieAuthCipher() extends AuthCipher{
  @Override
  val secretKey = "fjE7mWgqEqWD2aRj"
}

abstract class AuthCipher() {
  //abstract val
  val secretKey: String

  private def secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES")

  def encrypt(originalText: String) = {
    val originalBytes = originalText.getBytes("UTF-8")

    val cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
    val encryptBytes: Array[Byte] = cipher.doFinal(originalBytes)

    val encryptBase64: Array[Byte] = Base64.encodeBase64(encryptBytes, false)
    new String(encryptBase64)
  }

  def decrypt(encryptBase64Text:String) = {
    val encryptBytes = Base64.decodeBase64(encryptBase64Text.getBytes("UTF-8"))

    val cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
    val originalBytes: Array[Byte] = cipher.doFinal(encryptBytes)

    new String(originalBytes)
  }
}
