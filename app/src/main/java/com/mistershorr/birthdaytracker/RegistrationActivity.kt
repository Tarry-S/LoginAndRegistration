package com.mistershorr.birthdaytracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mistershorr.birthdaytracker.databinding.ActivityRegistrationBinding
import java.util.*
import com.backendless.BackendlessUser
import com.backendless.exceptions.BackendlessFault

import com.backendless.async.callback.AsyncCallback

import com.backendless.Backendless

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // access any values that were sent to us from the intent that launched this activity
        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME)
        val password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD)
        Toast.makeText(this, "user:$username pwd $password", Toast.LENGTH_SHORT).show()


        binding.buttonRegistrationRegister.setOnClickListener {
            // TODO: verify that the information they entered is valid

            // in a real app, we'd talk to a database here and save the new user

            // return to the Login Screen and send back the user & pass to prefill
            if(!RegistrationUtil.validateUsername(
                    binding.editTextRegistrationName.text.toString())) {
                Toast.makeText(this, "Invalid name", Toast.LENGTH_SHORT).show()
            }

            else {
                registerUser()
            }// closes the activity
        }
    }

    private fun registerUser() {
        val user = BackendlessUser()
        user.setProperty("email", binding.editTextRegistrationEmail.text.toString())
        user.setProperty("username", binding.editTextRegistrationUsername.text.toString())
        user.setProperty("name", binding.editTextRegistrationName.text.toString())
        user.setProperty("password", binding.editTextRegistrationPassword.text.toString())
        Backendless.UserService.register(user, object : AsyncCallback<BackendlessUser?> {
            override fun handleResponse(registeredUser: BackendlessUser?) {
                // user has been registered and now can login
                Toast.makeText(this@RegistrationActivity,
                    "${registeredUser?.getProperty("username")} has been registered",
                    Toast.LENGTH_SHORT).show()
                returnToLogin()
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Toast.makeText(this@RegistrationActivity, "Error ${fault.message}",
                    Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun returnToLogin() {
        var returnToLoginIntent = Intent().apply {
            putExtra(
                LoginActivity.EXTRA_USERNAME,
                binding.editTextRegistrationUsername.text.toString()
            )
            putExtra(
                LoginActivity.EXTRA_PASSWORD,
                binding.editTextRegistrationPassword.text.toString()
            )
        }
        setResult(Activity.RESULT_OK, returnToLoginIntent)
        finish()
    }
}










