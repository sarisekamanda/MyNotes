package com.example.sarise.mynotes.view

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.sarise.mynotes.R
import com.example.sarise.mynotes.model.Note
import kotlinx.android.synthetic.main.activity_nova_note.*

class NovaNoteActivity: AppCompatActivity() {

    private var image_uri : Uri? = null
    private var mCurrentPhotoPath: String = ""

    companion object {
        // image pick code
        private val REQUEST_IMAGE_GARELLY = 1000
        private val REQUEST_IMAGE_CAPTURE = 2000
        const val EXTRA_REPLY = "view.REPLY"
        const val EXTRA_DELETE = "view.Delete"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_note)

        // botão de voltar ativo no menu superior esquerdo
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fabAddImagem.setOnCreateContextMenuListener { menu, v, menuInfo ->
            menu.add(Menu.NONE, 1, Menu.NONE, "Escolher foto")
            menu.add(Menu.NONE, 2, Menu.NONE, "Tirar foto")
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_GARELLY)
    }

    private fun takePicture() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "nova imagem")
        values.put(MediaStore.Images.Media.DESCRIPTION, "imagem da camera")
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")

        image_uri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if(intent.resolveActivity(packageManager) != null) {
            mCurrentPhotoPath = image_uri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }

        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    private fun getPermissionImageFromGallery(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
                // permission denied
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, REQUEST_IMAGE_GARELLY)
            } else {
                // permission granted
                pickImageFromGallery()
            }
        }
        else{
            // system < M
            pickImageFromGallery()
        }
    }

    private fun getPermissionTakePicture(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
                // permission denied
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, REQUEST_IMAGE_CAPTURE)
            } else {
                // permission granted
                takePicture()
            }
        }
        else{
            // system < M
            takePicture()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_IMAGE_GARELLY -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) pickImageFromGallery()
                else Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
            }
            REQUEST_IMAGE_CAPTURE ->{
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) takePicture()
                else Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_GARELLY){
            image_uri = data?.data
            imgNovaNote.setImageURI(image_uri)
        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) imgNovaNote.setImageURI(image_uri)

    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            1 -> getPermissionImageFromGallery()
            2 -> getPermissionTakePicture()
        }
        return super.onContextItemSelected(item)
    }

    // ---- MENU BUNITINHO ----

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nova_nota, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        }
        else if (item?.itemId == R.id.menu_friend_save){
            if(etTitulo.text.isNullOrEmpty()) Toast.makeText(this,
                "Insira um Título",
                Toast.LENGTH_LONG).show()
            else {
                val note = Note(
                    titulo = etTitulo.toString(),
                    conteudo = etConteudo.toString()
                )
                //criando uma intent para inserir os dados de resposta
                val replyIntent = Intent()
                // inserindo na intent a chave (EXTRA_REPLY) e o valor (friend)
                replyIntent.putExtra(EXTRA_REPLY, note)
                // enviando os dados
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
            true
        }
        else{
            super.onOptionsItemSelected(item)
        }
    }

}