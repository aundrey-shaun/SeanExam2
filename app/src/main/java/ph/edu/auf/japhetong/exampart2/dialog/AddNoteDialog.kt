package ph.edu.auf.japhetong.exampart2.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ph.edu.auf.japhetong.exampart2.databinding.DialogAddNoteBinding
import ph.edu.auf.japhetong.exampart2.models.NoteModel

class AddNoteDialog : DialogFragment() {

    private var listener: ((NoteModel) -> Unit)? = null

    fun setOnNoteAddedListener(callback: (NoteModel) -> Unit) {
        listener = callback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogAddNoteBinding.inflate(layoutInflater)

        return AlertDialog.Builder(requireContext())
            .setTitle("Add Note")
            .setView(binding.root)
            .setPositiveButton("Add") { _, _ ->
                val title = binding.etTitle.text?.toString().orEmpty()
                val content = binding.etContent.text?.toString().orEmpty()
                if (title.isNotBlank() || content.isNotBlank()) {
                    listener?.invoke(NoteModel(title, content))
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    companion object {
        fun newInstance(): AddNoteDialog = AddNoteDialog()
    }
}
