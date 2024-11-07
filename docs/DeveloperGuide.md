# Developer Guide

---
Welcome to the PlanPal Developer Guide! Thank you for taking an interest in the behind-the-scenes work of our product. We hope this document proves informative and useful for your work!

## Table of Contents
{to be inserted at a later date}

---

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Setting up, getting started
1. Java 17 is required for PlanPal to function properly, please make sure your device has Java 17 installed. If not, you may download it [here](). If you need further assistance installing Java 17, you may refer to the [Installation Guide.]()
2. Once you have ascertained that your device has Java 17 installed, you may download the `.jar file` [here]().
insert SS of release page

Click on `PlanPal.jar` to automatically download the file. Place the file in a folder your choice.

---

## Design & Implementation

### Architecture
A high-level overview of the system is shown in the **Architecture Diagram** below.  
![Architecture.drawio.png](Images%28DG%29%2FArchitecture.drawio.png)

### Main Components
- `PlanPal`: Main entry of the program, initialises and connects to the Ui
- `Ui`: In charge of printing messages
- `Parser`: Determines the command to execute
- `Modes`: Determines the group of functionalities that the user wants to use.
- `FileManager`: Read and write data from hard disk

### Program Flow
The sequence diagram below describes how the components interacts with one another when the user issues a command.

For simplicity, the 3 different modes will be classified as `:Mode`.

<u>Sequence Diagram for PlanPal</u>  
![MainProgramFlowDiagram.drawio.png](Images%28DG%29%2FMainProgramFlowDiagram.drawio.png)


## Ui

<u>Overview</u>  
The `Ui` component handles operations that displays messages for the user to read as an instruction.  
Additionally, it is also used to capture user inputs in certain functions.

<u>Methods</u>  
The `Ui` component has the following key methods:

| Method                | Description                                                          |
|-----------------------|----------------------------------------------------------------------|
| printWelcomeMessage   | Prints the welcome message                                           |
| printAvailableModes   | Print all modes that are currently available in PlanPal              |
| printByeMessage       | Prints the good bye message                                          |
| printCreateStorage    | Prints the path to the storage that is created                       |
| getSetCategory        | Capture user input when using the category function in certain modes |
| printCat              | Prints the categories available in certain modes                     |
| printCategoryNotFound | Prints when category is not found                                    |
| print                 | Custom print function that accepts multiple strings                  |

<u>Design Considerations</u>  
This component was created as functionalities such as printing will be used in most of the classes in the PlanPal Application.  
Consolidating all the print methods that are repeated reduces repetition in code and makes it easier to update when necessary.

## Parser
<u>Overview</u>
This component handles the logic behind the application. The parser component consists of the parent `Parser` Class and 3 children that inherits the `Parser` Class. These 3 children are used when the respective modes are in play.  

<u>Class Diagram for Parser Component</u>  
![ParserClassDiagram.drawio.png](Images%28DG%29%2FParserClassDiagram.drawio.png)

<u>Design Considerations</u>  
The Parser class follows this structure as there are common commands between the different modes. Using inheritance prevents the repetition of code. Additionally, different parsers were created since different modes require different functionalities.

## Mode: Contact Manager
The class diagram below represents the contact book system

<u>Class Diagram for Contact Manager</u>  
![ContactClassDiagram.drawio.png](Images%28DG%29/ContactClassDiagram.drawio.png)


The `Contact` class in PlanPal represents a user's contact, encapsulating details like name, phone, and email. It implements the `Editable` and `Storeable` interfaces, enabling contacts to be modified and stored.

The `ContactManager` class in PlanPal manages a list of `Contact` objects. This class provides CRUD (Create, Read, Update, Delete) operations for handling contacts, along with advanced features for categorizing contacts. `ContactManager` implements the ListFunctions interface, allowing it to handle list-based operations in a structured way.

The `ListFunctions` interface in the PlanPal application provides generic CRUD (Create, Read, Update, Delete) operations for list management. It standardizes common list manipulations, enabling reusable, modular operations across various object types and components.

The `Editable`interface provides a standardized way to modify objects within PlanPal by defining an editing method, processEditFunction, which takes new values and updates object attributes accordingly.

