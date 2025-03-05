package br.mrenann.profile.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.mrenann.navigation.LocalNavigatorParent
import br.mrenann.navigation.SharedScreen
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ChevronRight
import compose.icons.evaicons.outline.CreditCard
import compose.icons.evaicons.outline.Home
import compose.icons.evaicons.outline.LogOut
import compose.icons.evaicons.outline.Person
import compose.icons.evaicons.outline.ShoppingBag

data class MenuItem(
    val title: String,
    val icon: ImageVector // Assuming you have icons as drawable resources
)

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val auth = Firebase.auth
        val firestore = Firebase.firestore
        val navigatorParent = LocalNavigatorParent.currentOrThrow
        val userId = auth.currentUser?.uid
        val navigator = LocalNavigator.currentOrThrow
        val loginScreen = rememberScreen(SharedScreen.LoginScreen)
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
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Header

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color.Black)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                tint = Color.White,
                                imageVector = EvaIcons.Outline.Person,
                                contentDescription = "Profile Picture",
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = userName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    color = Color.White,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(text = userEmail, fontSize = 14.sp, color = Color.Gray)
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))

                // Stats Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1F)
                            .clickable {
                                navigator.push(CardListScreen())
                            }
                    ) {
                        Icon(
                            imageVector = EvaIcons.Outline.CreditCard,
                            contentDescription = "My Gigs"
                        )
                        Text("My Cards")
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1F)
                            .clickable {
                                navigator.push(OrdersScreen())
                            }
                    ) {
                        Icon(
                            imageVector = EvaIcons.Outline.ShoppingBag,
                            contentDescription = "My Gigs"
                        )
                        Text("Orders")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                ListItem(
                    text = "My addresses",
                    icon = EvaIcons.Outline.Home,
                    color = Color.DarkGray,
                    onClick = {
                        navigator.push(AddressScreen())
                    })

                ListItem(
                    text = "Logout",
                    icon = EvaIcons.Outline.LogOut,
                    color = Color.Red,
                    onClick = {
                        auth.signOut()
                        navigatorParent.replace(loginScreen)
                    })
            }
        }
    }
}


@Composable
fun ListItem(text: String, icon: ImageVector, color: Color = Color.Black, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text, fontSize = 16.sp, modifier = Modifier.weight(1f))
            Icon(
                imageVector = EvaIcons.Outline.ChevronRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
        HorizontalDivider(Modifier.padding(horizontal = 12.dp), 1.dp, Color.LightGray)
    }
}