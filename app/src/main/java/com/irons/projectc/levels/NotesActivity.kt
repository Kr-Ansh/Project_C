package com.irons.projectc.levels

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.irons.projectc.R
import com.irons.projectc.databinding.ActivityNotesBinding

class NotesActivity : AppCompatActivity() {

    lateinit var notesBinding: ActivityNotesBinding

    private val PREFS_NAME = "NotesPrefs"
    private val KEY_NOTES = "notes_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        notesBinding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(notesBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadNotes()
    }

    private fun loadNotes() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedNotes = sharedPreferences.getString(KEY_NOTES, "Add a dummy note here...")
        notesBinding.etNotes.setText(savedNotes)
    }

    private fun saveNotes() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NOTES, notesBinding.etNotes.text.toString())
        editor.apply()
    }

    override fun onPause() {
        super.onPause()

        saveNotes()
    }
}