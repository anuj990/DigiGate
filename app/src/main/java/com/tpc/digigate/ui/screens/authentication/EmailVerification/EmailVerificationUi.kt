package com.tpc.digigate.ui.screens.authentication.EmailVerification

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnrememberedMutableState")
@Composable
fun EmailVerificationScreen(
    viewModel: EmailVerificationViewModel,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState

    androidx.compose.runtime.LaunchedEffect(uiState.emailSent, uiState.errorMessage) {
        if (uiState.emailSent) {
            Toast.makeText(context, "Email Sent Successfully", Toast.LENGTH_SHORT).show()
        }
        if (uiState.errorMessage != null) {
            Toast.makeText(context, "Error: ${uiState.errorMessage} ", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = com.tpc.digigate.R.drawable.emailverification),
                contentDescription = "email verification Page",
                modifier = Modifier.size(300.dp)
            )
            Text(
                text = "Email Sent Successfully",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "We’ve just sent an email to your inbox with a link to reset your password",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Thin,
                color = Color.Gray
            )
            if (uiState.isloading) {
                CircularProgressIndicator()
            } else {
                Button(
                    modifier = Modifier.clip(RoundedCornerShape(29.dp)),
                    onClick = {
                        viewModel.sendVerificationEmail()
                    },
                    enabled = uiState.canresend
                ) {
                    Text(
                        text = if (uiState.canresend) "Resend Email" else "Resend in ${uiState.countdown}"
                    )
                }
            }
            Button(onClick = { openGmailApp(context) }) {
                Text(text = "Open Gmail")
            }
        }
    }
}

private fun openGmailApp(context: android.content.Context) {
    try {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_APP_EMAIL)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No Email app found on this device.", Toast.LENGTH_SHORT).show()
    }
}


