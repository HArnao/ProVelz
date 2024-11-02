package com.upao.velz.sqlite

class Tables {

    val TABLE_USER = "usuarios"
    val USER_ID = "idUsuario"
    val USER_NAME = "nombre"
    val USER_LASTNAME = "apellido"
    val USER_EMAIL = "email"
    val USER_PHONE = "phone"
    val USER_DNI = "dni"
    val USER_PASSWORD = "password"
    val USER_CREATEDAT = "createdAT"
    val USER_UPDATEDAT = "updatedAT"

    val CREATE_TABLE_USER = ("CREATE TABLE " + TABLE_USER + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_NAME + " TEXT,"
            + USER_LASTNAME + " TEXT,"
            + USER_EMAIL + " TEXT,"
            + USER_PHONE + " TEXT,"
            + USER_DNI + " TEXT,"
            + USER_PASSWORD + " TEXT,"
            + USER_CREATEDAT + " TEXT,"
            + USER_UPDATEDAT + " TEXT"
            + ")"
    )

    //TABLA DE CITAS
    val TABLE_APPOINTMENT = "appointments"
    val APPOINTMENT_ID = "id"
    val APPOINTMENT_DATE = "dateAppointment"
    val APPOINTMENT_TIME = "timeAppointment"
    val APPOINTMENT_DESCRIPTION = "description"
    val APPOINTMENT_ID_USER = "idUsuario"
    val APPOINTMENT_STATUS = "status"
    val APPOINTMENT_REMINDER = "reminder"
    val APPOINTMENT_CREATEDAT = "createdAT"
    val APPOINTMENT_UPDATEDAT = "updatedAT"

    val CREATE_TABLE_APPOINTMENT = ("CREATE TABLE " + TABLE_APPOINTMENT + "("
            + APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + APPOINTMENT_DATE + " TEXT,"
            + APPOINTMENT_TIME + " TEXT,"
            + APPOINTMENT_DESCRIPTION + " TEXT,"
            + APPOINTMENT_ID_USER + " INTEGER,"
            + APPOINTMENT_STATUS + " TEXT,"
            + APPOINTMENT_REMINDER + " INTEGER,"
            + APPOINTMENT_CREATEDAT + " TEXT,"
            + APPOINTMENT_UPDATEDAT + " TEXT, "
            + "FOREIGN KEY (" + APPOINTMENT_ID_USER + ") REFERENCES " + TABLE_USER + "(" + USER_ID + ")"
            + ")"
            )





}



