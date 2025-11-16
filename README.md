## Task Description

This project automates the following test cases for the **SauceDemo** login form:

---
### Launch URL: https://www.saucedemo.com/

### ✅ UC-1: Successful purchase flow

**Preconditions:**

* User has valid credentials.
* Browser is open on the login page.

**Steps:**

1. Login on the `LoginPage` with valid credentials.
2. Assert that login is successful (user is on `InventoryPage`, `HeaderComponent` is visible).
3. Add one product to the cart on the `InventoryPage`.
4. Verify that the cart badge in `HeaderComponent` shows “1”.
5. Open the cart (`CartPage`) from the `HeaderComponent`.
6. Verify that the correct product is listed in the cart.
7. Click “Checkout” to open `CheckoutStepOnePage`.
8. Fill in checkout information (First Name, Last Name, Postal Code).
9. Continue to `CheckoutStepTwoPage`.
10. Verify that order summary and prices are displayed correctly.
11. Click “Finish” to complete the purchase (open `CheckoutCompletePage`).
12. Verify that confirmation message is displayed.
13. Click “Back Home” and confirm that cart is empty on `InventoryPage`.
14. Log out from `HeaderComponent`.

**Expected Result:**

* Order is successfully completed, confirmation message is displayed, and cart resets to empty.

---

### ✅ UC-2: Remove product from cart
**Preconditions:**

* User has valid credentials.
* Browser is open on the login page.

**Steps:**

1. Login on the `LoginPage` with valid credentials.
2. Add two different products to the cart on the `InventoryPage`.
3. Verify that the cart badge in `HeaderComponent` shows “2”.
4. Open the cart (`CartPage`) from the `HeaderComponent`.
5. Verify that both products are displayed in the cart.
6. Remove one product using the “Remove” button.
7. Verify that the removed product disappears from the cart.
8. Verify that the cart badge in `HeaderComponent` shows “1”.
9. Click “Checkout” and proceed to `CheckoutStepTwoPage`.
10. Verify that only one product is listed in the order summary.
11. Cancel checkout and return to the `InventoryPage`.
12. Log out.

**Expected Result:**

* The removed item disappears from the cart and checkout summary; the remaining item stays in the cart.

---

### ✅ UC-3: Checkout validation errors

**Preconditions:**

* User has valid credentials.
* Browser is open on the login page.

**Steps:**

1. Login on the `LoginPage` with valid credentials.
2. Add one product to the cart on the `InventoryPage`.
3. Open the cart (`CartPage`) and click “Checkout”.
4. On `CheckoutStepOnePage`, leave all fields empty and click “Continue”.
5. Verify that an error message is displayed (“Error: First Name is required”).
6. Fill only the First Name field and click “Continue”.
7. Verify that another error message appears (“Error: Last Name is required”).
8. Fill Last Name field and click “Continue”.
9. Verify that another error message appears (“Error: Postal Code is required”).
10. Fill in all required fields correctly and click “Continue” to go to `CheckoutStepTwoPage`.
11. Verify that order summary loads correctly.
12. Click “Cancel” to return to `InventoryPage`.
13. Verify that the product is still in the cart.
14. Log out.

**Expected Result:**

* Proper validation messages are displayed for missing fields, and checkout proceeds only when all inputs are valid.