The `Storeable` interface standardizes essential storage-related functionalities within PlanPal, ensuring that objects implementing it can be managed and stored in a consistent manner.

<u>Methods</u>

| Methods         | 
|-----------------|
| addContact      |
| deleteContact   |
| viewContactList |
| editContact     |
| findContact     |

## Commands

### Add Command
The sequence diagram below illustrates the process for resolving the "add" command.  
![AddContact.drawio.png](Images%28DG%29%2FAddContact.drawio.png)

<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ContactParser` class is the `Parser` component
- `ContactManager` class is the `Mode` component

<u>Explanation:</u> 
- Before this command is executed, the user will have to choose their mode.
- When modeInput is 1, representing contact manager, the user is then asked for a command in this mode.
- When command "add /name: john" is sent, the processCommand function is executed.
- Since the command is "add", addContact function is executed using the description "/name: john". 
- This creates a new contact and is then added to the list of contacts
- Finally, contact file is saved in the savedContacts FileManager.

### Delete Command
The sequence diagram below illustrates the process for resolving the "delete" command.
![DeleteContact.drawio.png](Images%28DG%29%2FDeleteContact.drawio.png)

<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ContactParser` class is the `Parser` component
- `ContactManager` class is the `Mode` component

<u>Explanation:</u>
- Before this command is executed, the user will have to choose their mode.
- When modeInput is 1, representing contact manager, the user is then asked for a command in this mode.
- When the command "delete 1" is sent, the processCommand function is executed.
- Since the command is "delete", deleteContact function is executed using the index "1".
- This will delete the contact with that is assigned with the index "1" in the list of contacts.
- Finally, the updated contact file is saved in the savedContacts FileManager.

### List Command
The sequence diagram below illustrates the process for resolving the "list" command.  
![ViewContact.drawio.png](Images%28DG%29%2FViewContact.drawio.png)

<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ContactParser` class is the `Parser` component
- `ContactManager` class is the `Mode` component
- There is no need for saving for list here.

<u>Explanation:</u>
- Before this command is executed, the user will have to choose their mode.
- When modeInput is 1, representing contact manager, the user is then asked for a command in this mode.
- When command "list" is sent, the processCommand function is executed.
- Since the command is "list", viewContactList command is executed, printing all the contacts in the list.

### Edit Command
The sequence diagram below illustrates the process for resolving the "edit" command.  
![EditContact.drawio.png](Images%28DG%29%2FEditContact.drawio.png)


<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ContactParser` class is the `Parser` component
- `ContactManager` class is the `Mode` component

<u>Explanation:</u>
- Before this command is executed, the user will have to choose their mode.
- When modeInput is 1, representing contact manager, the user is then asked for a command in this mode.
- When command "edit" is sent, the processCommand function is executed.
- Since the command is "edit", editList command is executed using the description 1 /name: Bob.
- The editList function edits the field at the stated index
- Finally, contact file is saved in the savedContacts FileManager.

### Set Category Command
The sequence diagram below illustrates the process for resolving the "category" command. 
ContactManager is omitted from the diagram for simplicity.


![SetContactCategory.drawio.png](Images%28DG%29%2FSetContactCategory.drawio.png)
<u>Components Breakdown:</u>
- `ContactParser` class is the `Parser` component
- `SetContactCategory` class is the `Mode` component

<u>Explanation:</u>
- Before this command is executed, the user will have to choose their mode.
- When modeInput is 1, representing contact manager, the user is then asked for a command in this mode.
- When command "category" is sent, the processCommand function is executed in ContactManager.
- Then handle function is called in SetContactCategory.
- "add {category}" can be used to add new category
- "remove {category}" can be used to remove existing category
- "edit {contact id} {category1/category2/..}" can be used to assign category to contact with specified id
- "view" is executed to show all existing categories
- "list" is executed to show all existing contacts
- "quit" is executed to quit setting category mode
- Finally, contact file is saved in the savedContacts FileManager.

### Search Command
The 'search' command allows users to search contacts belonging to user-defined categories

