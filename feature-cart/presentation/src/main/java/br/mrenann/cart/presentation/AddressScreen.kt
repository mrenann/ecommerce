package br.mrenann.cart.presentation

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Address
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft

data class AddressScreen(
    val address: Address? = null
) : Screen {
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
                    .padding(innerPadding)
            ) {


                AddressContent(
                    address = address,
                    navigatePop = { navigator.pop() },
                    navigateToChooseArrive = { type, address ->
                        navigator.push(
                            WhenArriveScreen(type, address)
                        )
                    })

            }
        }
    }

    @Composable
    fun AddressContent(
        navigatePop: () -> Boolean,
        navigateToChooseArrive: (String, String) -> Unit,
        address: Address?
    ) {
        val strings = LocalStrings.current.cartScreen

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navigatePop() }) {
                    Icon(
                        imageVector = EvaIcons.Outline.ChevronLeft,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = strings.deliveryMethod,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )

            }

            if (address != null) {
                AddressCard(
                    title = strings.sendToMyAddress,
                    location = "${address.street},${address.number}, ${address.district}, ${address.city}",
                    type = 1,
                    navigate = navigateToChooseArrive
                )
            } else {
                Card(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Column {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 12.dp)
                        ) {
                            Text(
                                text = strings.dontHaveMainAddress,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                }
            }
            AddressCard(
                title = strings.pickUpAgency,
                location = "224001-224499 E County Rd 345, Mooreland, OK 73852, USA",
                type = 2,
                navigate = navigateToChooseArrive
            )
        }

    }

    @Composable
    private fun AddressCard(
        title: String,
        location: String,
        type: Int,
        navigate: (String, String) -> Unit
    ) {
        val strings = LocalStrings.current.cartScreen
        val typeString = if (type == 1) "Residence" else "Agency"
        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            onClick = { navigate(typeString, location) },
            shape = RoundedCornerShape(4.dp)
        ) {
            Column {
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
                        Text(text = strings.free, color = Color(0xFF098D19))
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(location)
                    Text(if (type == 1) strings.residence else strings.agency)
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Column(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp)
                ) {
                    Text(
                        text = strings.chooseThisAddress,
                        color = Color(0xFF2481E8),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}
