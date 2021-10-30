package com.strink.krishimandi

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color.BLACK
import android.graphics.Color.RED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.strink.krishimandi.databinding.ActivityProfileBinding
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var imageB64: String? = null
    private var check: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("Profile", Context.MODE_PRIVATE)
        if(sharedPreferences.getBoolean("ProfileCheck", false)) {
            check = true
            val imageStr = sharedPreferences.getString("ImageVal","NoString")
            val decodeBytes = Base64.decode(imageStr,0)
            val imageBit = BitmapFactory.decodeByteArray(decodeBytes, 0 , decodeBytes.size)
            binding.storedImage.visibility = View.VISIBLE
            binding.storedImage.setImageBitmap(imageBit)
            binding.storedName.visibility = View.VISIBLE
            binding.storedName.text = sharedPreferences.getString("Name"," ").toString()
            binding.storedEmail.visibility = View.VISIBLE
            binding.storedEmail.text = sharedPreferences.getString("Email"," ").toString()
        }
        binding.profilePic.setOnClickListener {
            checkPermissionForImage()
        }
        binding.submitBtn.setOnClickListener {
            if(binding.nameInput.text!!.isEmpty() || binding.emailInput.text!!.isEmpty() || imageB64 == null) {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show()
                binding.statusTV.visibility = View.VISIBLE
                binding.statusTV.text = getString(R.string.FieldsCheck)
                binding.statusTV.setTextColor(RED)
            } else {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailInput.text.toString()).matches()) {
                    binding.statusTV.visibility = View.INVISIBLE
                    sharedPreferences.edit().putBoolean("ProfileCheck",true).apply()
                    sharedPreferences.edit().putString("ImageVal",imageB64).apply()
                    sharedPreferences.edit().putString("Name", binding.nameInput.text.toString()).apply()
                    sharedPreferences.edit().putString("Email", binding.emailInput.text.toString()).apply()
                    val imageStr = imageB64
                    val decodeBytes = Base64.decode(imageStr,0)
                    val imageBit = BitmapFactory.decodeByteArray(decodeBytes, 0 , decodeBytes.size)
                    binding.storedImage.visibility = View.VISIBLE
                    binding.storedImage.setImageBitmap(imageBit)
                    binding.storedName.visibility = View.VISIBLE
                    binding.storedName.text = binding.nameInput.text.toString()
                    binding.storedEmail.visibility = View.VISIBLE
                    binding.storedEmail.text = binding.emailInput.text.toString()
                    check = true
                } else {
                    Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show()
                    binding.statusTV.visibility = View.VISIBLE
                    binding.statusTV.text = getString(R.string.EmailValidity)
                    binding.statusTV.setTextColor(RED)
                }
            }
        }
        binding.activity2.setOnClickListener {
            if(check) {
               val intent = Intent(this,DetailActivity::class.java)
               startActivity(intent)
            } else {
                Toast.makeText(this, "Submit Your Info", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermissionForImage() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            val cameraPerm = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(
                cameraPerm,
                1002
            )
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(takePictureIntent, Companion.REQUEST_CODE)
            } else {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val takenImage = data?.extras?.get("data") as Bitmap
            imageB64 = encodeImage(takenImage)
            Log.d("Base64 Image", "onActivityResult: $imageB64")
            sharedPreferences.edit().putString("ImageVal",imageB64).apply()
            binding.profilePic.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    companion object {
        private const val REQUEST_CODE = 1002
    }
}