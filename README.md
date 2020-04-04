# App Manual 

***

### Login activity (Main activity)
-	Enter correct username or password of existing account and click login button to launch Shopping activity

-	If you want to create new account, click on create button to launch Create account activity

<img src="https://github.com/jykelly2/OnlineShoppingCart/blob/master/Picture1.png" height="550" width="350">


***

### Create account activity
-	Enter username, password (at least 5 characters) and retype password and click on create to make a new account. If account already exists, it will display a toast message to prevent duplication of account.

-	Click on the delete button to clear all or specific username accounts on the SQLite database. You can choose to delete by name, where you would be asked to input a username and it will delete all accounts with that username, or you can choose to delete everything by choosing all. 

-	Back button would launch back to Login activity


[[https://github.com/jykelly2/OnlineShoppingCart/blob/master/Picture2.png]]


***


### Shopping activity
-	Scroll down recycler view and click on check buttons to add items you want to the list. For each item, you can choose size and quantity from the spinner. 

-	Specify category and price by choosing price range and category from the spinner and click on set price range and category button, which would display only items within the specified range and category on a recycler view. 

-	Click on show selected items button to display items you picked so far. When launching back from the Summary page to make changes, you can click on this button to show all the items you already picked to make changes to purchase easier. 

-	Click on go to summary button to launch Summary activity.


[[https://github.com/jykelly2/OnlineShoppingCart/blob/master/Picture3.png]]


***


### Summary activity
-	Choose shipping option (standard $10 or express $20)

-	Click on calculate total to review total prices on items you picked. (Displays date, subtotal, shipping fee, tax, total)

-	Click on back button to go back to Shopping activity to make changes. 

-	Click on confirm button to launch Payment activity.



[[https://github.com/jykelly2/OnlineShoppingCart/blob/master/Picture4.png]]


***


### Payment activity
-	Click on radio button to specify card type.

-	Enter 16 digits card number, select expiry date (click on the Text View), enter 3 digit CVC code.

-	Click on remember information which will fill in the information automatically when user logs in. Unchecking this check box will update SQL with null values, and will leave these information blank for user to type in information again.

-	Click on complete payment button to launch Confirmation Activity and click on go back button to go back to Summary Activity 


[[https://github.com/jykelly2/OnlineShoppingCart/blob/master/Picture5.png]]


***


### Confirmation activity
-	Click on logout button to logout and launch Login activity

-	Click on back button to go back to Summary activity

Shopping activity, Summary activity, Payment Activity and Confirmation activity would all have a menu, which includes logout, where clicking on logout would launch Login activity. 


[[https://github.com/jykelly2/OnlineShoppingCart/blob/master/Picture6.png]]




