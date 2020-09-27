package com.example.todo_app_kotlin

import java.io.Serializable

class Todo : Serializable {

    private lateinit var content: String;
    private var isChecked = false;
    constructor(content: String, isChecked: Boolean) {
        this.content = content
        this.isChecked = isChecked
    }

   public fun getContent(): String {
       return this.content
   }
   public fun isChecked(): Boolean {
       return this.isChecked
   }
   public fun setContent(content: String) {
       this.content = content
   }
   public fun setCheked(isChecked: Boolean) {
       this.isChecked = isChecked
   }

}