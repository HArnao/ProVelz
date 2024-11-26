package com.upao.velz.apiLaravel

import com.upao.velz.models.RequestModel.AppointmentRequest
import com.upao.velz.models.RequestModel.LoginRequest
import com.upao.velz.models.RequestModel.PaymentRequest
import com.upao.velz.models.RequestModel.UserRequest
import com.upao.velz.models.User
import com.upao.velz.models.responseModel.AppDetailResponse
import com.upao.velz.models.responseModel.AppIdResponse
import com.upao.velz.models.responseModel.AppointmentResponse
import com.upao.velz.models.responseModel.DentistResponse
import com.upao.velz.models.responseModel.ListAppResponse
import com.upao.velz.models.responseModel.PaymentResponse
import retrofit2.Response
import com.upao.velz.models.responseModel.TreatmentResponse
import com.upao.velz.models.responseModel.UserResponse
import com.upao.velz.models.responseModel.payment.ListPaymentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("treatment")
    suspend fun getTreatments(): Response<List<TreatmentResponse>>

    @POST("user/register")
    suspend fun registerUser(@Body userRequest: User): Response<UserResponse>

    @GET("user/email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<UserResponse>

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<UserResponse>

    @POST("user/login")
    suspend fun loginUser(@Body userRequest: LoginRequest): Response<UserResponse>

    @POST("appointment/add")
    suspend fun addAppointment(@Body appointRequest: AppointmentRequest): Response<AppIdResponse>

    @PUT("appointment/edit/{id}")
    suspend fun editAppointment(@Path("id") id: Int, @Body appointmentRequest: AppointmentRequest): Response<AppIdResponse>

    @GET("appointment/list")
    suspend fun getAppointment(): Response<ListAppResponse>

    @GET("appointment/list/{id}")
    suspend fun getListAppointment(@Path("id") id: Int): Response<ListAppResponse>

    @POST("payment/add")
    suspend fun addPayment(@Body paymentRequest: PaymentRequest): Response<PaymentResponse>

    @GET("payment/list/{userId}")
    suspend fun getListPayments(@Path("userId") id: Int): Response<ListPaymentResponse>

    @PUT("user/edit/{id}")
    suspend fun editUser(@Path("id") id: Int, @Body userRequest: UserRequest): Response<UserResponse>

    @GET("dentist")
    suspend fun getDentists(): Response<List<DentistResponse>>

}