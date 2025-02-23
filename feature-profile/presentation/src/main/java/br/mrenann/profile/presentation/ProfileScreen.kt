package br.mrenann.profile.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Person

class ProfileScreen() : Screen {
    @Composable
    override fun Content() {
        val auth = Firebase.auth
        val firestore = Firebase.firestore
        val context = LocalContext.current
        val userId = auth.currentUser?.uid
        val navigator = LocalNavigator.currentOrThrow

        var userName by remember { mutableStateOf("Carregando...") }
        var userEmail by remember { mutableStateOf("Carregando...") }

        LaunchedEffect(userId) {
            if (userId != null) {
                firestore.collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            userName = document.getString("name") ?: "Usuário"
                            userEmail = document.getString("email") ?: "E-mail não encontrado"
                        }
                    }
                    .addOnFailureListener {
                        userName = "Erro ao carregar"
                        userEmail = "Erro ao carregar"
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = EvaIcons.Outline.Person,
                    contentDescription = "Profile Icon",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .padding(20.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = userName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = userEmail,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        navigator.push(CardScreen())
                    }
                ) {
                    Text("See Cards")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        auth.signOut()
                        Toast.makeText(context, "Desconectado", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Logout")
                }
            }
        }
    }
}