package com.gihan.composetutorial


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Preview(
    name = "score preview",
    showBackground = true,
)
@Composable
fun MyText() {
    Text(
        text = "elephant can see storm ",
        style = TextStyle(
            color = Color.Red,
            fontSize = 25.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center

        ),
        maxLines = 2
    )
}
@Preview(
    name = "score preview",
    showBackground = true,
)
@Preview(
    name = "score preview",
    showBackground = true,
)
@Composable
fun MyButton() {

    var buttonIsEnable by remember {
        mutableStateOf(true)
    }
    Button(onClick = { buttonIsEnable=!buttonIsEnable },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White,
            disabledContainerColor = Color.LightGray,
        ),
        enabled = buttonIsEnable
    ) {

        Text(text = if (buttonIsEnable) "Click me" else "I am Disable ")
    }
}

@Composable
fun MyTextField() {

    var email by remember { mutableStateOf("") }
    var validEmail by remember {
        mutableStateOf(true)
    }
    Column {
        TextField(
            value = email,
            onValueChange ={
                email=it
                if (email == "g@g.com")
                    validEmail=true
                else
                    validEmail=false
            },
            label = {
                Text(text = "Enter Email")
            }
        )

        Text(text = if (validEmail) "" else "Enter valid Email")
    }


}
@Preview(
    name = "score preview",
    showBackground = true,
)
@Composable
fun MyImageView() {

    Image(painter = painterResource(R.drawable.ic_launcher_background), contentDescription ="" )
}