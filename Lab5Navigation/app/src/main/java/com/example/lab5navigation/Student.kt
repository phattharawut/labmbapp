package com.example.lab5navigation


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(val id: String,
                   val name: String,
                   val age: Int,a
                   val hobby: List<String>
) : Parcelable