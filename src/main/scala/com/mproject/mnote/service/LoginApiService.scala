package com.mproject.mnote.service

import com.mproject.mnote.auth.PasswordAuthCipher
import com.mproject.mnote.db.{Repository, DB}
import com.mproject.mnote.model.RegisterModel

/**
 * Created by matu on 2014/12/06.
 */
class LoginApiService {
  def register(register:RegisterModel) = {
    val cipher = new PasswordAuthCipher()
    val encryptPassword = cipher.encrypt(register.password)

    DB.using(DB.getConnection()){implicit conn => {
      val repo = new Repository()
      repo.insertAccount(register.account, encryptPassword)
    }}
  }

  def changePassword(account:String, password: String) = {
    val cipher = new PasswordAuthCipher()
    val encryptPassword = cipher.encrypt(password)

    DB.using(DB.getConnection()){implicit conn => {
      val repo = new Repository()
      repo.changePassword(account, encryptPassword)
    }}
  }
}
