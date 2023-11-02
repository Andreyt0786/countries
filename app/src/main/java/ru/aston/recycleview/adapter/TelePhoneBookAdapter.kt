package ru.aston.recycleview.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.paging.ItemKeyedDataSource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.aston.recycleview.R
import ru.aston.recycleview.databinding.CardTelePhoneBookBinding
import ru.aston.recycleview.dto.TelePhoneBook


interface OnInteractionListener {
    fun onEdit(telePhoneBook: TelePhoneBook) {}
    fun onRemove(telePhoneBook: TelePhoneBook) {}

    fun onCheck(telePhoneBook: TelePhoneBook) {}
}

class TelePhoneBookDiffCallback : DiffUtil.ItemCallback<TelePhoneBook>() {
    override fun areItemsTheSame(oldItem: TelePhoneBook, newItem: TelePhoneBook): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TelePhoneBook, newItem: TelePhoneBook): Boolean {
        return oldItem == newItem
    }
}

class TelePhoneBookAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<TelePhoneBook, TelePhoneBookViewHolder>(TelePhoneBookDiffCallback()) {

    private var list: MutableList<TelePhoneBook> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TelePhoneBookViewHolder {
        val binding =
            CardTelePhoneBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TelePhoneBookViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: TelePhoneBookViewHolder, position: Int) {
        val telePhoneBook = getItem(position)
        holder.bind(telePhoneBook)
    }
}

class TelePhoneBookViewHolder(
    private val binding: CardTelePhoneBookBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {



    fun bind(telePhoneBook: TelePhoneBook) {

        binding.apply {
            contactName.text = telePhoneBook.name
            contactSurname.text = telePhoneBook.surName
            telephoneUser.text = telePhoneBook.number

            check.setOnClickListener {
               if(telePhoneBook.isSelected == false) {
                   telePhoneBook.isSelected = true
               }
                else {telePhoneBook.isSelected = false}

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    binding.check.setButtonIconDrawableResource(
                        if (telePhoneBook.isSelected) R.drawable.baseline_add_circle_24 else R.drawable.baseline_add_circle_outline_24
                    )
                }
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(telePhoneBook)
                                true
                            }

                            R.id.edit -> {
                                onInteractionListener.onEdit(telePhoneBook)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            check.setOnClickListener{
                onInteractionListener.onCheck(telePhoneBook)
            }

        }
    }

}