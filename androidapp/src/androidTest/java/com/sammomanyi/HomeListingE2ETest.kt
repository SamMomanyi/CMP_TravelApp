package com.sammomanyi

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class HomeListingE2ETest {



    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>(
    )

    private val emailText = "customer1@trevnor.com"
    private val passwordText = "password123"

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testHomeListingScreen_toDetails() {
        composeTestRule.activity.resetData()
        composeTestRule.waitForIdle()
        val email = composeTestRule.onNodeWithTag("login_email")
        email.assertIsDisplayed()
        email.performClick()
        email.performTextInput(emailText)

        val password = composeTestRule.onNodeWithTag("login_password")
        password.assertIsDisplayed()
        password.performClick()
        password.performTextInput(passwordText)


        val loginButton = composeTestRule.onNodeWithTag("login_button")
        loginButton.assertIsDisplayed()
        loginButton.performClick()

        composeTestRule.waitForIdle()
        composeTestRule.waitUntilAtLeastOneExists(hasTestTag("listing_card_0"),10000L)
        val listingItem = composeTestRule.onNodeWithTag("listing_card_0")
        listingItem.assertIsDisplayed()
        listingItem.performClick()

        composeTestRule.waitForIdle()
        composeTestRule.waitUntilAtLeastOneExists(hasTestTag("book_now_button"),10000L)

        val detailsScreen = composeTestRule.onNodeWithTag("details_header")
        detailsScreen.assertIsDisplayed()
        val button = composeTestRule.onNodeWithTag("book_now_button").assertIsDisplayed()
        button.assertIsDisplayed()

        button.performClick()
        composeTestRule.waitForIdle()
        val bookingScreen = composeTestRule.onNodeWithTag("trip_dates_card")
        bookingScreen.assertIsDisplayed()
        composeTestRule.activity.resetData()
    }
}