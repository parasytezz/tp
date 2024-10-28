# User Guide

## Introduction

---
{Give a product intro}


## Purpose of User Guide

---
The purpose of this guide is to show you how to get started on using this PlanPal and to introduce you to the basics of using it. this includes getting to know the feature and syntax of the commands.

## Target Audience

---

## Quick Start

---
{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
2. Down the latest version of `PlanPal` from [here](http://link.to/duke).

## Table of Contents
- [Features](#Features)
  - [Contacts](#1-Contacts)
    - Add contact
    - List contact
    - Edit contact
    - Delete contact
    - Find contact
  - Budget
  - Activities
    - Add activity
    - List activity 
    - Edit activity
    - Delete activity
    - Find activity
---
## Features
This section will focus on some of the key features of PlanPal and explain their usage. We will go over several features, including the Contact functions, Budget functions, and Activity functions.

1. [Contacts](#1-contacts)
2. Budget
3. Activities
---
## 1. Contacts
PlanPal will assist you in tracking the Contacts in your planner, and is capable of adding new Contacts, removing Contacts, listing all Contacts, as well as editing Contacts. This guide will detail these features below.
- Add contact
- List contact
- [Edit contact](#Editing-a-contact) 
- Delete contact
- Find contact
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
### Adding a contact: `todo`
Adds a new item to the list of todo items.

Format: `todo n/TODO_NAME d/DEADLINE`

* The `DEADLINE` can be in a natural language format.
* The `TODO_NAME` cannot contain punctuation.  

Example of usage: 

`todo n/Write the rest of the User Guide d/next week`

`todo n/Refactor the User Guide to remove passive voice d/13/04/2020`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
