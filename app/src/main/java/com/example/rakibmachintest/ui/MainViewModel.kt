package com.example.rakibmachintest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rakibmachintest.repository.FormRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

    private val formRepository: FormRepository

) : ViewModel() {

    val formLiveData get() = formRepository.formLiveData


    fun getAllForm() {
        viewModelScope.launch {
            formRepository.getForm()
        }
    }

}