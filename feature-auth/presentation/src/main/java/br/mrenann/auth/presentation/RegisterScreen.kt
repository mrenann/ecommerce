package br.mrenann.auth.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.main.presentation.MainScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.Firebase
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Email
import compose.icons.evaicons.fill.Lock
import compose.icons.evaicons.outline.ChevronLeft
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterScreen : Screen {
    @Composable
    override fun Content() {
        var name by remember { mutableStateOf("") } // Novo campo para nome
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        val context = LocalContext.current
        val authenticationManager = remember { AuthenticationManager(context) }
        val coroutineScope = rememberCoroutineScope()
        val navigation = LocalNavigator.currentOrThrow

        Scaffold { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navigation.pop() }) {
                        Icon(
                            imageVector = EvaIcons.Outline.ChevronLeft,
                            contentDescription = "Back"
                        )
                    }


                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()

                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Create Account",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Please fill the form to create your account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.size(18.dp))
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Campo para Nome
                        OutlinedTextField(
                            value = name,
                            onValueChange = { value -> name = value },
                            placeholder = { Text("Name") },
                            leadingIcon = {
                                Icon(
                                    imageVector = EvaIcons.Fill.Email,
                                    contentDescription = "User Icon"
                                )
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = email,
                            onValueChange = { value -> email = value },
                            placeholder = { Text("Email") },
                            leadingIcon = {
                                Icon(
                                    imageVector = EvaIcons.Fill.Email,
                                    contentDescription = "Email Icon"
                                )
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = password,
                            onValueChange = { value -> password = value },
                            placeholder = { Text("Password") },
                            leadingIcon = {
                                Icon(
                                    imageVector = EvaIcons.Fill.Lock,
                                    contentDescription = "Password Icon"
                                )
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { value -> confirmPassword = value },
                            placeholder = { Text("Confirm Password") },
                            leadingIcon = {
                                Icon(
                                    imageVector = EvaIcons.Fill.Lock,
                                    contentDescription = "Confirm Password Icon"
                                )
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.size(18.dp))

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword,
                            onClick = {
                                authenticationManager.createAccountWithEmail(name, email, password)
                                    .onEach { response ->
                                        when (response) {
                                            is AuthResponse.Success -> {
                                                val profileUpdates =
                                                    UserProfileChangeRequest.Builder()
                                                        .setDisplayName(name)
                                                        .build()
                                                val auth = Firebase.auth
                                                auth.currentUser?.updateProfile(profileUpdates)
                                                    ?.addOnCompleteListener { }

                                                navigation.replaceAll(MainScreen())
                                            }

                                            is AuthResponse.Error -> {
                                                Log.i("RegisterScreen", "Error")
                                            }
                                        }
                                    }.launchIn(coroutineScope)
                            }
                        ) {
                            Text("Register")
                        }
                    }
                }
            }
        }
    }
}

