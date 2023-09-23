import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Найти") }
    var searchValue by remember { //объект для работы с текстом, для TextField
        mutableStateOf("") //его начальное значение
    }
    var imageSrc by remember { mutableStateOf("206.png") }
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(), //заполняем всё доступное пространство
            horizontalAlignment = Alignment.CenterHorizontally, //по центру горизонтально
//            verticalArrangement = Arrangement.Center //и вертикально
        ) { // вертикальная колонка для размещения объектов
            Row(

            ){
                TextField(
                    value = searchValue, //связываем текст из поля с созданным ранее объектом
                    onValueChange = { newText -> //обработчик ввода значений в поле
                        searchValue = newText //все изменения сохраняем в наш объект
                    },
                    textStyle = TextStyle( //объект для изменения стиля текста
                        fontSize = 14.sp //увеличиваем шрифт
                    )
                    //todo make showing choice for autoinput
                )
                Button(onClick = {
                    //todo search in db and show on image or popup window with notification
                    imageSrc = "206_back.png"
                }) {
                    Text(text)
                }
            }
            Image(
                painter = painterResource(imageSrc),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
        }

    }
}

fun main() = application {
    val windowState = rememberWindowState(
        position = WindowPosition(Alignment.Center)
    )
    Window(
        state = windowState,
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
