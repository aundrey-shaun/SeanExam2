package ph.edu.auf.japhetong.exampart2.helpers

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ph.edu.auf.japhetong.exampart2.models.NoteModel

object PrefsHelper {
    private const val PREF_NAME = "notes_prefs"
    private const val KEY_NOTES = "notes_list"

    fun saveNotes(context: Context, notes: List<NoteModel>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(notes)
        prefs.edit().putString(KEY_NOTES, json).apply()
    }

    fun loadNotes(context: Context): MutableList<NoteModel> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_NOTES, null)
        if (json.isNullOrBlank()) {
            return mutableListOf()
        }
        return try {
            val type = object : TypeToken<List<NoteModel>>() {}.type
            val parsed: List<NoteModel>? = Gson().fromJson(json, type)
            parsed?.toMutableList() ?: mutableListOf()
        } catch (e: Exception) {
            // if parsing fails, return an empty list instead of crashing
            mutableListOf()
        }
    }
}



