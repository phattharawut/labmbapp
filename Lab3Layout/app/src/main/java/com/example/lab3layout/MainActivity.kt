package com.example.lab3layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.lab3layout.ui.theme.Lab3LayoutTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab3LayoutTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ASS03()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun RowLayout(message1: String, message2: String, modifier: Modifier = Modifier){
    Row ( modifier = modifier.fillMaxWidth() ){
        Text(
            text = "Hello $message1!",
            style = TextStyle(background = Color.Yellow),
            fontSize = 30.sp,
            modifier = modifier
        )
        Text(
            text = "$message2!",
            style = TextStyle(background = Color.Gray),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp)

        )
        Image(
            painter = painterResource(id = R.drawable.cats),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit
        )
    }
}
@Composable
fun ColumnLayout(message1: String, message2: String, modifier: Modifier = Modifier){
    Column ( modifier = modifier.fillMaxWidth() ){
        Text(
            text = "Hello $message1!",
            style = TextStyle(background = Color.Yellow),
            fontSize = 30.sp,
            modifier = modifier
        )
        Text(
            text = "$message2!",
            style = TextStyle(background = Color.Gray),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp)

        )
        Image(
            painter = painterResource(id = R.drawable.cats),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun BoxLayout(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .background(
                color = Color(
                    red = 0.447f,
                    green = 0.878f,
                    blue = 0.933f,
                    alpha = 0.749f
                )
            )
            .fillMaxSize()
    ) {

        Text(
            text = "TopStart",
            fontSize = 20.sp,
            modifier = modifier
                .background(Color.Yellow)
                .padding(5.dp)
                .align(Alignment.TopStart)
        )

        Text(
            text = "TopCenter",
            fontSize = 20.sp,
            modifier = modifier
                .background(Color.Yellow)
                .padding(5.dp)
                .align(Alignment.TopCenter)
        )

        Text(
            text = "TopEnd",
            fontSize = 20.sp,
            modifier = modifier
                .background(Color.Yellow)
                .padding(5.dp)
                .align(Alignment.TopEnd)
        )

        Text(
            text = "CenterStart",
            fontSize = 20.sp,
            modifier = modifier
                .background(Color.Yellow)
                .padding(5.dp)
                .align(Alignment.CenterStart)
        )
        Text(
            text = "CenterStart",
            fontSize = 20.sp,
            modifier = modifier
                .background(Color(0xFFFF80CC))
                .padding(5.dp)
                .align(Alignment.Center)
        )
        Text(
            text = "CenterStart",
            fontSize = 20.sp,
            modifier = modifier
                .background(Color(0xFFFF80CC))
                .padding(5.dp)
                .align(Alignment.CenterEnd)
        )
        Text(
            text = "CenterStart",
            fontSize = 20.sp,
            modifier = modifier
                .background(Color(0xFFFF80CC))
                .padding(5.dp)
                .align(Alignment.BottomStart)
        )
        Text(
            text = "CenterStart",
            fontSize = 20.sp,
            modifier = modifier
                .background(Color(0xFFFF80CC))
                .padding(5.dp)
                .align(Alignment.BottomCenter)
        )
        Text(
            text = "CenterStart",
            fontSize = 20.sp,
            modifier = modifier
                .background(Color(0xFFFF80CC))
                .padding(5.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun BoxLayout2(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .wrapContentSize(unbounded = true, align = Alignment.CenterEnd)
            .border(
                width = 1.dp,
                color = Color.Green,
                shape = RoundedCornerShape(size = 32.dp)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.cats),
            contentDescription = "cat image",
            contentScale = ContentScale.Fit,
            modifier = modifier.size(size = 300.dp)
        )

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter),
            text = "We are cats.",
            fontSize = 30.sp,
            color = Color(red = 50, green = 50, blue = 250)
        )
    }
}

@Composable
fun ConstraintLayoutEx() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(insets = WindowInsets.statusBars)

    ) {
        val (firstText, secondText, catImage) = createRefs()

        Text(
            text = "Hello World!",
            style = TextStyle(background = Color.Yellow),
            fontSize = 30.sp,
            modifier = Modifier
                .constrainAs(firstText) {
                    centerHorizontallyTo(parent)
                }

        )

        Text(
            text = "We are cats!",
            style = TextStyle(background = Color.Gray),
            fontSize = 20.sp,
            modifier = Modifier

                .padding(all = 5.dp)
                .constrainAs(secondText) {
                    top.linkTo(catImage.bottom)
                    end.linkTo(catImage.end)
                    centerHorizontallyTo(firstText)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.cats),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(300.dp)
                .border(
                    width = 0.8.dp,
                    color = Color.Blue,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .constrainAs(catImage) {
                    top.linkTo(firstText.bottom)
                    start.linkTo(firstText.start)
                    centerHorizontallyTo(firstText)
                }
        )
    }
}

@Composable
fun BackGroundBox(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.3F
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(unbounded = true, align = Alignment.Center)
            .statusBarsPadding()

    ) {

        Image(
            painter = painterResource(id = R.drawable. _760185875589),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(size = 300.dp)
                .padding(all = 35.dp)
        )

        Text(
            text = "Happy brithday my friend\n Pie",
            fontSize = 30.sp,
            color = Color(0xFFFF80CC),
            modifier = modifier
                .padding(all = 5.dp)
                .align(Alignment.TopCenter)
        )
        Text(
            text = "Merry Catmas!",
            fontSize = 35.sp,
            color = Color.Magenta,
            modifier = modifier
                .padding(all = 5.dp)
                .align(Alignment.BottomCenter)
        )
    }
}
@Composable
fun ASS03(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5F
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(unbounded = true, align = Alignment.Center)
            .statusBarsPadding()

    ) {

        Image(
            painter = painterResource(id = R.drawable._760185875589),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(size = 300.dp)
                .padding(all = 35.dp)

        )

        Text(
            text = "Happy brithday my friend \n Pie",
            fontSize = 30.sp,
            color = Color(0xFF000000),
            modifier = modifier
                .padding(all = 5.dp)
                .align(Alignment.TopCenter)
        )

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(unbounded = true, align = Alignment.BottomEnd)
            .statusBarsPadding()

    ) {

        Image(
            painter = painterResource(id = R.drawable.studentphoto),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(size = 200.dp)
                .padding(all = 35.dp)

        )

        Text(
            text = "Form Pooh",
            fontSize = 25.sp,
            color = Color(0xFF000000),
            modifier = modifier
                .padding(all = 5.dp)
                .align(Alignment.TopCenter)
        )

    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab3LayoutTheme {
        ASS03()
    }
}