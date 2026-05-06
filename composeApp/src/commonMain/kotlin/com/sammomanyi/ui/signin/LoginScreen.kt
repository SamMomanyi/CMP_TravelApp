package com.sammomanyi.ui.signin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.sammomanyi.presentation.feature.register.AuthNavigation
import com.sammomanyi.presentation.feature.register.SignInViewModel
import com.sammomanyi.navigation.NavRoutes
import com.sammomanyi.widgets.ErrorMessageBottomSheetDialog
import com.sammomanyi.widgets.TravenorCircleImageButton
import com.sammomanyi.widgets.TravenorSpacer
import com.sammomanyi.widgets.TravenorTextField
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(backStack: NavBackStack<NavKey>, viewModel: SignInViewModel = koinViewModel()) {

    val uiState = viewModel.uiState.collectAsState()
    val email = viewModel.email.collectAsState()
    val password = viewModel.password.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(true) {
        viewModel.navigationState.collectLatest {
            when (it) {
                is AuthNavigation.ToListing -> {
                    backStack.add(NavRoutes.Listing).apply { backStack.remove(NavRoutes.Login) }
                }

                is AuthNavigation.ToSignUp -> {
                    backStack.add(NavRoutes.SignUp)
                }

                else -> {}
            }
        }
    }

    Scaffold {
        var passwordVisibility by remember { mutableStateOf(false) }
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            uiState.value.user?.let {
                Text(it.toString())
            }
            TravenorCircleImageButton(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Arrow",
                modifier = Modifier,
                onClick = {}
            )

            TravenorSpacer(20.dp)
            Text(
                "Sign in now", modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )
            Text(
                "Please sign in to continue our app",
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            TravenorSpacer(26.dp)

            TravenorTextField(
                email.value, onValueChange = { viewModel.onEmailChange(it) },
                modifier = Modifier.testTag("login_email"),
                placeholder = {
                    Text(
                        "Email Address",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                },
            )

            TravenorSpacer(16.dp)

            TravenorTextField(
                password.value, onValueChange = { viewModel.onPasswordChange(it) },
                modifier = Modifier.testTag("login_password"),
                placeholder = {
                    Text(
                        "Password",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Image(
                        imageVector = if (!passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Password Icon",
                        modifier = Modifier.size(48.dp).padding(12.dp).clickable {
                            passwordVisibility = !passwordVisibility
                        }
                    )
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {}, modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Forgot Password?",
                        modifier = Modifier,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            AnimatedVisibility(uiState.value.isLoading) {
                CircularProgressIndicator()
            }

            Button(
                onClick = {
                    viewModel.signIn()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("login_button")
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !uiState.value.isLoading
            ) {
                Text("Sign In", modifier = Modifier.padding(vertical = 8.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Don't have an account?",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                TextButton(onClick = {
                    viewModel.onSignUpClick()
                }) {
                    Text("Sign Up", color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        uiState.value.errorMessage?.let {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = { viewModel.removeErrorMessage() },
                modifier = Modifier.padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                ErrorMessageBottomSheetDialog(
                    message = "Failed to sign in",
                    description = it,
                    buttonText = "Retry",
                    onButtonClick = {
                        viewModel.removeErrorMessage()
                    }
                )
            }
        }

    }

}

