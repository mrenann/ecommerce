package br.mrenann.profile.presentation.components.addresses

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.mrenann.core.domain.model.Address
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Briefcase
import compose.icons.evaicons.fill.Home

@Composable
fun AddressItem(address: Address, toEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                toEdit()
            }
            .then(
                if (address.main) Modifier.border(
                    1.dp,
                    MaterialTheme.colorScheme.onBackground,
                    RoundedCornerShape(8.dp)
                ) else Modifier
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = if (address.type == "home") EvaIcons.Fill.Home else EvaIcons.Fill.Briefcase,
                contentDescription = "Localized description",
            )
            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = "${address.street}, ${address.number} ${address.complement ?: ""}",
                    fontWeight = FontWeight.Bold
                )
                Text(text = "${address.district}, ${address.city} - ${address.state}")
                Text(text = "CEP: ${address.code}")
            }
        }
    }
}