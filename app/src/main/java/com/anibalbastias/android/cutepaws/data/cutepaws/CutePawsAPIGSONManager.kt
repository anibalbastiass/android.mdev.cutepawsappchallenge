package com.anibalbastias.android.cutepaws.data.cutepaws

import com.anibalbastias.android.cutepaws.data.cutepaws.deserializer.MessageDeserializer
import com.anibalbastias.android.cutepaws.data.dataStoreFactory.common.TypeData
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class CutePawsAPIGSONManager private constructor() {

    private fun createGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    private fun createGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()

        gsonBuilder.registerTypeAdapter(TypeData::class.java, MessageDeserializer())

        return gsonBuilder
    }

    companion object {

        private var instance: CutePawsAPIGSONManager? = null

        fun create(): Gson {
            if (instance == null) {
                instance = CutePawsAPIGSONManager()
            }
            return instance!!.createGson()
        }

        fun createGsonBuilder(): GsonBuilder {
            if (instance == null) {
                instance = CutePawsAPIGSONManager()
            }
            return instance!!.createGsonBuilder()
        }
    }
}