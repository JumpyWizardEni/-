package com.example.example

import com.google.gson.annotations.SerializedName

   
data class RequestAnswer (

   @SerializedName("result") var result : List<Result>,
   @SerializedName("totalCount") var totalCount : Int

)