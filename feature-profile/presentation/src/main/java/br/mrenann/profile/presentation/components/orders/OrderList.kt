package br.mrenann.profile.presentation.components.orders

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.mrenann.core.domain.model.Order

@Composable
fun OrderList(orders: List<Order>, navigateToDetails: (Order) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        items(orders) { order ->
            OrderCard(
                order = order,
                onClick = { navigateToDetails(order) }
            )
            Spacer(modifier = Modifier.height(8.dp)) // Add spacing between cards
        }
    }
}