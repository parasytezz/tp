# User Guide

## Introduction

---


PlanPal is a desktop application designed for international students studying in NUS. It functions as an all-in-one organizational tool, enabling users to manage contacts, track expenses, and schedule activities. The user interacts with the application using a CLI and is written in Java 17.



## Purpose of User Guide

---
The purpose of this guide is to show you how to get started on using this PlanPal and to introduce you to the basics of using it. this includes getting to know the feature and syntax of the commands.

## Target Audience

---
International students who are frugal and organized.

## Quick Start

---
{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
2. Down the latest version of `PlanPal` from [here](http://link.to/duke).

## Quick Tutorial

---
1. On start up, you should see the following screen:  
   <img src="Images(UG)/MainScreen.png" alt="Main Screen" style="width:300px; margin-top: 10px; margin-bottom: 10px;">
2. Next, select the mode you want to use. In this example, to use contacts, select 1.  
   <img src="Images(UG)/ModeScreen.png" alt="Mode Screen" style="width:300px; margin-top: 10px; margin-bottom: 10px;">
3. Enter the command as per the formats shown below.
4. To go to another mode, type:`exit`. You should see the screen showing all available modes.
5. To exit the program, type: `bye`.

---
## Features
This section will focus on some of the key features of PlanPal and explain their usage. We will go over several features, including the Contact, Expenses and Activity Manager functionalities.

### Table of Contents
- [Features](#Features)
  - [Contact Manager](#Contact-Manager)
    - [Add contact](#adding-a-contact)
    - [List contact](#viewing-the-contact-list)
    - [Edit contact](#Editing-a-contact)
    - Delete contact
    - Find contact
  - [Expense Manager](#expense-manager)
    - [Add expenses](#adding-an-expense)
    - [List expenses](#viewing-the-expense-list)
    - Delete expenses
    - Find expenses
  - Activity Manager
    - Add activity
    - List activity
    - Edit activity
    - Delete activity
    - Find activity

---
## Contact Manager
PlanPal will assist you in tracking the Contacts in your planner. The guide below will show you how to make use of the contact manager commands.

---
## Adding a Contact
The `add` command allows users to add a contact with various categories

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
[Name = johnny Phone = 12345678 Email = johnny@gmail.com]
```
---
## Viewing the Contact List
The `list` command allows users to view all their current contacts.

### Usage:
```
list
```

### Expected Output:
```
_________________________________________________________
Below is the list:
1. [Name = johnny Phone = 12345678 Email = johnny@gmail.com]
_________________________________________________________
```
---
## Editing a Contact
The `edit` command allows users to find a contact from the list

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
```
---
## Finding a Contact
The `find` command allows users to find a contact from the list

### Usage:
```
find john
```
### Example 1:
```
find alice david
```
### Expected Output:
```
_________________________________________________________
Here is what I found:
1. [Name = David Phone = null Email = null]
2. [Name = Alice Phone = null Email = null]
_________________________________________________________
```
---
## Expense Manager
PlanPal will assist you in tracking your expenses in your planner. The guide below will show you how to make use of the expense manager commands.

---
## Adding an Expense
The `add` command allows users to add an expense with various categories

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
[transport, cost = $10]
```
---
## Viewing the Expense List
The `list` command allows users to view all their current expenses.

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

---

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
