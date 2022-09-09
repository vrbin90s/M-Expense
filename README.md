# M-Expense - Expense Management App
---

This Mobile App Prototype has been developed for the Mobile Application coursework at the University of Greenwich as part of the fulfillment of the Digital Media Design and Development Degree using Android Studio Software and Java Programming language.

# Scenario
---

Often employees are able to claim expenses for travel that they have to undertake in order to do their jobs. For instance, an employee who usually works in London may have to travel to Plymouth for a meeting. They could
claim for the transport costs (e.g., underground, train and bus) and also for lunch and refreshments or accommodation.

Therfore the goal of this project is to create a mobile app for use by employees that they can use to record details of their expenses and then upload them to their employer’s server.

# System functionalities
---

:heavy_check_mark: A - Users should be able to enter the details of the trip including:

- [x] Name of the trip
- [x] Destination
- [x] Date of the trip
- [x] Return date
- [x] Requires risk assessment
- [x] Description
- [x] Transportation Type

Additionaly app should: 

- [x] Check the input and if the user doesn’t enter anything in one of the required fields, the appshould display an error message.
- [x] Once the details have been accepted by the app (e.g. no required fields were missing), it should display the details back to the user for confirmation and allow them to go back and change any details that they wish.

---

:heavy_check_mark: B - Store, view and delete trip details or reset the database.

- [x] All the details entered by the user should initially be stored on the device in an SQLite database.
- [x] The user should be able to list all the details of all trips that have been entered into the app, edit or delete individual trips, and delete all the details from the database.

---

:heavy_check_mark: C - Add expenses to a trip that allows employees to enter details about expenses by selecting a trip and then entering the following details:

- [x] Type of expense (e.g. “travel”, “food”, “other” )
- [x] Amount of the expense
- [x] Time of the expense
- [x] Additional comments -

Additionaly app shoul:

- [x] Allow users to enter multiple expenses for a single trip.
- [x] Store all details entered on the device in an SQLLite database.
- [x] Users should be able to select a trip and display all expenses.

---

:heavy_check_mark: D - Search

- [x] The user should be able to search for a trip in the database by name.
- [x] The user should be able to use advanced search options that will allow to filter all trips by the following criteria: name, destination or date.

---

:heavy_check_mark: E - Upload details to a cloud-based web service

- [x] The user should be able to upload all the details of expenses stored on the mobile device to a cloudbased web service. It should be possible for all expenses to be uploaded at once.

---

:heavy_check_mark: F - Additional Features

- [x] Pick up the location automatically from the user's location

---

# Instruction / Install

1. Clone the repository
2. Import project foler in your Android Studio Development Environment
3. Use API 30 or above to run the emulator.

---

# Screenshots

![image](https://user-images.githubusercontent.com/72602872/189371564-dd7b4f02-a684-4a91-a813-f8a3c0f51ec9.png)
![image](https://user-images.githubusercontent.com/72602872/189371698-7ed63a2a-a072-40c9-b6cd-70d88fb50071.png)
![image](https://user-images.githubusercontent.com/72602872/189371806-35d9d4c9-efe2-449a-b003-2e37e3616654.png)
![image](https://user-images.githubusercontent.com/72602872/189371906-77805254-bcb5-464e-99d3-b012bcb0b8ad.png)
![image](https://user-images.githubusercontent.com/72602872/189371973-6b480049-4da4-44b4-aa81-a5157e98ef1b.png)
![image](https://user-images.githubusercontent.com/72602872/189372183-a1b9737c-71b7-49ca-9276-4a307c6f1c7d.png)
![image](https://user-images.githubusercontent.com/72602872/189372256-2045871f-0519-4be0-a18d-07f40d8658bc.png)
![image](https://user-images.githubusercontent.com/72602872/189372356-4c88a174-c89a-4d77-883d-8a69eccb831d.png)









