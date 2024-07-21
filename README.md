*(Imported for local residue of deleted Github account.)
## Budget App (Expense Tracker)

**Proposal:** This application is designed to help give an overview of an individuals money flow and to assist them in 
managing it. This application should be free for anyone to use, however its intended audience is primarily young adults, 
who are often just getting a stable source of income for the first time. The application is designed to be simple and easy 
to use, while at the same time having multiple uses and being very effective at what it does. This project is of 
interest to me because I was looking for something that would challenge my current abilities, while being realistic and 
practical. I went into this project with the mindset that it should be something I myself would want to use, something 
that I would be able to show to an employer that boast a strong work ethic and an innovative mentality.

There are already several budget apps out there, and it's not my intention to add mine to the collection of mundane,
basic budget apps that nobody gives any special attention to. There are a few thing I plan to add to set mine apart. 
The functions of my app are listed below, both regular and unique (which will be italicized).
- Record Income and add to balance
- Record Expenses and subtract from balance
- Record Internal and External transfers and adjust from balance
- Categorize Income and Expenses
- Provide a graph of monthly/weekly flow of monthly and stats [TODO]
- *Calculate Linear Regression and make predictions for future weeks and months* [TODO]
- Create and manage personalized budgets
- *Make encouraging comments that differ based on account balance range*
- Give warnings when close or going over budget
- Give warnings when spending more than earning
- *Change overview currency to at least 5 different main currencies* [TODO]
- Data will be stored in account that can be logged in to [TODO]
- .....more?

List as of now is uncertain and many changes will be added later, both implementing new things and removing old things

## User Stories 
- As a user, I want to be able to add expenses(outflows) to my balance
- As a user, I want to be able to add incomes(inflows) to my balance
- As a user, I want to be able to see an overview of my expenses and incomes in my balance (transactions)
- As a user, I want to be able to label my expenses into different groups (groceries, education, self-care...)
- As a user, I want to be able to label my incomes into different groups (salary, cheque, loan...)
- As a user, I want to be able to personalize the limitations on my budget
- As a user, I want to be able to save my Budget App/Expense Tracker to file
- As a user, I want to be able to load my Budget App/Expense Tracker from file

## Current Tasks
- Increase overall robustness by throwing several exceptions
- Implemented Category Info
- Increased cohesion and coupling by making categories an abstract class and making expenses and income it's subtype or
- Increased cohesion and coupling by making every category its own class
- Added more features, such as a field set to 1 that amount & balance are multiplied by, allowing me to change currency
- Implement Account class and Login

## How to Install
...

## How to Use
...

## Citations
- Json Serialization Demo (Reference for Save and Load to file):
- https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
- Teller App (Reference for Require, Modifies, Effect): 
- https://github.students.cs.ubc.ca/CPSC210/TellerApp
- total() method: 
- https://stackoverflow.com/questions/16242733/sum-all-the-elements-java-arraylist
- GUI Demo:
- https://www.youtube.com/watch?v=Kmgo00avvEw&t=127s
- Pop up Window:
- https://www.youtube.com/watch?v=4edL_cwmiZ4
- Alarm System (Reference for Event and EventLog):
- https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
