package com.mproject.mnote.auth

import org.junit.runner.RunWith
import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.junit.JUnitRunner

/**
 * Created by matu on 2014/12/05.
 */
@RunWith(classOf[JUnitRunner])
class AuthCipherTest extends FlatSpec with Matchers {

  "encode and decode" should "passwordAuthCipher" in {

    val cipher = new PasswordAuthCipher()

    val passwordText = "abcdef12345"
    val encryptText = cipher.encrypt(passwordText)
    val decryptText = cipher.decrypt(encryptText)

    passwordText should be(decryptText)
  }

  "encode and decode" should "CookeiAuthCipher" in {

    val cipher = new CookieAuthCipher()

    val passwordText = "abcdef12345"
    val encryptText = cipher.encrypt(passwordText)
    val decryptText = cipher.decrypt(encryptText)

    passwordText should be(decryptText)
  }
}
