package ru.aston.recycleview.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.aston.recycleview.dto.TelePhoneBook

class TelePhoneRepositoryImpl() : TelePhoneBookRepository {
    private var nextId = 1

    private var telePhoneBooks = listOf(
        TelePhoneBook(
            id = nextId++,
            name = "Катя",
            surName = "Иванова",
            number = "78889992255"

        ),
        TelePhoneBook(
            id = nextId++,
            name = "Петя",
            surName = "Швецов",
            number = "78889992233"

        ),
        TelePhoneBook(
            id = nextId++,
            name = "Оля",
            surName = "Петрова",
            number = "78889997799"
        ),
        TelePhoneBook(
            id = nextId++,
            name = "Сергей",
            surName = "Полунин",
            number = "78889997766"
        ),
        TelePhoneBook(
            id = nextId++,
            name = "Маша",
            surName = "Ким",
            number = "78889997711"
        ),

        )
    private val data = MutableLiveData(telePhoneBooks)

    private val dataIsCheck = MutableLiveData(telePhoneBooks)
    override fun getAll(): LiveData<List<TelePhoneBook>> = data

    override fun save(telePhoneBook: TelePhoneBook) {
        if (telePhoneBook.id == 0) {

            telePhoneBooks = listOf(
                telePhoneBook.copy(
                    id = nextId++,
                    name = telePhoneBook.name,
                    surName = telePhoneBook.surName,
                    number = telePhoneBook.number,
                )
            ) + telePhoneBooks
            data.value = telePhoneBooks
            return
        }
        telePhoneBooks = telePhoneBooks.map {
            if (it.id != telePhoneBook.id) it else it.copy(
                name = telePhoneBook.name,
                surName = telePhoneBook.surName,
                number = telePhoneBook.number,
                isSelected = telePhoneBook.isSelected,)
        }
        data.value = telePhoneBooks

    }

    override fun removeById(id: Int) {
        telePhoneBooks = telePhoneBooks.filter { it.id != id }
        data.value = telePhoneBooks
    }
}