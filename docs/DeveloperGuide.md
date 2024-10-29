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



## Design

---
### Architecture
![Architecture.drawio.png](Images%2FArchitecture.drawio.png)

The **Architecture Diagram** given above explains the high-level design of the program. Given below is a quick overview of the main components.

### Main Components
- `PlanPal`: Main entry of the program, initialises and connects the components
- `UI`: In charge of printing messages
- `Logic`: Determines the command to execute
- `Storage`: Read and write data from hard disk
- `Command`: Specific commands for execution

### Program Flow
- The `PlanPal` controls access to the `UI`, `Parser`, and `FileManager` (Storage) components of the app.
- The user will send input to `PlanPal`.
- `PlanPal` will parse the input to produce a `Command`, which will hook back into `PlanPal` to gain access to the other components when running

---
## Implementation

---
## Contact Manager Features

### Add Command
The sequence diagram below illustrates the process for resolving the "add" command.
![AddContact.drawio.png](Images%2FAddContact.drawio.png)

### Edit Command
The sequence diagram below illustrates the process for resolving the "edit" command.
![EditContact.drawio.png](Images%2FEditContact.drawio.png)

### Set Category Command
The sequence diagram below illustrates the process for resolving the "category" command.
![SetCategory.drawio.png](Images%2FSetCategory.drawio.png)
---
## Product scope
### Target user profile

{Describe the target user profile}
As an international student in NUS, I want to be able to...

### Value proposition

{Describe the value proposition: what problem does it solve?}

---

## User Stories

|Version| As a ... | I want to ...             | So that I can ...                                                              |
|--------|----------|---------------------------|--------------------------------------------------------------------------------|
|v1.0|NUS international student| see my list of contacts   | easily call home or look for emergency contacts when needed                    |
|v1.0|NUS international student| add a contact             | expand my list of useful contacts                                              |
|v1.0|NUS international student| delete a contact          | remove a contact if I no longer need it                                        |
|v1.0|NUS international student| edit a contact            | amend any mistakes when creating the contact or if the number has been changed |
|v1.0|NUS international student| save my contacts          | my contacts are still there when I exit and enter the app again                |                                                       |
|v2.0||  |                     |

---
## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}