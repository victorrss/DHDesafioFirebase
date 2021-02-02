package com.digitalhouse.desafio4firebase.game.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.desafio4firebase.R
import com.digitalhouse.desafio4firebase.databinding.ActivityAddGameBinding
import com.digitalhouse.desafio4firebase.game.service.cr
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import java.util.*
import kotlin.collections.HashMap

class AddGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGameBinding
    private lateinit var image: String

    private var TAG = "AddGame"

    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    private val CODE_IMG = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        image = ""

        alertDialog = SpotsDialog.Builder().setContext(this).build()

        binding.cvUploadPhoto.setOnClickListener { setIntent() }

        binding.cvAddGame.btnSave.setOnClickListener {
            try {
                validate()
                var game = getData()
                sendGame(game, this)
            } catch (ex: Exception) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    ex.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Upload"), CODE_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG && data != null) {
            storageReference = FirebaseStorage.getInstance().getReference()
            var gamesRef = storageReference.child(UUID.randomUUID().toString());
            val uploadTask = gamesRef.putFile(data!!.data!!)

            //LOADING ENABLE
            setContentView(R.layout.loading_display)
            uploadTask.continueWithTask { task ->
                gamesRef.downloadUrl
            }.addOnCompleteListener { task ->
                val downloadUri = task.result
                val url = downloadUri!!.toString()
                    .substring(0, downloadUri.toString().indexOf("&token"))
                image = url
                alertDialog.dismiss()

                if (!url.isNullOrEmpty())
                    Picasso
                        .get()
                        .load(url)
                        .into(binding.cvUploadPhotoImageView)
                else binding.cvUploadPhotoImageView.setImageResource(R.drawable.ic_camera)

                //LOADING DISABLE
                setContentView(binding.root)
            }
        } else {
            image = ""
            binding.cvUploadPhotoImageView.setImageResource(R.drawable.ic_camera)
        }
    }

    fun validate() {
        if (binding.cvAddGame.itname.text.toString().isNullOrEmpty())
            throw Exception("Informe o nome")
        if (binding.cvAddGame.itcreated.text.toString().isNullOrEmpty())
            throw Exception("Informe a data")
        if (binding.cvAddGame.itdescription.text.toString().isNullOrEmpty())
            throw Exception("Informe a descrição")
    }

    fun getData(): MutableMap<String, Any> {
        val game: MutableMap<String, Any> = HashMap()

        game["name"] = binding.cvAddGame.itname.text.toString()
        game["year"] = binding.cvAddGame.itcreated.text.toString()
        game["description"] = binding.cvAddGame.itdescription.text.toString()
        game["url"] = image

        return game
    }

    fun sendGame(game: MutableMap<String, Any>, context: AppCompatActivity) {
        val name = binding.cvAddGame.itname.text.toString()
        cr.document(name).set(game).addOnSuccessListener {
            Log.d(TAG, "Success send game")
            context.onBackPressed()
        }.addOnFailureListener {
            Log.i(TAG, it.toString())
        }
    }
}