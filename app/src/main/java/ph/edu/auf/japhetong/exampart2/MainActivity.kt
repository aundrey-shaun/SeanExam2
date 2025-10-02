package ph.edu.auf.japhetong.exampart2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.auf.japhetong.exampart2.adapters.NotesAdapter
import ph.edu.auf.japhetong.exampart2.databinding.ActivityMainBinding
import ph.edu.auf.japhetong.exampart2.dialog.AddNoteDialog
import ph.edu.auf.japhetong.exampart2.helpers.PrefsHelper
import ph.edu.auf.japhetong.exampart2.models.NoteModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NotesAdapter
    private var notes = mutableListOf<NoteModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1) Inflate binding + set content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2) Load saved notes from SharedPreferences
        notes = PrefsHelper.loadNotes(this)

        // 3) Setup RecyclerView
        adapter = NotesAdapter(notes.toMutableList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // 4) Search: match title OR content
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val q = s?.toString().orEmpty()
                if (q.isBlank()) {
                    adapter.filterList(notes) // show full list
                } else {
                    val filtered = notes.filter {
                        it.title.contains(q, ignoreCase = true) ||
                                it.content.contains(q, ignoreCase = true)
                    }
                    adapter.filterList(filtered)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 5) Add Note via Dialog
        binding.fabAdd.setOnClickListener {
            val dialog = AddNoteDialog.newInstance()
            dialog.setOnNoteAddedListener { newNote ->
                notes.add(newNote)               // update source list
                adapter.filterList(notes)        // refresh adapter
                PrefsHelper.saveNotes(this, notes) // persist
            }
            dialog.show(supportFragmentManager, "AddNoteDialog")
        }

        // 6) Explicit Intent → About screen
        binding.btnOpenAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        // 7) Implicit Intent → open website
        binding.btnOpenWebsite.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, "https://developer.android.com".toUri()))
        }
    }
}


