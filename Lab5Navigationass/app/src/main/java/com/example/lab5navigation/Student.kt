package com.example.lab5navigation


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(val username: String,
                   val password: String,


                   val gender: String,
                   val email: String,
                   val birthday: String

) : Parcelable