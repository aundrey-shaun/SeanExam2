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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notes = PrefsHelper.loadNotes(this)

        adapter = NotesAdapter(notes)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // TextWatcher version (no KTX needed)
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val q = s?.toString().orEmpty()
                val filtered = notes.filter { it.title.contains(q, ignoreCase = true) }
                adapter.filterList(filtered)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.fabAdd.setOnClickListener {
            val dialog = AddNoteDialog.newInstance()
            dialog.setOnNoteAddedListener { newNote ->
                notes.add(newNote)
                adapter.addNewItem(newNote)
                PrefsHelper.saveNotes(this, notes)
            }
            dialog.show(supportFragmentManager, "AddNoteDialog")
        }

        binding.btnOpenAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        binding.btnOpenWebsite.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, "https://developer.android.com".toUri()))
        }
    }
}
