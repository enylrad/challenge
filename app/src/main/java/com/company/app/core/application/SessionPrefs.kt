package com.company.app.core.application

import android.content.Context
import com.company.app.BuildConfig
import com.company.app.commons.data.local.entity.RequestEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.securepreferences.SecurePreferences


/**
 * Class that manages the sharedpreferences
 */
class SessionPrefs private constructor(val context: Context) {

    private val mPrefs: SecurePreferences by lazy {
        SecurePreferences(context)
    }

    private var mRequestList: MutableList<RequestEntity> = mutableListOf()

    fun saveRequestLists(languageEntities: MutableList<RequestEntity>) {
        mRequestList = languageEntities
        mPrefs.edit().putString(PREFS, Gson().toJson(languageEntities)).commit()
    }

    fun getRequestLists(): MutableList<RequestEntity> {
        val json = mPrefs.getString(PREFS, null)
        return if (json != null) {
            if (mRequestList.isEmpty()) {
                val myDataType = object : TypeToken<List<RequestEntity>>() {}.type
                mRequestList = Gson().fromJson(json, myDataType)
            }
            mRequestList
        } else {
            mutableListOf()
        }
    }


    companion object {

        private val TAG = SessionPrefs::class.java.name
        private const val PREFS = BuildConfig.SHARED_PREFS_APP

        fun getInstance(context: Context): SessionPrefs = SessionPrefs(context)
    }
}


