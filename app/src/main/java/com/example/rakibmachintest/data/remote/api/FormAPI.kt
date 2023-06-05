package com.example.rakibmachintest.data.remote.api

import com.example.rakibmachintest.model.FormModel
import com.example.rakibmachintest.model.FormModelItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FormAPI {

    @GET("/pats/form/")
    suspend fun getForm() : Response<ArrayList<FormModelItem>>
}