The sequence diagram below illustrates the process for resolving the "search" command.
![SearchByCategory.drawio.png](Images%28DG%29%2FSearchByCategory.drawio.png)
<u>Explanation:</u>
- Before this command is executed, the user will have to choose their mode.
- When modeInput is 1, representing contact manager, the user is then asked for a command in this mode.
- When command "search {category}" is sent, the processCommand function is executed.
- Since the command is "search {category}", a list of contacts that is assigned with the category is shown


### Find Command
The sequence diagram below illustrates the process for resolving the "find" command.
![FindContactDiagram.drawio.png](Images%28DG%29/FindContactDiagram.drawio.png)

<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ContactParser` class is the `Parser` component
- `ContactManager` class is the `Mode` component
- There is no need for saving for list here.

<u>Explanation:</u>
- Before this command is executed, the user will have to choose their mode.
- When modeInput is 1, representing contact manager, the user is then asked for a command in this mode.
- When command "find john" is sent, the processCommand function is executed.
- Since the command is "find", findContact command is executed using the description "john".
- This finds contacts containing the description "john" and adds them to an ArrayList of matching contacts.
- Finally, the matching contacts are printed out for the user to see.

---

## Mode: Expense Manager
The class diagram below represents the Expense Manager system.

{insert class diagram} 

**Work in progress for v2.0**

### Set Budget Command
The sequence diagram below illustrates the process from adding a budget (to a month).  

![SetBudgetDiagram.drawio.png](Images%28DG%29%2FSetBudgetDiagram.drawio.png)  

<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ExpenseParser` class is the `Parser` component
- `ExpenseManager` class is the `Mode` component
- `BudgetManager` class is created when ExpenseManager was constructed

<u>Explanation:</u>
- Before this command is executed, the user will have to choose their mode.
- When modeInput is 2, representing expense manager, the user is then asked for a command in this mode.
- When command to set budget is sent, the `ExpenseParser` processes this command.
- BudgetManager is then used to set the budget.
- During this process, it tries to find the month value and then using this value, the budget is set to that month.
- Budget is then saved to a budget txt file.

### Add Command
The sequence diagrams below illustrate the process for resolving the "add" command.  
![AddExpense.drawio.png](Images%28DG%29%2FAddExpense.drawio.png)  
<div style="display: flex; justify-content: space-around;">
    <img src="Images%28DG%29%2FAddExpenseRef1.drawio.png" width="50%">
    <img src="Images%28DG%29%2FAddExpenseRef2.drawio.png" width="50%">
</div>  

<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ExpenseParser` class is the `Parser` component
- `ExpenseManager` class is the `Mode` component
- `BudgetManager` class is created when ExpenseManager was constructed

<u>Explanation:</u>  
The way the add command works for expense manager is similar to how it works for contact manager. The key differences are listed below:
- There is a check for `/recurring` and `/month:` tag.
- Addition of the recurring expense list when new expense list is created.

### List Command
The sequence diagram below illustrates the process for resolving the "list" command.

![ViewExpensesDiagram.drawio.png](Images%28DG%29%2FViewExpensesDiagram.drawio.png)  

<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ExpenseParser` class is the `Parser` component
- `ExpenseManager` class is the `Mode` component
- `RecurringManager` class is created when ExpenseManager was constructed

