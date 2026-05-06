//
//  HomeListingE2ETest.swift
//  iosApp
//
//  Created by Furqan on 08/03/2026.
//
import XCTest


final class HomeListingE2ETest : XCTestCase {
    
    
    private let userName = "customer1@trevnor.com"
    private let password = "password123"
    
    
    private var app: XCUIApplication!
    
    override func setUpWithError() throws {
        continueAfterFailure = false
        app  = XCUIApplication()
        app.launchArguments = ["CLEAR_DATA"]
        app.launch()
        
        try login()
    }
    
    override func tearDownWithError() throws {
    
        app.terminate()
        app = nil
    }
    
    func element(withTag tag: String) -> XCUIElement {
        app.descendants(matching: .any).matching(identifier: tag).firstMatch
    }
    
    func login() throws {
    
        let email = element(withTag: "login_email")
        XCTAssertTrue(email.waitForExistence(timeout: 5),"email field not found")
        email.tap()
        email.typeText(userName)
        
        
        let pass = element(withTag: "login_password")
        XCTAssertTrue(pass.waitForExistence(timeout: 5),"password field not found")
        pass.tap()
        pass.typeText(password)
        
        
        let loginButton = element(withTag: "login_button")
        XCTAssertTrue(loginButton.waitForExistence(timeout: 5),"Button field not found")
        loginButton.tap()

    }

    func testShowDetailsScreen_expectedContent() {
        let card = element(withTag: "listing_card_0")
        XCTAssertTrue(card.waitForExistence(timeout: 10),"No First item available")
        card.tap()
        
        let header = element(withTag: "details_header")
        XCTAssertTrue(header.waitForExistence(timeout: 10),"no Details available")
        
        app.swipeUp()
        app.swipeUp()
        
        let button = element(withTag: "book_now_button")
        XCTAssertTrue(button.waitForExistence(timeout: 10),"No Pay now button available")
    
        button.tap()
   
        let datesCard = element(withTag: "trip_dates_card")
        XCTAssertTrue(datesCard.waitForExistence(timeout: 10),"Dates card now button available")
        
    }
    
    
    
}
