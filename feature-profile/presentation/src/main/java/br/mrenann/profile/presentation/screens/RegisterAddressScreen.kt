package br.mrenann.profile.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.core.domain.model.Address
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronLeft

data class RegisterAddressScreen(
    val address: Address? = null
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var street by remember { mutableStateOf(address?.street ?: "") }
        var number by remember { mutableStateOf(address?.number?.toString() ?: "") }
        var district by remember { mutableStateOf(address?.district ?: "") }
        var complement by remember { mutableStateOf(address?.complement ?: "") }
        var city by remember { mutableStateOf(address?.city ?: "") }
        var state by remember { mutableStateOf(address?.state ?: "") }
        var code by remember { mutableStateOf(address?.code ?: "") }
        var isMain by remember { mutableStateOf(address?.main ?: true) }


        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                        text = if (address != null) "Edit Address" else "Add Address",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Address Form
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1F),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Main Address")
                            Switch(
                                checked = isMain,
                                onCheckedChange = { isMain = it }
                            )
                        }

                        OutlinedTextField(
                            value = street,
                            onValueChange = { street = it },
                            placeholder = { Text("Street") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                        )

                        OutlinedTextField(
                            value = number,
                            onValueChange = { number = it },
                            placeholder = { Text("Number") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                        )

                        OutlinedTextField(
                            value = district,
                            onValueChange = { district = it },
                            placeholder = { Text("District") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                        )

                        OutlinedTextField(
                            value = city,
                            onValueChange = { city = it },
                            placeholder = { Text("City") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                        )

                        OutlinedTextField(
                            value = state,
                            onValueChange = { state = it },
                            placeholder = { Text("State") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                        )

                        OutlinedTextField(
                            value = code,
                            onValueChange = { code = it },
                            placeholder = { Text("Postal Code") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                        )

                        OutlinedTextField(
                            value = complement,
                            onValueChange = { complement = it },
                            placeholder = { Text("Complement") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                        )


                    }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Botão de Remover (Aparece apenas quando editando um endereço existente)
                        if (address != null) {
                            Button(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                onClick = {
                                    val db = Firebase.firestore
                                    val userId = Firebase.auth.currentUser?.uid ?: return@Button

                                    db.collection("users").document(userId)
                                        .collection("addresses")
                                        .document(address.id)
                                        .delete()
                                        .addOnSuccessListener {
                                            navigator.pop()
                                        }
                                        .addOnFailureListener {
                                            Log.e("Firestore", "Failed to delete address", it)
                                        }
                                }
                            ) {
                                Text("Remove Address")
                            }
                        }

                        // Botão de Salvar
                        Button(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            enabled = street.isNotEmpty() && number.isNotEmpty() &&
                                    district.isNotEmpty() && city.isNotEmpty() &&
                                    state.isNotEmpty() && code.isNotEmpty(),
                            onClick = {
                                val db = Firebase.firestore
                                val userId = Firebase.auth.currentUser?.uid ?: return@Button

                                val ref = if (address != null) {
                                    db.collection("users").document(userId).collection("addresses")
                                        .document(address.id)
                                } else {
                                    db.collection("users").document(userId).collection("addresses")
                                        .document()
                                }


                                val updatedAddress = Address(
                                    id = ref.id,
                                    street = street,
                                    number = number.toInt(),
                                    district = district,
                                    complement = complement,
                                    city = city,
                                    state = state,
                                    code = code,
                                    type = address?.type ?: "home",
                                    main = false
                                )

                                ref.set(updatedAddress)
                                    .addOnSuccessListener {
                                        if (isMain) {
                                            db.collection("users").document(userId)
                                                .update("mainAddress", ref.id)
                                                .addOnSuccessListener {
                                                    // If everything is successful, go back to the previous screen
                                                    navigator.pop()
                                                }
                                                .addOnFailureListener {
                                                    Log.e(
                                                        "Firestore",
                                                        "Failed to update main address",
                                                        it
                                                    )
                                                }
                                        } else {
                                            navigator.pop() // Go back if no changes are made to the main address
                                        }
                                    }
                                    .addOnFailureListener {
                                        Log.e("Firestore", "Failed to save address", it)
                                    }
                            }
                        ) {
                            Text("Save Address")
                        }
                    }

                }
            }
        }
    }
}
