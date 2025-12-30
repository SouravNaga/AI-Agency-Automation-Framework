# Automation Test Instruction Guide

This README explains how to write and structure automation test instructions for the project.
Each action in the test flow has a specific format, making it easier to generate scripts or execute tests manually.

---

## Instruction Types

### 1. Navigate
- **Format:**  `Navigate <URL>`
- **Description:** Navigates the browser to the specified URL.
- **Example:**  `Navigate https://www.saucedemo.com/`

### 2. Enter / Input
- **Format:**  `Enter <data> in <page_name>`
- **Description:** Enters the provided data into a field on the specified page.
- **Example:**
  ```
  Enter standard_user in login page
  Enter secret_sauce in login page
  Enter Sourav in login page
  Enter Naga in login page
  Enter 712413 in login page
  ```

### 3. Click
- **Format:**  `Click <element_key> in <page_name>`
- **Description:** Clicks the specified button, link, or interactive element on the page.
- **Example:**
  ```
  Click login_btn in login page
  Click bike_light in login page
  Click add_to_cart in login page
  Click cart in login page
  Click checkout in login page
  Click continue in login page
  Click finish in login page
  Click back_home in login page
  Click menu in login page
  ```

### 4. Wait
- **Format:**  `Wait <element_key> in <page_name>`
- **Description:** Waits for the specified element to be present, visible, or interactable before continuing.
- **Example:**  `Wait cart in login page`

### 5. Verify / Assertion

#### a. Verify element text
- **Format:**  `verify within <page_name> the <element_key> is <expected_text>`
- **Example:**  `verify within login page the heading_text swag_labs_heading is Swag Labs`

#### b. Verify current URL
- **Format:**  `verify within <page_name> the current_url is <expected_url>`
- **Example:**  `verify within login page the current_url is https://www.saucedemo.com/inventory.html`

#### c. Verify element visibility
- **Format:**  `verify within <page_name> the element <element_key> is visible`
- **Example:**  `verify within login page the element swag_labs_heading is visible`

---

## Example Full Test Flow
```
Navigate https://www.saucedemo.com/

Enter standard_user in login page
Enter secret_sauce in login page
Click login_btn in login page

Click bike_light in login page
Click add_to_cart in login page
Wait cart in login page
Click cart in login page

Click checkout in login page
Enter Sourav in login page
Enter Naga in login page
Enter 712413 in login page

Click continue in login page
Click finish in login page
Click back_home in login page
Click menu in login page

verify within login page the heading_text swag_labs_heading is Swag Labs
verify within login page the current_url is https://www.saucedemo.com/inventory.html
verify within login page the element swag_labs_heading is visible
```

---

### Notes
- `<page_name>` should be descriptive, e.g., `login page`, `cart page`.
- `<element_key>` refers to the identifier for elements, e.g., `login_btn`, `add_to_cart`.
- Keys and data should be consistent with your JSON element locators for automation.

