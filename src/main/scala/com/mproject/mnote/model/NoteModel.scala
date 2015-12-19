package com.mproject.mnote.model

import java.util.Date

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/10/11
 * Time: 22:26
 */
case class NoteModel(account:String, id:String, title:String, note:String, action:String, updated_on:Date)
