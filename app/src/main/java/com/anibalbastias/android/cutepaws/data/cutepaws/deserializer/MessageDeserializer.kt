package com.anibalbastias.android.cutepaws.data.cutepaws.deserializer

import com.anibalbastias.android.cutepaws.data.dataStoreFactory.breeds.model.CutePawsData
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type


class MessageDeserializer : JsonDeserializer<CutePawsData> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CutePawsData? {

        /*
            Message could be:
            - HashMap<String, ArrayList<String>>
            - ArrayList<String>
            - String
         */

        val messageObject: CutePawsData? = null
        val jsonObject = json!!.asJsonObject

        if (jsonObject.has("message")) {
            val elem = jsonObject["message"]
            if (elem != null && !elem.isJsonNull) {
                if (elem.isJsonPrimitive) {
                    messageObject?.disclaimer = elem.asString
                } else {
                    when (elem) {
                        is ArrayList<*> -> {
                            messageObject?.list = elem as ArrayList<String>
                        }
                        is HashMap<*, *> -> {
                            messageObject?.hashMap = elem as HashMap<String, ArrayList<String>>
                        }
                    }
                }
            }
        }
        return messageObject
    }

}