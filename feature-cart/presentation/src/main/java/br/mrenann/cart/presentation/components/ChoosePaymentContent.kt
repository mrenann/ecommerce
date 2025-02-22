package br.mrenann.cart.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Car
import compose.icons.evaicons.outline.ChevronRight

@Composable
fun ChoosePaymentContent(
    navigatePop: () -> Boolean,
    goToNext: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            PaymentOption(
                icon = EvaIcons.Outline.Car, // Replace
                title = "Pix",
                subtitle = "Aprovação imediata",
                cashback = "Até R\$ 639 de cashback",
                recommended = true
            )
            HorizontalDivider(modifier = Modifier, thickness = 1.dp, color = Color.LightGray)
        }
        item {
            PaymentOption(
                icon = EvaIcons.Outline.Car, // Replace
                title = "Saldo no Mercado Pago",
                subtitle = "Saldo: R\$ 4,30",
                cashback = "Até R\$ 639 de cashback"
            )
            HorizontalDivider(modifier = Modifier, thickness = 1.dp, color = Color.LightGray)
        }

        item {
            PaymentOption(
                icon = EvaIcons.Outline.Car, // Replace
                title = "Linha de Crédito",
                subtitle = "Limite disponível: R\$ 189,10\nDe 4 a 6x sem cartão",
                cashback = "Até R\$ 639 de cashback"
            )
            HorizontalDivider(modifier = Modifier, thickness = 1.dp, color = Color.LightGray)
        }

        item {
            PaymentOption(
                icon = EvaIcons.Outline.Car, // Replace
                title = "Meli Dólar",
                subtitle = "Saldo: R\$ 9,90",
                cashback = "Até R\$ 639 de cashback",
                isNew = true

            )
            HorizontalDivider(modifier = Modifier, thickness = 1.dp, color = Color.LightGray)
        }

        item {
            BankPaymentOption(
                bankName = "BANCO INTER S.A.",
                lastDigits = "8090",
                cardType = "MasterCard"
            ) //Replace with your own icon
            HorizontalDivider(modifier = Modifier, thickness = 1.dp, color = Color.LightGray)
        }

        item {
            BankPaymentOption(bankName = "Visa", lastDigits = "6124", cardType = "VISA")
            HorizontalDivider(modifier = Modifier, thickness = 1.dp, color = Color.LightGray)
        }
        item {
            BankPaymentOption(
                bankName = "BANCO ORIGINAL SA",
                lastDigits = "2033",
                cardType = "MasterCard"
            )
            HorizontalDivider(modifier = Modifier, thickness = 1.dp, color = Color.LightGray)
        }
        item {
            BankPaymentOption(bankName = "Visa", lastDigits = "0848", cardType = "VISA")
            HorizontalDivider(modifier = Modifier, thickness = 1.dp, color = Color.LightGray)
        }

        item {
            CouponsSection()
        }
        item {
            TotalAmountSection()
        }


    }
}


@Composable
fun PaymentOption(
    icon: ImageVector,
    title: String,
    subtitle: String,
    cashback: String,
    recommended: Boolean = false,
    isNew: Boolean = false,

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .clickable { /* Handle click */ },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Important for spacing
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8F0FE)), // Light blue circle background
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = null, // Provide a meaningful description
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = title, fontWeight = FontWeight.Bold)
                    if (isNew) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "NOVO",
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(
                                    Color.Blue,
                                    shape = RoundedCornerShape(2.dp)
                                )  // Small blue tag
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
                Text(text = cashback, fontSize = 12.sp, color = Color(0xFF008000)) // Green color
            }
        }


        if (recommended) {
            Text(
                text = "RECOMENDADO",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        Color.Blue,
                        shape = RoundedCornerShape(4.dp)
                    ) // Rounded blue background
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        } else {

            Icon(
                imageVector = EvaIcons.Outline.ChevronRight,
                contentDescription = "See more",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun BankPaymentOption(bankName: String, lastDigits: String, cardType: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .clickable { /* Handle click */ },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

//            if(cardType == "VISA"){
//                Icon(
//                    modifier = Modifier.size(40.dp),
//                    painter = painterResource(id = R.drawable.ic_visa),
//                    contentDescription = "visa",
//                    tint = Color.Unspecified
//                )
//
//            }else{
//                Icon(
//                    modifier = Modifier.size(40.dp),
//                    painter = painterResource(id = R.drawable.ic_mastercard),
//                    contentDescription = "mastercard",
//                    tint = Color.Unspecified
//                )
//
//            }
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = bankName, fontWeight = FontWeight.Bold)
                Text(text = "$cardType **** $lastDigits", fontSize = 14.sp, color = Color.Gray)
                Text(text = "Até R\$ 639 de cashback", fontSize = 12.sp, color = Color(0xFF008000))
            }
        }
        Icon(
            imageVector = EvaIcons.Outline.ChevronRight,
            contentDescription = "See more",
            tint = Color.Gray
        )
    }
}


@Composable
fun CouponsSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = "Cupons (1 para aplicar)", color = Color(0xFF000080)) // Dark blue
    }
}

@Composable
fun TotalAmountSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Você pagará", color = Color.Black)
            Text(text = "R\$ 12780", color = Color.Black)
        }

    }
}