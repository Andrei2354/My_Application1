package com.example.myapplication

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.OutputStreamWriter
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SaveTextToFile("ejemplo.txt")
                    guardarTextoEnArchivo("datetime", "ejemplo.txt")
                    leerTextoEnArchivo("ejemplo.txt")
                }
            }
        }
    }


    @Composable
    fun SaveTextToFile(nombreArchivo: String) {
        val datetime = "LocalDateTime.now().toString()"

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { guardarTextoEnArchivo(datetime, nombreArchivo) }
            ) {
                Text("Guardar archivo")
            }
        }
    }
    private fun guardarTextoEnArchivo(texto: String, nombreArchivo: String) {
        val estadoAlmacenamiento = Environment.getExternalStorageState()

        if (estadoAlmacenamiento == Environment.MEDIA_MOUNTED) {
            val directorio = getFilesDir()
            val archivo = File(directorio, nombreArchivo)

            try {
                val flujoSalida = FileOutputStream(archivo, true)
                val writer = OutputStreamWriter(flujoSalida)
                writer.append(texto)
                writer.close()

                Log.i("DAM2", "Leer el contenido: $directorio $nombreArchivo")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("DAM2", "Error al guardar el archivo ${e.message}")
            }
        } else {
            Log.i("DAM2", "No se pudo acceder al almacenamiento externo")
        }
    }
    private fun leerTextoEnArchivo(nombreArchivo: String) {
        val estadoAlmacenamiento = Environment.getExternalStorageState()

        if (estadoAlmacenamiento == Environment.MEDIA_MOUNTED) {
            val directorio = getFilesDir()
            val archivo = File(directorio, nombreArchivo)

            try {
                val flujoEntrada = FileReader(archivo)
                val leer = BufferedReader(flujoEntrada)
                val contenidoArchivo = leer.readLines()
                leer.close()

                Log.i("DAM2", "Leer el contenido: $contenidoArchivo")
                File(directorio, nombreArchivo).delete()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("DAM2", "Error al guardar el archivo ${e.message}")
            }
        } else {
            Log.i("DAM2", "No se pudo acceder al almacenamiento externo")
        }
    }
}