<u>Explanation:</u>  
The way the list command works for expense manager is similar to how it works for contact manager. The key difference are listed below:
- There is a check for `/recurring` and `/month:` tag.
- It will add the recurring expense list if the user is viewing a new expense list without anything in it. (Refer to the ref frame in [add command](#add-command-1))

### Edit Command
The sequence diagram below illustrates the process for resolving the "edit" command.  
![EditExpense.drawio.png](Images%28DG%29%2FEditExpense.drawio.png)  

<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ExpenseParser` class is the `Parser` component
- `ExpenseManager` class is the `Mode` component
- `RecurringManager` class is created when ExpenseManager was constructed

<u>Explanation:</u>  
The way the edit command works for expense manager is similar to how it works for contact manager. The key differences are listed below:
- There is a check for `/recurring` and `/month:` tag.
- You are able to edit both the recurring expense list or the monthly list, depending on whichever is chosen.

### Delete Command

### Find Command

---
## Mode: Activity Manager
The class diagram below represents the Activity Manager system.
![ActivityClassDiagram.drawio.png](Images%28DG%29%2FActivityClassDiagram.drawio.png)

### Add Command
The sequence diagram below illustrates the process for resolving the "add" command.
![AddActivities.drawio.png](Images%28DG%29%2FAddActivities.drawio.png)

<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ActivityParser` class is the `Parser` component
- `ActivityManager` class is the `Mode` component

<u>Explanation:</u>  
The way the add command works for activity manager is similar to how it works for contact manager, with the only difference 
being the names used, as well as an additional type to dictate activity type.

### Delete Command
The sequence diagram below illustrates the process for resolving the "add" command.
![DeleteActivties.drawio.png](Images%28DG%29%2FDeleteActivties.drawio.png)

<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ActivityParser` class is the `Parser` component
- `ActivityManager` class is the `Mode` component

<u>Explanation:</u>  
The way the delete command works for activity manager is similar to how it works for contact manager, with the only difference
being the names used.

### Edit Command
The sequence diagram below illustrates the process for resolving the "edit" command.
![EditActivity.drawio.png](Images%28DG%29%2FEditActivity.drawio.png)
<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ActivityParser` class is the `Parser` component
- `ActivityManager` class is the `Mode` component

<u>Explanation:</u>  
The way the edit command works for activity manager is similar to how it works for contact manager.

### List Command
The sequence diagram below illustrates the process for resolving the "list" command.
![ViewActivity.drawio.png](Images%28DG%29%2FViewActivity.drawio.png)
<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ActivityParser` class is the `Parser` component
- `ActivityManager` class is the `Mode` component

<u>Explanation:</u>  
The way the list command works for activity manager is similar to how it works for contact manager.

### Find Command
The sequence diagram below illustrates the process for resolving the "find" command.
![FindActivity.drawio.png](Images%28DG%29%2FFindActivity.drawio.png)
<u>Components Breakdown:</u>
- For simplicity, the `Ui` component has been taken out
- `ActivityParser` class is the `Parser` component
- `ActivityManager` class is the `Mode` component

<u>Explanation:</u>  
The way the find command works for activity manager is similar to how it works for contact manager.

---
## Product scope
### Target user profile

Our target user profile is international students studying in NUS.

### Value proposition

Studying in a foreign country can be disorienting and isolating, this application will help users keep track of the contacts and important details of the people they meet in NUS so that they can connect with others conveniently. This application will also help them manage their spending while in a foreign country.

---

## User Stories

|Version| As a ... | I want to ...             | So that I can ...                                                                 |
|--------|----------|---------------------------|-----------------------------------------------------------------------------------|
|v1.0|NUS international student| see my list of contacts   | easily call home or look for emergency contacts when needed                       |
|v1.0|NUS international student| add a contact             | expand my list of useful contacts                                                 |
|v1.0|NUS international student| delete a contact          | remove a contact if I no longer need it                                           |
|v1.0|NUS international student| edit a contact            | amend any mistakes when creating the contact or if the number has been changed    |
|v1.0|NUS international student| save my contacts          | my contacts are still there when I exit and enter the application again           |                                                       |
|v2.0|NUS international student|    |                                                                                   |
|v2.0|NUS international student|    |                                                                                   |
|v2.0|NUS international student|    |                                                                                   |
|v2.0|NUS international student|    |                                                                                   |
|v2.0|NUS international student|    |                                                                                   |
|v2.0|NUS international student|    |                                                                                   |
|v2.0|NUS international student| see my list of activities | keep track of my schedule                                                         |
|v2.0|NUS international student| add an activity           | expand my list of activities                                                      |
|v2.0|NUS international student| delete an activity        | remove an activity when I have done it                                            |
|v2.0|NUS international student| edit an activity          | amend any mistakes when creating the activity or if the activity has been changed |
|v2.0|NUS international student| save my activities        | my activities are still there when I exit and enter the application again         |

---
## Non-Functional Requirements

- Technical Requirements: Any mainstream OS, i.e. Windows, macOS or Linux, with Java 17 installed.
- Project Scope Constraints: Data storage is only to be performed locally.
- Quality Requirements: The application should be intuitive for a user with little experience with CLIs.

## Glossary

* *Contact* - Personal details and contact information of an individual

---

## Instructions for manual testing

View the User Guide for the full list of UI commands and their related use case and expected outcomes.