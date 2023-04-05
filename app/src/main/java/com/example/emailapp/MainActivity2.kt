package com.example.emailapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class MainActivity2 : AppCompatActivity() {

    private lateinit var tempRecipient:EditText
    private lateinit var tempBody:EditText
    private lateinit var tempUser:EditText
    private lateinit var tempPassword:EditText
    private lateinit var btnEmail:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        supportActionBar?.hide()

        tempUser = findViewById(R.id.txt)
        tempPassword = findViewById(R.id.txt2)
        tempRecipient = findViewById(R.id.txt11)
        tempBody = findViewById(R.id.txt12)

        btnEmail = findViewById(R.id.send_button)

        btnEmail.setOnClickListener {
            val user = tempUser.text.toString()
            val password = tempPassword.text.toString()
            val recipient = tempRecipient.text.toString()
            val body = tempBody.text.toString()
            sendEmail(user, password, recipient, body)
        }
    }

    private fun sendEmail(user: String, password: String, recipient: String, body: String) {
        Thread {
            val properties = Properties()
            properties["mail.smtp.auth"] = "true"
            properties["mail.smtp.starttls.enable"] = "true"
            properties["mail.smtp.host"] = "smtp.gmail.com"
            properties["mail.smtp.port"] = "587"

            val session = Session.getDefaultInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(user, password)
                }
            })

            try {
                val message = MimeMessage(session)
                message.setFrom(InternetAddress(user))
                message.addRecipient(Message.RecipientType.TO, InternetAddress(recipient))
                message.subject = "This is an auto generated Email send using a Kotlin & SMTP Based Android Application, Developed by Santhosh S 200701221 - REC"
                message.setText(body)

                Transport.send(message)
                runOnUiThread {
                    Toast.makeText(applicationContext, "Message Send", Toast.LENGTH_SHORT).show()
                }
            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }.start()
    }
}