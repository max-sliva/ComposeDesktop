import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.util.*

fun searchItem(
    things: Array<Thing>?,
    searchValue: String,
    storageNameToPngHashMap: HashMap<StorageName, String>
): String {
    var imageSrc = ""
    things!!.forEach {
        if (it is Item && it.name.lowercase(Locale.getDefault()) == searchValue.lowercase(Locale.getDefault())) {
            imageSrc = storageNameToPngHashMap[it.place.name].toString()
            //                            println("${storageNameToPngHashMap[it.place.name]}")

        }
    }
    return imageSrc
}

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Найти") }
    var searchValue by remember { //объект для работы с текстом, для TextField
        mutableStateOf("") //его начальное значение
    }
    var storageNameToPngHashMap = HashMap<StorageName, String>()
    //BACK_SHELF, CENTER_TABLES, TABLE_AT_DOOR, CUSTOM_PLACE
    storageNameToPngHashMap[StorageName.BACK_SHELF] = "206_backShelf.png"
    storageNameToPngHashMap[StorageName.CENTER_TABLES] = "206_center.png"
    var imageSrc by remember { mutableStateOf("206.png") }
    val dataHolder = DataHolder()
    val things = dataHolder.getData()
    var arrayOfNames = dataHolder.getItemNames()
    var namesList = arrayOfNames.toMutableList()
    val textStyle = TextStyle(fontSize = 20.sp)
    var isVisible by remember { mutableStateOf(false) }
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(), //заполняем всё доступное пространство
            horizontalAlignment = Alignment.CenterHorizontally, //по центру горизонтально
//            verticalArrangement = Arrangement.Center //и вертикально
        ) { // вертикальная колонка для размещения объектов
            Row() {
                TextField(
                    value = searchValue, //связываем текст из поля с созданным ранее объектом
                    onValueChange = { newText -> //обработчик ввода значений в поле
                        searchValue = newText //все изменения сохраняем в наш объект
//                        namesList.forEach { print("$it ") }
                        if (newText.length >= 3) {
                            namesList.clear()
                            isVisible = true
                            arrayOfNames.forEach {
                                var accept = false
                                it.split(" ").forEach { word ->
                                    if (word.lowercase(Locale.getDefault()).startsWith(newText.lowercase())) accept = true
                                    //todo add check for several words
                                }
                                if (accept) namesList.add(it)
                            }
                        } else isVisible = false

                    },
                    textStyle = TextStyle( //объект для изменения стиля текста
                        fontSize = 14.sp //увеличиваем шрифт
                    ),
                    placeholder = { Text(text = "Введите текст для поиска") }
                )
                Button(onClick = {
                    //todo add popup window with notification if no item found
                    imageSrc = searchItem(things, searchValue, storageNameToPngHashMap)
                }) {
                    Text(text)
                }
            }
            if (isVisible) LazyRow() {
                items(namesList) { name ->
                    Text(
                        text = name,
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable(onClick = {
                                searchValue = name
                                imageSrc = searchItem(things, searchValue, storageNameToPngHashMap)
                            })
                            .border(BorderStroke(2.dp, Color.Gray))
                            .padding(4.dp)
                    )
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
