package com.upao.velz.activities

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import com.upao.velz.controllers.UserController
import com.upao.velz.databinding.ActivityProfileBinding
import com.upao.velz.models.User
import java.util.regex.Pattern
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {

    private val userController = UserController(this)
    private lateinit var binding: ActivityProfileBinding
    private var idUsuario: Int  = 0
    private var userData: User? = null
    private var isChanged = false
    private var uri: Uri? = null
    val storageApp = FirebaseApp.getInstance("StorageApp")
    val storage = FirebaseStorage.getInstance(storageApp)
    val storageRef = storage.reference
    val pickMedia = registerForActivityResult(PickVisualMedia()) { selectedUri ->
        if (selectedUri != null) {
            uri = selectedUri
            Glide.with(this).load(uri).transform(CircleCrop()).into(binding.ivProfilePicture)
        } else {
            // NO PASA NADA
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        idUsuario = intent.getIntExtra("user_data_id", 0)

        binding.btnBack.setOnClickListener{
            finish()
        }

        val firebaseImageUrl = "https://firebasestorage.googleapis.com/v0/b/fidelio-core-platform-test.appspot.com/o/profile-users-velz%2Fdefecto%2Fperfilmobile.png?alt=media&token=7d6ac511-ce15-4f7c-9712-f828a3da73b2"

        userController.getUserById(idUsuario){ user ->
            if (user != null) {
                userData = user
                binding.etFirstName.setText(user.name)
                binding.etLastName.setText(user.lastname)
                binding.etPhone.setText("+51 ${user.phone}")
                binding.etDni.setText(user.dni)


                val imageRef = storageRef.child("profile-users-velz/user_id_${userData?.id}/profile_image_${userData?.id}.jpg")

                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(this).load(uri).transform(CircleCrop()).into(binding.ivProfilePicture)
                }.addOnFailureListener {
                    Glide.with(this).load(firebaseImageUrl).transform(CircleCrop()).into(binding.ivProfilePicture)
                }

            } else {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.etFirstName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isChanged = true
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.etLastName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isChanged = true
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isChanged = true
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.etDni.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isChanged = true
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.ivCameraIcon.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        binding.btnGuardar.setOnClickListener {

            val phone = binding.etPhone.text.toString().replace("+51 ", "") // Limpiar el prefijo
            val dni = binding.etDni.text.toString()

            if (!isPhoneValid(phone)) {
                Toast.makeText(this, "Número de teléfono no válido. Debe tener 9 dígitos y comenzar con un 9.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isDniValid(dni)) {
                Toast.makeText(this, "Número de DNI no válido. Debe tener 8 dígitos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(uri != null){
                uploadImageToFirebase(uri!!)
            }

            if (isChanged) {
                val updatedUser = User(
                    id = idUsuario,
                    name = binding.etFirstName.text.toString(),
                    lastname = binding.etLastName.text.toString(),
                    email = " ",
                    phone = binding.etPhone.text.toString().replace("+51 ", ""),
                    dni = binding.etDni.text.toString(),
                    password = ""
                )

                userController.edtiUserProfile(idUsuario, updatedUser)

                Toast.makeText(this, "Cambios realizados con éxito", Toast.LENGTH_SHORT).show()

                finish()

            } else {
                Toast.makeText(this, "No se han realizado cambios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val userFolderRef = storageRef.child("profile-users-velz/user_id_${userData?.id}")
        val imageRef = userFolderRef.child("profile_image_${userData?.id}.jpg")

        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            Toast.makeText(this, "Imagen subida exitosamente", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { exception ->
            Log.e("FirebaseUpload", "Error al subir la imagen: ${exception.message}", exception)
            Toast.makeText(this, "Error al subir la imagen: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isPhoneValid(phone: String): Boolean {
        val phonePattern = "^9\\d{8}\$"
        return Pattern.matches(phonePattern, phone) && phone.length == 9
    }

    private fun isDniValid(dni: String): Boolean {
        val dniPattern = "^[0-9]{8}\$"
        return Pattern.matches(dniPattern, dni) && dni.length == 8
    }


}