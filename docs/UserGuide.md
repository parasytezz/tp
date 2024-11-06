# User Guide

---
## Introduction

---

PlanPal is a desktop application designed for international students studying in NUS. It functions as an all-in-one organizational tool, enabling users to manage contacts, track expenses, and schedule activities. The user interacts with the application using a CLI and is written in Java 17.

## Purpose of User Guide

---
The purpose of this guide is to show you how to get started on using this PlanPal and to introduce you to the basics of using it. this includes getting to know the feature and syntax of the commands.

## Target Audience

---
International students who are frugal and organized. It caters to the needs of these international students, allowing them to keep track of important contacts, activities, as well as manage their spending while in a foreign country.

### Table of Contents
- [Quick start](#quick-start)
- [Features](#Features)
  - [Modes](#modes)
  - [Exit](#exit)
  - [Contact Manager](#Contact-Manager)
    - [Add contact](#adding-a-contact)
    - [List contact](#viewing-the-contact-list)
    - [Edit contact](#Editing-a-contact)
    - [Delete contact](#deleting-a-contact)
    - [Setting category](#Setting-category)
      - [add category](#1-add-category)
      - [remove category](#2-remove-category)
      - [edit categories of contact](#3-edit-categories-of-contact)
      - [view categories](#4-view-categories)
      - [view the contact list](#5-view-the-contact-list)
      - [print category functions](#6-print-category-functions)
      - [quit category](#7-quit-category)
    - [Search contacts by category](#Search-contacts-by-category)
    - [Find contact](#finding-a-contact)
  - [Expense Manager](#expense-manager)
    - [Add expenses](#adding-an-expense)
    - [List expenses](#viewing-the-expense-list)
    - [Delete expenses](#deleting-an-expense)
    - [Find expenses](#finding-an-expense)
  - [Activity Manager](#activity-manager)
    - [Add activity](#adding-an-activity)
    - [List activity](#viewing-the-activities-list)
    - [Find activity](#finding-an-activity)
    - [Edit activity](#editing-an-activity)
    - [Delete activity](#deleting-an-activity)

---
## Quick Start
1. Ensure that you have Java 17 or above installed.
2. Down the latest version of `PlanPal` from [here](http://link.to/duke).
   Click on `PlanPal.jar` to automatically download the file. Place the file in a folder of your choice.
3. Open the command terminal and navigate to the folder where `PlanPal.jar` is downloaded.
4. Use the command `java -jar PlanPal.jar` to launch PlanPal
   On start up, you should see the following screen:  
   <img src="Images(UG)/MainScreen.png" alt="Main Screen" style="width:300px; margin-top: 10px; margin-bottom: 10px;">
5. When you are finished using PlanPal, use the `bye` command to terminate the application. This will ensure that your data is stored correctly and is available for future access.

---
## Features
This section will focus on some of the key features of PlanPal and explain their usage. We will go over several features, including the Contact, Expenses and Activity Manager functionalities.

---
## Modes
Within PlanPal, there exists 3 modes, namely Contact Manager, Expense Manager, and Activity Manager.

---
1. To select the `mode` you want to use, enter a number ranging from 1 to 3. In this example, to use `Contacts`, enter 1 into the CLI.  
   <img src="Images(UG)/ModeScreen.png" alt="Mode Screen" style="width:300px; margin-top: 10px; margin-bottom: 10px;">
2. Functionalities for each `mode` will be expanded on below.

---
## Exit
To go to another mode, type:`exit`. You should see the screen showing all available `modes`.

---
## Contact Manager
PlanPal will assist you in tracking the `Contacts` in your planner. The guide below will show you how to make use of the contact manager commands.

---
## Adding a Contact
The `add` command allows users to add a `Contact` with various categories.

### Usage:
```
add /<category 1>: <value 1> /<category 2>: <value 2> /<category 3>: <value 3> ... 
```
### Example:
```
add /name: johnny /phone:12345678 /email:johnny@gmail.com
```
### Expected Output:
```
_________________________________________________________
Added successfully!
_________________________________________________________
Currently in list:
1. [Name = johnny, Phone = 12345678, Email = johnny@gmail.com]
_________________________________________________________
```
---
## Viewing the Contact List
The `list` command allows users to view all their current `Contacts`.

### Usage:
```
list
```

### Expected Output:
```
_________________________________________________________
Below is the list:
1. [Name = johnny, Phone = 12345678, Email = johnny@gmail.com]
_________________________________________________________
```
---
## Deleting a Contact
The `delete` command allows users to delete an existing `Contact` in the contact list.

### Usage
```
delete <index> 
```

### Example
The user wants to delete an existing `Contact` that has an index of '2' in the contact list.
```
delete 2
```

### Expected Output:
```
_________________________________________________________
Deleted successfully!
_________________________________________________________
Currently in list:
1. [Name = Johnny, Phone = 12345678, Email = johnny@gmail.com]
_________________________________________________________
```
---
## Editing a Contact
The `edit` command allows users to edit a `Contact` from the list.

### Usage:
```
edit <index> /<category 1>: <value 1> /<category 2>: <value 2> /<category 3>: <value 3> ... 
```
### Example 1:
```
edit 1 /name: Cassie
```
### Expected Output:
```
_________________________________________________________
Edited successfully!
_________________________________________________________
Currently in list:
1. [Name = Cassie, Phone = 12345678, Email = johnny@gmail.com]
_________________________________________________________
```
---
## Finding a Contact
The `find` command allows users to find `Contacts` from the list.

### Usage:
```
find <value>
```
### Example 1:
```
find alice david
```
### Expected Output:
```
_________________________________________________________
Here is what I found:
1. [Name = David, Phone = null, Email = null]
2. [Name = Alice, Phone = null, Email = null]
_________________________________________________________
```
---
## Setting category
The `category` command allows users to customize `category` in contacts.
Currently, `category` does not support loading and saving, which means data will be lost once program is exit.

### Usage:
```
category
```
### Expected Output:
```
Category not found.
_________________________________________________________
```
### Expected Output if no contacts in category:
```
_________________________________________________________
Category options :
1. add Category type [ add {category} || e.g. add close friend ]
2. remove Category type [ remove {category} || e.g. remove emergency ]
3. edit Category of Contact [ edit {contact index} {category} || e.g. edit 1 friend ]
4. delete all Category of Contact ([ edit {contact index} /  || e.g. edit 1 / ]
5. view Category lists (e.g. view)
6. view Contact list (e.g. list)
7. print category functions (e.g. help)
8. quit
_________________________________________________________
```
---
### 1. add category
Example 1:
```
add friend
```
Expected output if successfully added:
```
_________________________________________________________
successfully added Category : 'friend'
_________________________________________________________
```
Example 2:
```
add /friend
```
Expected output if "/" is included:
```
_________________________________________________________
/ is not allowed to be used in category name
_________________________________________________________
```
Example 3:
```
add
```
Expected output if description is empty:
```
_________________________________________________________
Description cannot be empty!
_________________________________________________________
```
---
### 2. remove category
Example:
```
remove friend
```
Expected output if friend is not a category:
```
_________________________________________________________
friend is not a category
_________________________________________________________
```
Expected output if friend is a category:
```
_________________________________________________________
successfully deleted Category : 'friend'
_________________________________________________________
```
---
### 3. edit categories of contact
Format
```
edit {contact index} {category1/category2/...}
```
Example 1:
```
edit 1 friend
```
Expected output if friend is not a category:
```
_________________________________________________________
friend is not a valid category
_________________________________________________________
```
Expected output for successfully edit:
```
_________________________________________________________
successfully assigned categories to Contact id : 1
_________________________________________________________
```
Example 2:
```
edit 0 friend
```
Expected output if contact id is invalid:
```
_________________________________________________________
Invalid contact id
_________________________________________________________
```
Example 3:
```
edit 1
```
Expected output if contact id is invalid:
```
_________________________________________________________
successfully assigned categories to Contact id : 1
_________________________________________________________
```
Example 4:
```
edit
```
Expected output if contact id is invalid:
```
_________________________________________________________
Description cannot be empty!
_________________________________________________________
```
---
### 4. view categories
Example:
```
view
```
Expected output:
```
_________________________________________________________
Below is the list:
1. friend
_________________________________________________________
```
---
### 5. view the contact list
The `list` command allows users to view all their current `Contacts`.

Usage:
```
list
```

Expected Output:
```
_________________________________________________________
Below is the list:
1. [Name = johnny, Phone = 12345678, Email = johnny@gmail.com, Categories = []]
_________________________________________________________
```
---
### 6. print category functions
Usage:
```
help
```

Expected Output:
```
_________________________________________________________
Category options :
1. add Category type [ add {category} || e.g. add close friend ]
2. remove Category type [ remove {category} || e.g. remove emergency ]
3. edit Category of Contact [ edit {contact index} {category} || e.g. edit 1 friend ]
4. delete all Category of Contact ([ edit {contact index} /  || e.g. edit 1 / ]
5. view Category lists (e.g. view)
6. view Contact list (e.g. list)
7. print category functions (e.g. help)
8. quit
_________________________________________________________
```
---
### 7. quit category
### Example:
```
quit
```
---
## Search contacts by category
The `search` command allows users to search `Contacts` belonging to user-defined `category`.

### Usage:
```
search <query>
```
### Example 1:
```
search friend
```
### Expected Output if category not defined:
```
Category not found.
_________________________________________________________
```
### Expected Output if no contacts in category:
```
Contacts in category: friend
There is no contact in friend
_________________________________________________________
```
### Expected Output if there exists contacts in category:
```
Contacts in category: friend
[Name = andy Phone = null Email = null]
_________________________________________________________
```
---
## Expense Manager
PlanPal will assist you in tracking your `Expenses` in your planner. The guide below will show you how to make use of the expense manager commands.

### Quick Guide
This section provides a quick tutorial on how to use expense manager.  
As a start, ALWAYS follow this sequence to prevent errors:
1. Set the budget using the `budget` command. Without setting budget, you will not be able to add any expense to your list.
2. Add expenses using the `add` command.
3. View the list when needed using the `list` command.
4. Exit the mode using the `exit` command.

<ins>Note !!!</ins>  
There are 2 additional tags you need to take note of.  
By default, the program assumes that you are working the current month and also NOT on the recurring list
Adding these tags to any of your commands in this mode does the following:
- `/recurring`: Tells the program to work on the recurring list of expenses. Does not support for budget.
- `/month:`: Tells the program to work on that month's properties (budgets and expenses). The format should be `/month: <value>`

---
## Setting a Budget
The `budget` command allows users to add a budget.  
By default, without the month being specified, it will assume that the month is the current month.  
You CANNOT use `/recurring` tag for this function.

### Usage:
```
budget <value>
budget <value> <month>
budget <month> </> <value>
```
### Example:
```
budget 1000
budget 1000 /month: 2024-11
budget /month:2024-11 /1000
```
### Expected Output:
```
_________________________________________________________
For the month of 2024-11
Budget has been set to: $1000
_________________________________________________________
```
---
## Adding an expense
The `add` command allows users to add an expense to the expense list.  
By default, without any tags the following is assumed:
- Month is the current month
- It is NOT a recurring expense.

Currently, the fields that can be used are as follows:

| Field | Constraints                                                                                     |
|-------|-------------------------------------------------------------------------------------------------|
| name  | -                                                                                               |
| cost  | Non negative double value                                                                       |
| type  | Only these values are allowed: <br/>- FOOD<br/>- TRANSPORTATION<br/>- ENTERTAINMENT<br/>- OTHER |

**!!! If budget has not been set, you will not be able to add anything!**

### Usage 1 (default addition without tags):
```
add /<field 1>: <value 1> /<field 2>: <value 2> ... 
```
### Example:
```
add /name: Lunch /cost: 10
```
### Expected Output:
```
_________________________________________________________
For the month of 2024-11
Budget has been set to: $1000
_________________________________________________________
```
---

## Activity Manager
PlanPal will assist you in tracking your `activities` in your planner. The guide below will show you how to make use of the activity manager commands.

---
## Adding an activity

---
## Viewing the Activities list
The `list` command allows users to view all their current `activities`.

### Usage:
```
list
```
### Expected Output:
```
_________________________________________________________
Below is the list:
1. [activity: running, activityType: exercise]
2. [activity: swimming, activityType: exercise]
3. [activity: groceries, activityType: necessities]
_________________________________________________________
```
---
## Finding an activity
The `find` command allows users to find `activities` from the list.

### Usage:
```
find <value>
```
### Example 1:
```
find exercise
```
### Expected Output:
```
_________________________________________________________
Here is what I found:
1. [activity = running, activityType = exercise]
2. [activity = swimming, activityType = exercise]
_________________________________________________________
```
---
## Editing an activity
The `edit` command allows users to edit an `activity` from the list.

### Usage:
```
edit <index> /<category 1>: <value 1> /<category 2>: <value 2> ...
```
### Example 1
```
edit 1 /name: diving
```
### Expected Output:
```
_________________________________________________________
Currently in list:
1. [activity = diving, activityType = exercise]
2. [activity = swimming, activityType = exercise]
3. [activity = groceries, activityType = necessities]
_________________________________________________________
```
---
## Deleting an Activity

---

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
