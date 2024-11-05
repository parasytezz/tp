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
      - [quit category](#5-quit-category)
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
Category options : 
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```
---
### 1. add category
### Example:
```
add friend
```
### Expected output:
```
_________________________________________________________
add friend
_________________________________________________________
_________________________________________________________
successfully added Category : 'friend'
_________________________________________________________
Category options : 
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```
---
### 2. remove category
### Example:
```
remove friend
```
### Expected output if friend is not a category:
```
_________________________________________________________
friend is not a category
_________________________________________________________
Category options : 
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```
### Expected output if friend is a category:
```
_________________________________________________________
successfully deleted Category : 'friend'
_________________________________________________________
Category options : 
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```
---
### 3. edit categories of contact
### Example:
```
edit 1 friend
```
### Expected output if contact id is invalid:
```
_________________________________________________________
Invalid contact id
_________________________________________________________
Category options : 
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```
### Expected output if friend is not a category:
```
_________________________________________________________
friend is not a valid category
_________________________________________________________
Category options : 
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```
### Expected output for successfully edit:
```
_________________________________________________________
successfully assigned categories to Contact id : 0
_________________________________________________________
Category options : ## currently do not support loading and saving ##
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```
---
### 4. view categories
### Example:
```
view
```
### Expected output:
```
_________________________________________________________
friend
_________________________________________________________
Category options : 
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```
---
### 5. quit category
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

---
## Adding an Expense
The `add` command allows users to add an `Expense` with various categories.

### Usage:
```
add /<category 1>: <value 1> /<category 2>: <value 2> /<category 3>: <value 3> ... 
```
### Example:
```
add /name: transport /cost: 10
```
### Expected Output:
```
_________________________________________________________
Added successfully!
_________________________________________________________
Currently in list:
1. [transport, cost = $10]
_________________________________________________________
```
---
## Viewing the Expense List
The `list` command allows users to view all their current `expenses`.

### Usage:
```
list
```

### Expected Output:
```
_________________________________________________________
Below is the list:
1. [transport, cost = $10]
_________________________________________________________
Total cost of all expenses is: $10.0
_________________________________________________________
```
---
## Deleting an Expense
The `delete` command allows users to delete an `expense` from the list.

### Usage:
```
delete <index>
```
### Example 1:
```
delete 1
```
### Expected Output:
```
_________________________________________________________
Deleted successfully!
_________________________________________________________
```
---
## Finding an Expense
The `find` command allows users to find `expenses` from the list.

### Usage:
```
find <value>
```
### Example 1:
```
find lunch
```
### Expected Output:
```
_________________________________________________________
Here is what I found:
1. [utown lunch, cost = $5]
2. [PGP lunch, cost = $4]
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
