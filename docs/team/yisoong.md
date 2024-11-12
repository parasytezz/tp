# Nathan Tan - Project Portfolio Page

## Overview
PlanPal is a desktop application designed for international students studying in NUS. It functions as an all-in-one organizational tool, enabling users to manage contacts, track expenses, and schedule activities. The user interacts with the application using a CLI and is written in Java 17.

Code Contributed: [link to code](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=yisoong&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-09-20&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

### Summary of Contributions
* Feature: Find Contacts
  * What it does: This feature allows users to filter contacts based on specific keyword or phrase queries.
  * Justification: It enhances the usability by enabling efficient contact retrieval, allowing the user to quickly access information from lengthy lists

* Feature: Find Expenses
  * What it does: This feature allows users to filter expenses based on specific keyword or phrase queries. 
  * Justification: It enhances usability by enabling efficient expense retrieval, allowing users to quickly access information from lengthy lists

* Feature: Delete Expense
  * What it does: This feature allows users to delete a specific expense from their expense list based on the index selected
  * Justification: It enhances usability by enabling the user to remove expenses that have been tracked incorrectly

* Feature: Expense Types
  * What it does: This feature allows users to classify expenses according to 4 expense types - FOOD, TRANSPORTATION, ENTERTAINMENT and OTHER
  * Justification: It enhances usability by enabling the user to track their spending based on expense type

* Feature: Expense Type Breakdown
  * What it does: This feature allows users to see the proportion of their spending on different expense types when viewing the expense list
  * Justification: It enhances usability by enabling users to have more information about their expenses, such as:
    * Their total spending on each expense type
    * Their proportion of spending on each expense type


* Enhancements Implemented
  * Improved `findInList`:
    * Works with all lists and not just contact
    * Queries are case insensitive
    * Fixed strings are ignored
  * Added the `/name:` field for expenses for easier tracking
  * Added formatting in the `list` function output to show spending breakdown

  
* User Guide
  * Project overview and description
  * Documentation for the `find` feature for both Contacts and Expenses 
  * Documentation for the `delete` feature for Expenses
  * Documentation for Expense Types
  * Command summary table for Expense Manager


* Developer Guide:
  * Overall class diagram for Contact Manager mode
  * Breakdown and descriptions of classes involved in Contact Manager Mode
  * Overall class diagram for Expense Manager mode 
  * Breakdown and descriptions of classes involved in Expense Manager Mode
  * Contact Manager: `find` command
  * Expense Manager: `find` command, `delete` command
  * User stories table


* Contributions to team-based tasks
  * Setting up and maintaining the issue tracker
  * Error finding and bug fixes
  * Editing code to make it reusable for other components (e.g.`findInList`)


