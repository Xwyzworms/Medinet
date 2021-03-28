package com.reyhanabbywahyu.medinet2.`class`

import androidx.versionedparcelable.ParcelField
import java.io.Serializable

data class User (
        val email: String , var password : String , var nama_user : String ) : Serializable