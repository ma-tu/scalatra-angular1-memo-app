package com.mproject.mnote.auth

import com.mproject.mnote.db.{Repository, DB}

/**
 * Created by matu on 2014/12/06.
 */
object Authentication {
  def authenticate(account:String, password:String): Option[User] = {
    try {
      val encryptPassword =
        DB.using(DB.getConnection()) { implicit conn => {
          val repo = new Repository()
          repo.getPasswordOfAccount(account)
        }}
      if (encryptPassword.isDefined){
        val cipher = new PasswordAuthCipher()
        val decryptPassword = cipher.decrypt(encryptPassword.get)

        if (password == decryptPassword){
          Some(User(account))
        }else{
          None
        }
      }else{
        None
      }
    }catch{
      case e:Exception => None
    }
  }
}
