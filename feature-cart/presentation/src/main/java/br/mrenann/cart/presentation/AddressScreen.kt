package br.mrenann.cart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft

class AddressScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F8FE))
                    .padding(innerPadding)
            ) {


                AddressContent(navigatePop = { navigator.pop() }, navigateToChooseArrive = { address->
                    navigator.push(
                        WhenArriveScreen(address)
                    )
                })

            }
        }
    }

    @Composable
    fun AddressContent(
        navigatePop: () -> Boolean,
        navigateToChooseArrive: (String) -> Unit
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navigatePop() }) {
                    Icon(
                        tint = Color.Black,
                        imageVector = EvaIcons.Outline.ChevronLeft,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Delivery method",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )

            }

            AddressCard(
                title = "Send to my address",
                location = "100-198 W Commercial St, Langston, OK 73050, USA",
                type = "Residence",
                navigate = navigateToChooseArrive
            )
            AddressCard(
                title = "Pick up at Ecommerce Agency",
                location = "224001-224499 E County Rd 345, Mooreland, OK 73852, USA",
                type = "Agency",
                navigate = navigateToChooseArrive
            )
        }

    }

    @Composable
    private fun AddressCard(title: String, location: String, type: String, navigate: (String) -> Unit) {
        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            onClick = { navigate(type) },
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(modifier = Modifier.background(Color.White)) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "Free", color = Color(0xFF098D19))
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(location)
                    Text(type)
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Column(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp)
                ) {
                    Text(
                        text = "Choose this Address",
                        color = Color(0xFF2481E8),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }


    @Composable
    @Preview
    fun AddressContentPreview() {
        AddressContent({ false }, {})
    }
}
