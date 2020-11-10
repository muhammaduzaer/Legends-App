package com.example.legends

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val userName: String,
    val email: String,
    val password: String
) : Parcelable