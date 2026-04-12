package com.dieang.energym.ui.feature.auth

@Composable
fun LoginScreen(
    state: AuthState,
    onLogin: (String, String) -> Unit,
    onSuccess: () -> Unit
) {
    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) onSuccess()
    }

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { onLogin(email, pass) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        if (state.isLoading) {
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        state.error?.let {
            Spacer(Modifier.height(16.dp))
            Text(it, color = Color.Red)
        }
    }
}
