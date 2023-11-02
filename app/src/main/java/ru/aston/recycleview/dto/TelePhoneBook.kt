package ru.aston.recycleview.dto

data class TelePhoneBook(
    val id:Int,
    val name:String,
    val surName:String,
    val number:String,
    var isSelected:Boolean = false
)


