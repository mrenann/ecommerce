package br.mrenann.profile.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Address
import br.mrenann.profile.presentation.components.addresses.AddressItem
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft
import kotlinx.coroutines.tasks.await

class AddressScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var addresses by remember { mutableStateOf<List<Address>>(emptyList()) }
        val strings = LocalStrings.current.profileTab.myAddresses

        LaunchedEffect(true) {
            val db = Firebase.firestore
            val userId = Firebase.auth.currentUser?.uid

            if (userId != null) {
                val userRef = db.collection("users")
                    .document(userId)
                    .get().await()
                val mainAddress = userRef.data?.get("mainAddress")

                val ref =
                    db.collection("users").document(userId).collection("addresses").get().await()
                val fetchedAddresses = ref.documents.mapNotNull {
                    it.toObject(Address::class.java)
                }
                addresses = if (mainAddress != null) {
                    fetchedAddresses.map { address ->
                        if (address.id == mainAddress) {
                            address.copy(main = true)
                        } else {
                            address
                        }
                    }
                } else {
                    fetchedAddresses
                }

            }

        }

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = EvaIcons.Outline.ChevronLeft,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1F),
                        text = strings.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )
                }

                LazyColumn(
                    modifier = Modifier.weight(1F),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(addresses) { address ->
                        AddressItem(address = address, toEdit = {
                            navigator.push(RegisterAddressScreen(address))
                        })
                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        navigator.push(RegisterAddressScreen())
                    }
                ) {
                    Text(strings.addAddress)
                }
            }
        }
    }
}
