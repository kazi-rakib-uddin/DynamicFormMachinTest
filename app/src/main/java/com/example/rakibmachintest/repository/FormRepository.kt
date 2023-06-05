package com.example.rakibmachintest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rakibmachintest.data.remote.api.FormAPI
import com.example.rakibmachintest.model.FormModel
import com.example.rakibmachintest.model.FormModelItem
import com.example.rakibmachintest.utils.NetworkResult
import javax.inject.Inject

class FormRepository @Inject constructor(private val formAPI: FormAPI) {

    private val _formLiveData = MutableLiveData<NetworkResult<List<FormModelItem>>>()
    val formLiveData get() = _formLiveData


    suspend fun getForm() {

        val response = formAPI.getForm()
        if (response.isSuccessful && response.body() != null) {
            _formLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else {
            _formLiveData.postValue(NetworkResult.Error("somthing went wromg"))
        }
    }

}