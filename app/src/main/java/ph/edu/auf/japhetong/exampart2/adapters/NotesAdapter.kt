package ph.edu.auf.japhetong.exampart2.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.auf.japhetong.exampart2.databinding.ItemNoteBinding
import ph.edu.auf.japhetong.exampart2.models.NoteModel

class NotesAdapter(private val source: MutableList<NoteModel>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val visible = mutableListOf<NoteModel>().apply { addAll(source) }

    inner class NoteViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = visible[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.tvContent.text = item.content
    }

    override fun getItemCount(): Int = visible.size

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filtered: List<NoteModel>) {
        visible.clear()
        visible.addAll(filtered)
        notifyDataSetChanged()
    }

    fun addNewItem(item: NoteModel) {
        source.add(item)
        // Refresh visible with full list by default
        filterList(source)
    }
}





