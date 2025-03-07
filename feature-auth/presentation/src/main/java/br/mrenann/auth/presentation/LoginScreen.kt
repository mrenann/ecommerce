package br.mrenann.auth.presentation

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import br.mrenann.main.presentation.MainScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.Email
import compose.icons.evaicons.fill.Lock
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.security.MessageDigest
import java.util.UUID

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        val context = LocalContext.current
        val authenticationManager = remember {
            AuthenticationManager(context)
        }
        val coroutineScope = rememberCoroutineScope()
        val navigation = LocalNavigator.currentOrThrow

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sign-in",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Please fill the form to continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.size(18.dp))
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = email,
                        maxLines = 1,
                        onValueChange = { value ->
                            email = value
                        },
                        placeholder = { Text("Email") },
                        leadingIcon = {
                            Icon(
                                imageVector = EvaIcons.Fill.Email,
                                contentDescription = "Search Icon"
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        maxLines = 1,
                        onValueChange = { value ->
                            password = value
                        },
                        placeholder = { Text("Password") },
                        leadingIcon = {
                            Icon(
                                imageVector = EvaIcons.Fill.Lock,
                                contentDescription = "Search Icon"
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
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.size(18.dp))


                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        enabled = email.isNotEmpty() && password.isNotEmpty(),
                        onClick = {
                            authenticationManager.loginWithEmail(email, password)
                                .onEach { response ->
                                    when (response) {
                                        is AuthResponse.Success -> {
                                            navigation.replace(MainScreen())
                                        }

                                        is AuthResponse.Error -> {
                                            Log.i("LoginScreen", "Error")

                                        }
                                    }
                                }.launchIn(coroutineScope)
                        }
                    ) {
                        Text("Sign-In")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            navigation.push(RegisterScreen())
//                            authenticationManager.signInWithGoogle()
//                                .onEach { response ->
//                                    when (response) {
//                                        is AuthResponse.Success -> {
//                                            Log.i("LoginScreen", "Success Google")
//                                        }
//
//                                        is AuthResponse.Error -> {
//                                            Log.i("LoginScreen", "Error Google")
//
//                                        }
//                                    }
//                                }.launchIn(coroutineScope)
                        }
                    ) {
                        Text("Create Account")
                    }
                }
            }
        }
    }
}

class AuthenticationManager(val context: Context) {
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    fun createAccountWithEmail(name: String, email: String, password: String): Flow<AuthResponse> =
        callbackFlow {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            // Criando os dados do usuário no Firestore
                            val userData = hashMapOf(
                                "name" to name, // Novo campo para Nome
                                "email" to email,
                                "createdAt" to FieldValue.serverTimestamp()
                            )

                            firestore.collection("users")
                                .document(userId)
                                .set(userData)
                                .addOnSuccessListener {
                                    trySend(AuthResponse.Success)
                                }
                                .addOnFailureListener { exception ->
                                    trySend(AuthResponse.Error(message = exception.message ?: ""))
                                }
                        } else {
                            trySend(AuthResponse.Error(message = "User ID is null"))
                        }
                    } else {
                        trySend(AuthResponse.Error(message = task.exception?.message ?: ""))
                    }
                }
            awaitClose()
        }


    fun loginWithEmail(email: String, password: String): Flow<AuthResponse> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        fetchUserName(userId) { displayName ->
                            Log.d("UserData", "Nome do usuário: $displayName")

                            // Atualiza o displayName do FirebaseAuth
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName)
                                .build()

                            auth.currentUser?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        Log.d(
                                            "UserData",
                                            "displayName atualizado para: ${auth.currentUser?.displayName}"
                                        )
                                    } else {
                                        Log.e(
                                            "UserData",
                                            "Erro ao atualizar displayName",
                                            updateTask.exception
                                        )
                                    }
                                    trySend(AuthResponse.Success) // Envia resposta apenas após a atualização
                                }
                        }
                    } else {
                        Log.e("UserData", "Usuário não autenticado")
                        trySend(AuthResponse.Success)
                    }
                } else {
                    trySend(
                        AuthResponse.Error(
                            message = task.exception?.message ?: "Erro ao fazer login"
                        )
                    )
                }
            }

        awaitClose()
    }

    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.fold("") { str, it ->
            str + "%02x".format(it)
        }
    }

    fun signInWithGoogle(): Flow<AuthResponse> = callbackFlow {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setAutoSelectEnabled(false)
            .setNonce(createNonce())
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val credentialManager = CredentialManager.create(context)
            val result = credentialManager.getCredential(
                context = context,
                request = request,
            )
            val credential = result.credential
            if (credential is CustomCredential) {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val firebaseCredential = GoogleAuthProvider
                            .getCredential(
                                googleIdTokenCredential.idToken,
                                null
                            )
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    trySend(AuthResponse.Success)
                                } else {
                                    trySend(
                                        AuthResponse.Error(
                                            message = "more inside" + it.exception?.message
                                        )
                                    )
                                }
                            }
                    } catch (e: GoogleIdTokenParsingException) {
                        trySend(AuthResponse.Error(message = "inside" + e.message))
                    }
                }
            }
        } catch (e: Exception) {
            trySend(AuthResponse.Error(message = "outside" + e.message))
        }

        awaitClose()

    }

    fun fetchUserName(userId: String, onResult: (String) -> Unit) {
        val db = Firebase.firestore
        val userRef = db.collection("users").document(userId)

        userRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val displayName = document.getString("name") ?: "No Name"
                    onResult(displayName) // Retorna o nome do usuário
                } else {
                    onResult("No Name") // Retorno padrão se não encontrar o documento
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao buscar usuário", e)
                onResult("No Name") // Retorno em caso de erro
            }
    }


}

interface AuthResponse {
    data object Success : AuthResponse
    data class Error(val message: String) : AuthResponse
}