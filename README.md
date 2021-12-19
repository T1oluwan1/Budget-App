# <ins>My Personal Project</ins>

## Budget App(Expense Tracker)

**Proposal:** This application is designed to help give an overview of an individuals flow of money and to help them 
manage it. The application should be free for anyone to use, however its intended audience is mostly young adults, who 
often, are just getting a stable source of income for the first time. The application is designed to be simple and easy 
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
- Provide a graph of monthly/weekly flow of monthly and stats
- *Calculate Linear Regression and make predictions for future weeks and months*
- Create and manage personalized budgets
- *Make encouraging comments that differ based on account balance range*
- Give warnings when close or going over budget
- Give warnings when spending more than earning
- *Make snarky comments based on spending*
- *Change overview currency to at least 5 different main currencies*
- .....more?

List as of now is uncertain and many changes will be added later, both implementing new things and removing old things

## User Stories 

P1
- As a user, I want to be able to add expenses(outflows) to my balance
- As a user, I want to be able to add incomes(inflows) to my balance
- As a user, I want to be able to see an overview of my expenses and incomes in my balance (transactions)
- As a user, I want to be able to label my expenses into different groups (groceries, education, self-care...)
- As a user, I want to be able to label my incomes into different groups (salary, cheque, loan...)
- As a user, I want to be able to personalize the limitations on my budget
- 
P2
- As a user, I want to be able to save my Budget App/Expense Tracker to file
- As a user, I want to be able to load my Budget App/Expense Tracker from file

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

## Phase 4: Task 2

Wed Nov 24 23:16:44 MST 2021

Set Budget for shopping to $100.0

Wed Nov 24 23:16:59 MST 2021

Added salary Income of $1000.0 to Account

Wed Nov 24 23:17:09 MST 2021

Added shopping Expense of $101.0 to Account


Process finished with exit code 0

## Phase 4: Task 3
If I had more time to work on the project I would have:

- Increase overall robustness by throwing several exceptions
- Implemented Category Info (started but couldn't finish in time)
- Increased cohesion and coupling by making categories an abstract class and making expenses and income it's subtype or
- Increased cohesion and coupling by making every category its own class
- Added more features, such as a field set to 1 that amount & balance are multiplied by, allowing me to change currency