# Developer Guide

---
Welcome to the PlanPal Developer Guide! Thank you for taking an interest in the behind-the-scenes work of our product. We hope this document proves informative and useful for your work!

## Table of Contents
1. [Acknowledgements](#Acknowledgements)
2. [Setting up, getting started](#Setting-up-getting-started)
3. [Design](#Design)
   - [Architecture](#Architecture)
   - [UI component]()
   - [Logic component]()
   - [Model component]()
   - [Storage component]()
   - [Common classes]()
4. [Implementation]()
   - [Contact]()
     - [Add and delete Command]()
     - [Edit Command]()
     - [category Command]()
     - [search Command]()
   - [Activities]()
     - Add and delete activity
     - Edit an activity
     - List activities
     - Search for activities
   - [Budget]()
   - [Storage feature]()
5. [Documentation, logging, testing, configuration, dev-ops]()
6. [Appendix: Requirements]()
   - [Product scope]()
   - [User stories]()
   - [Use cases]()
   - [Non-Functional Requirements]()
   - [Glossary]()
7. [Appendix: Instructions for manual testing]()
   - [Launch and shutdown]()
   - [Create a contact]()
   - [Create an activity]()
   - [Create a budget]()
   
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
---

# Contact Manager Features

The sequence diagram below illustrates the process for resolving the "edit" command.
![EditContact.drawio.png](Images%2FEditContact.drawio.png)
## Adding Contact
The `add` command allows users to add a contact with various categories

### Usage:
```
add /<category 1>: <value 1> /<category 2>: <value 2> /<category 3>: <value 3> ... 
```
### Example:
```
add /name: johnny
```
### Expected Output:
```
_________________________________________________________
Added successfully!
_________________________________________________________
```
## Deleting Contact
The `delete` command allows users to delete an existing contact in the contact list.

### Usage
```
delete <index of the contact in the list> 
```

### Example
The user wants to delete a contact that has an index of '2' in the contact list.


```
delete 2
```

### Expected Output:
```
_________________________________________________________
Deleted successfully!
_________________________________________________________
```

---

## Finding a Contact
The `find` command allows users to find a contact from the list

### Usage:
```
find <query>
```
### Example 1:
```
find johnny
```
### Expected Output:
```
_________________________________________________________
Here is what I found:
1. [Name = johnny Phone = null Email = null]
_________________________________________________________
```

### Example 2:
```
find Alice David
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


## category Command
The 'category' command allows users to customize category in contacts. 
Currently, category does not support loading and saving, which means data will be lost once program is exit.

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
Category options : ## currently do not support loading and saving ##
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```
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
Category options : ## currently do not support loading and saving ##
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```

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
Category options : ## currently do not support loading and saving ##
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
Category options : ## currently do not support loading and saving ##
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```

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
Category options : ## currently do not support loading and saving ##
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
Category options : ## currently do not support loading and saving ##
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
Category options : ## currently do not support loading and saving ##
1. add Category type (e.g. add close friend)
2. remove Category type (e.g. remove emergency)
3. edit Category of Contact (e.g. edit 1 friend/family, to delete all category: edit 1 /)
4. view Category lists (e.g. view)
5. quit
```

### 5. quit category
### Example:
```
quit
```
---


## search contacts in a category
The 'search' command allows users to search contacts belonging to user-defined categories

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