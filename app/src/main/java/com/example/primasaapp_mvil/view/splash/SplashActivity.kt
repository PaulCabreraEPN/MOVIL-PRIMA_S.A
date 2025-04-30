package com.example.primasaapp_mvil.view.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.primasaapp_mvil.R
import com.example.primasaapp_mvil.ui.theme.Blue50
import com.example.primasaapp_mvil.ui.theme.Blue80
import com.example.primasaapp_mvil.ui.theme.PRIMASAAPPMóvilTheme
import com.example.primasaapp_mvil.ui.theme.Whitefb

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            setContent {
                PRIMASAAPPMóvilTheme {
                    Surface {
                        SplashNavGraph()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreenTemplate(
        imageRes = R.drawable.sales_person_2,
        title = "¡Bienvenido a PRIMA S.A!",
        description = "Facilita tu trabajo y gestiona tus ventas de forma rápida y sencilla.",
        bottomText = "¡Empecemos Ahora!",
        currentIndex = 2,
        onNextClick = { },
        onSkipClick = { },
        onPreviousClick = { }
    )
}

@Composable
fun SplashScreenTemplate(
    imageRes: Int,
    title: String,
    description: String,
    bottomText: String,
    currentIndex: Int,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit,
    onPreviousClick: () -> Unit

) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Whitefb)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onSkipClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Blue50),
                    shape = RoundedCornerShape(topStart = 30.dp, bottomStart = 20.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Saltar", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.2f),
                contentScale = ContentScale.Fit
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                colors = CardDefaults.cardColors(containerColor = Blue50)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = description,
                            fontSize = 16.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = bottomText,
                            fontSize = 16.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }

                    Column {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            repeat(3) { index ->
                                val isSelected = index == currentIndex
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .size(
                                            width = if (isSelected) 16.dp else 8.dp,
                                            height = 8.dp
                                        )
                                        .clip(CircleShape)
                                        .background(
                                            if (isSelected) Color.White else Color.White.copy(alpha = 0.4f)
                                        )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        when(currentIndex){
                             1 -> Row(
                                 modifier = Modifier.fillMaxWidth(),
                                 horizontalArrangement = Arrangement.SpaceBetween // Cambiado para que ambos botones estén a los extremos
                             ) {
                                 // Botón de retroceso
                                 IconButton(
                                     onClick = onPreviousClick, // Define esta lambda en tu código
                                     modifier = Modifier
                                         .size(56.dp)
                                         .background(Blue80, shape = CircleShape)
                                 ) {
                                     Icon(
                                         painter = painterResource(id = R.drawable.ic_arrow_forward_prev),
                                         contentDescription = null,
                                         tint = Color.White,
                                         modifier = Modifier.size(32.dp)
                                     )
                                 }

                                 // Botón de siguiente
                                 IconButton(
                                     onClick = onNextClick,
                                     modifier = Modifier
                                         .size(56.dp)
                                         .background(Blue80, shape = CircleShape)
                                 ) {
                                     Icon(
                                         painter = painterResource(id = R.drawable.ic_arrow_forward),
                                         contentDescription = null,
                                         tint = Color.White,
                                         modifier = Modifier.size(32.dp)
                                     )
                                 }
                             }

                            2 -> Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween // Cambiado para que ambos botones estén a los extremos
                            ) {
                                // Botón de retroceso
                                IconButton(
                                    onClick = onPreviousClick, // Define esta lambda en tu código
                                    modifier = Modifier
                                        .size(56.dp)
                                        .background(Blue80, shape = CircleShape)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_arrow_forward_prev),
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }

                                Button(
                                    onClick = onSkipClick,
                                    colors = ButtonDefaults.buttonColors(containerColor = Blue80),
                                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text("Finalizar", color = Color.White)
                                }
                            }

                            else -> Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(
                                    onClick = onNextClick,
                                    modifier = Modifier
                                        .size(56.dp)
                                        .background(Blue80, shape = CircleShape)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_arrow_forward),
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
