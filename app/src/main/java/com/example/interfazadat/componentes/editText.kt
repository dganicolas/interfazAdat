package com.example.interfazadat.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun editText(texto:String, cambioTexto:(String)->Unit, marcador:String,infoError:Boolean,redondearEsquinas: Dp= 30.dp,tamano:Float = 0.93f,esPrivado:VisualTransformation= VisualTransformation.None) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(tamano),
        value = texto,
        onValueChange = { cambioTexto(it)  },
        label = { texto(
            marcador,
            bold = FontWeight.Bold,
            color = Color.Black,
            align = TextAlign.Center,
            modifier = Modifier.Companion.padding(horizontal = 16.dp)
        ) },
        shape = RoundedCornerShape(redondearEsquinas),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (infoError) Color.Green else MaterialTheme.colorScheme.error,
            unfocusedBorderColor = if (infoError) Color.Green else Color.Gray,
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        visualTransformation = esPrivado
    )
}