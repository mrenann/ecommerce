package br.mrenann.cart.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Address
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class PreparingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var mainAddress by remember { mutableStateOf<Address?>(null) }

        // Navigate to AddressScreen after 3 seconds
        LaunchedEffect(Unit) {

            // Fetch user data and get the mainAddress
            val db = Firebase.firestore
            val userId = Firebase.auth.currentUser?.uid

            if (userId != null) {
                // Fetch mainAddress ID
                val userRef = db.collection("users").document(userId).get().await()

                val mainAddressId = userRef.data?.get("mainAddress") as? String

                if (mainAddressId != null) {
                    // Fetch the address using the mainAddressId
                    val addressRef = db.collection("users")
                        .document(userId)
                        .collection("addresses")
                        .document(mainAddressId)
                        .get()
                        .await()

                    mainAddress = addressRef.toObject(Address::class.java)
                }
            }

            delay(3000) // 3 seconds delay

            // Replace with AddressScreen after the delay, pass the mainAddress data if needed
            navigator.replace(AddressScreen(mainAddress)) // You can pass `mainAddress` if you want
        }

        // Display the preparing content and the fetched address if needed
        PreparingScreenContent(mainAddress)
    }
}

@Composable
fun PreparingScreenContent(mainAddress: Address?) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF00C236))
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = LocalStrings.current.cartScreen.preparingEverithingForYou,
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}

@Composable
@Preview
fun PreparingScreenPreview() {
    PreparingScreenContent(mainAddress = Address(
        street = "123 Main St",
        city = "Some City",
        state = "Some State",
        code = "12345",
        type = "home",
        main = true
    )
    )
}
