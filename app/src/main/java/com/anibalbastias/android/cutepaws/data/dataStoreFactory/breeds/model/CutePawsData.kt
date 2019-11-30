package com.anibalbastias.android.cutepaws.data.dataStoreFactory.breeds.model

import com.anibalbastias.android.cutepaws.data.dataStoreFactory.common.TypeData
import com.google.gson.annotations.SerializedName

data class CutePawsData(

    @field:SerializedName("message")
    val message: Any? = null,

    @field:SerializedName("success")
    val success: String? = null,

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("status")
    val status: String? = null,

    // Possible message objects
    var disclaimer: String? = null,
    var list: ArrayList<String>? = null,
    var hashMap: HashMap<String, ArrayList<String>>? = null

) : TypeData()