# GoldenCompass User Guide

## Table of Contents
- [Introduction](#introduction)
- [Quick Start](#quick-start)
- [Features](#features)
  - [Getting help](#getting-help)
  - [Exiting the program](#exiting-the-program-bye)
  - [Adding an internship application](#adding-an-internship-application-add)
  - [Adding an interview](#adding-an-interview-add-interview)
  - [Updating interview date](#updating-interview-date-update-date)
  - [Listing all internships](#listing-all-internships-list)
  - [Deleting an internship](#deleting-an-internship-delete)
  - [Deleting an interview](#deleting-an-interview-delete-interview)
  - [Searching internships](#searching-internships-search)
  - [Searching interviews](#searching-interviews-search-interview)
  - [Listing all interviews](#listing-all-interviews-list-interview)
  - [Listing upcoming interviews](#listing-upcoming-interviews-upcoming)
  - [Adding alias](#adding-alias-alias)
  - [Removing alias](#removing-alias-remove-alias)
  - [Undoing commands](#undoing-commands-undo)
  - [Redoing commands](#redoing-commands-redo)
  - [Marking an internship application as offer received](#marking-an-internship-application-as-offer-received-mark)
  - [Marking an internship application as rejected](#marking-an-internship-application-as-rejected-reject)
  - [Clearing rejected internships](#clearing-rejected-internships-clear-rejected)
- [Data Storage](#data-storage)
- [FAQ](#faq)
- [Known Issues](#known-issues)
- [Command Summary](#command-summary)

## Introduction

GoldenCompass is a **command-line application for managing internship applications**,
optimised for fast typists who prefer a keyboard-driven workflow.
It helps you track internships, schedule interviews, and stay on top of deadlines
all from your terminal.

## Quick Start

1. Ensure that you have **Java 17** or above installed.
2. Download the latest `goldencompass.jar` from the
   [releases page](https://github.com/AY2526S2-CS2113-W10-4/tp/releases).
3. Copy the file to the folder you want to use as the home folder.
4. Open a terminal, navigate to that folder, and run:
   ```
   java -jar goldencompass.jar
   ```
5. Type a command and press Enter to execute it. Refer to the
   [Features](#features) section below for details on each command.

## Features

> **Notes about the command format:**
> - The commands are all **case-sensitive**.
> - Words in `UPPER_CASE` are parameters to be supplied by the user.
>   e.g. in `add-interview INDEX /d DATE`, `INDEX` and `DATE` are parameters.
> - Items in square brackets are optional.
>   e.g. `search-interview [/c COMPANY] [/t ROLE] [/d DATE]`.
> - Flags start with `/` and are followed by their value.
>   e.g. `/c Google` supplies `Google` as the company parameter.
> - Parameters can be in any order.
> - Indexes are **1-based** and refer to the position shown in the most recent list.

### Getting help
Gets a help message on how to use a particular command with a special flag: `/help`.

When `/help` is present, all other parameters are ignored.

Format: `COMMAND /help`

Example:

To get help about the command `add`
```
add /help
```

### Exiting the program: `bye`

Exits the program regardless of any parameters supplied, including the help flag `/help`.


**Format:** `bye`

### Adding an internship application: `add`

Adds a new internship application to the tracker. By default, the status of a newly added `Internship` is set to `PENDING`.

**Format:** `add COMPANY_NAME /t TITLE`

* `COMPANY_NAME` is the name of the company you are applying to.
* The `/t` flag is strictly required and denotes the title of the role.

**Input Constraints:**
To maintain a clean and reliable tracker, the `add` command enforces the following rules:
* **Length Limit:** Both the `COMPANY_NAME` and `TITLE` must be between 2 and 40 characters long.
* **Valid Characters:** Only alphanumeric characters (letters and numbers), spaces, and commas `,` are permitted. Other special symbols (e.g., `|`, `@`, `!`) will be rejected.
* **No Duplicates (Case-Insensitive):** You cannot add an `Internship` if an identical entry already exists in your list. The system ignores capitalization, meaning `GRAB` and `grab` are treated as the same company.

* **Examples:**
* `add Grab /t Software Engineer`
  Adds a Software Engineer role at Grab to your list.
* `add Google /t Data Analyst`
  Adds a Data Analyst role at Google to your list.

**Expected Output (Success):**
```text
Got it! I've added this internship to your compass:
  Grab - Software Engineer
```

**Expected Output (Error - Duplicate Entry):**
```text
Warning: This internship already exists in your list!
```

**Expected Output (Error - Invalid Characters):**
```text
Only alphanumeric characters and commas ',' are permitted.
```
**Note on multi-role applications:**
* GoldenCompass supports applications for multiple roles within the same company.
* If you provide the `/t` flag multiple times, the system will combine them into a single entry.
* **Example:** `add Grab /t Backend /t DevOps` will be recorded as `Grab - Backend DevOps`.
* **Pro-tip:** This is great for tracking "Hybrid" roles where you'll be doing more than one thing!

### Adding an interview: `add-interview`

Adds an interview to an existing internship.

Format: `add-interview INDEX /d DATE`

- `INDEX` is the 1-based index of the internship from the list.
- `DATE` must follow the format `yyyy-MM-dd HH:mm`. The time is in **24-hour** format
  (e.g. `09:00` for 9 AM, `14:30` for 2:30 PM, `22:00` for 10 PM).
- The date must be a valid calendar date. Impossible dates such as `2026-02-30` or
  `2026-13-01` are rejected.
- The date must be in the future.
- Each internship can only have one interview. Use `update-date` to change the date, or
  `delete-interview` to remove the existing interview before adding a new one.
- Use `add-interview /help` to see usage information.

Examples:
- `add-interview 1 /d 2027-06-15 10:00`
- `add-interview 3 /d 2027-07-01 14:30`

### Updating interview date: `update-date`

Updates the date and time of an existing interview.

Format: `update-date INDEX /d DATE`

- `INDEX` is the 1-based index of the interview as shown in `list-interview` (sorted by date).
  After the update, the interview list is re-sorted, so running `list-interview` again
  will reflect the new ordering.
- `DATE` must follow the format `yyyy-MM-dd HH:mm`. The time is in **24-hour** format
  (e.g. `09:00` for 9 AM, `14:30` for 2:30 PM, `22:00` for 10 PM).
- The date must be a valid calendar date. Impossible dates such as `2026-02-30` or
  `2026-13-01` are rejected.
- The date must be in the future.
- Use `update-date /help` to see usage information.

Examples:
- `update-date 1 /d 2027-06-20 09:00`
- `update-date 2 /d 2027-08-01 15:00`

### Listing all internships: `list`

Displays all internships currently in your tracker with their index numbers and status.

Format: `list`

- Shows company name, job title, and offer/rejection status (if any).
- If no internships exist, a message is shown.

Example:
- `list`

### Deleting an internship: `delete`

Removes an internship application from the tracker.

Format: `delete INDEX`

- `INDEX` is the 1-based index of the internship from the list.
- **Note:** When an internship is deleted, its corresponding interview is also automatically deleted.

Examples:
- `delete 1` - Deletes the 1st internship in the list.
- `delete 3` - Deletes the 3rd internship in the list.

### Deleting an interview: `delete-interview`

Removes an interview from the interview list.

Format: `delete-interview INDEX`

- `INDEX` is the 1-based index of the interview shown in `list-interview` command.
- **Note:** Only the interview is deleted. The internship remains in your list.

Example:
- `delete-interview 1` - Deletes the first interview in the interview list.

### Searching internships: `search`

Searches for internships by company name and/or job title.
Text matching is case-insensitive and uses substring matching.

Format: `search [/c COMPANY] [/t TITLE]`

- At least one of `/c` or `/t` must be provided.
- `/c` - search by company name (partial match, case-insensitive)
- `/t` - search by job title (partial match, case-insensitive)

Examples:
- `search /c Google` - Finds all internships with "Google" in the company name.
- `search /t Engineer` - Finds all internships with "Engineer" in the title.
- `search /c Google /t Software` - Finds internships matching both criteria.

### Searching interviews: `search-interview`

Searches for interviews by company name, role, and/or date.
Text matching is case-insensitive substring matching.
Date matching is an exact match on the date portion.
Multiple flags narrow the results using AND logic.

Format: `search-interview [/c COMPANY] [/t ROLE] [/d DATE]`

- At least one of `/c`, `/t`, or `/d` must be provided.
- `/c` - search by company name (partial match, case-insensitive).
- `/t` - search by role/title (partial match, case-insensitive).
- `/d` - search by date, must follow the format `yyyy-MM-dd` (exact date match).
- Use `search-interview /help` to see usage information.

Examples:
- `search-interview /c Google`
- `search-interview /t Engineer /d 2025-06-15`
- `search-interview /d 2025-07-01`

### Listing all interviews: `list-interview`

Lists all interviews in ascending order of dates.

Format: `list-interview`

Example:
```
list-interview
Here are the interview invitations:
1. NUS - Bus Driver @ 2026-03-25 11:00
2. Meta - Frontend Developer @ 2026-03-25 14:00
3. Google - Software Engineer @ 2026-03-27 10:00
4. Amazon - Backend Developer @ 2026-04-01 09:00
```
If no interview has been added:
```
list-interview
You don't have any interviews!
```

### Listing upcoming interviews: `upcoming`

Lists upcoming interviews within a specific number of days.

Format: `upcoming [N]`, where `N` is an optional integer.

- If no integer is supplied after `upcoming`, a default of `N=5` days will be used.
- `N` can be negative, in which case, interviews of the past `|N|` days will be listed.
- The filtering is **inclusive of time boundaries** for all integers `N`.

Example:

If now is `2026-03-25 12:00`
```
upcoming
You have the following interviews in the upcoming 5 day(s):
Meta - Frontend Developer @ 2026-03-25 14:00
Google - Software Engineer @ 2026-03-27 10:00
upcoming 7
You have the following interviews in the upcoming 7 day(s):
Meta - Frontend Developer @ 2026-03-25 14:00
Google - Software Engineer @ 2026-03-27 10:00
Amazon - Backend Developer @ 2026-04-01 09:00
upcoming -1
You have the following interviews in the upcoming -1 day(s):
NUS - Bus Driver @ 2026-03-25 11:00
```
If now is `2026-03-19 11:00`:
```
upcoming
You don't have any interviews in the upcoming 5 day(s).
upcoming 6
You have the following interviews in the upcoming 6 day(s):
NUS - Bus Driver @ 2026-03-25 11:00
```

### Adding alias: `alias`

Adds an alias to the commands. Alias **cannot** have alias, while a command can have multiple aliases. 

A command is 
considered as an alias to itself, except that it can have alias.

**Note:** `bye` command cannot take an alias.

Format: `alias /c COMMAND /a ALIAS`, where `COMMAND` is the command and `ALIAS` is the alias to add.

Example:

To add an alias `ls` to `list`:
```
alias /c list /a ls
```

### Removing alias: `remove-alias`

Removes an existing alias. 

However, although a command is considered as special alias to itself, it cannot be removed.

Format: `remove-alias ALIAS`, where `ALIAS` is the alias to remove.

Example:

To remove the alias `ls`:
```
remove-alias ls
```

### Undoing commands: `undo`

Undoes up to `10` commands that are undoable, i.e, that modify the data, see [Command Summary](#command-summary).

Format: `undo`

Example:

Suppose you have added a wrong internship and want to undo that:
```
undo
```

### Redoing commands: `redo`

Redoes the commands that have been undone. However, if there are new undoable commands, i.e, new changes to the data,
redo history would be cleared, see [Command Summary](#command-summary).

Format: `redo`

Example:

Suppose you have added an internship and accidentally undone that, you want to redo it:
```
redo
```

### Marking an internship application as offer received: `mark`

Updates the status of an existing internship application in your tracker to indicate that you have successfully received an offer.

**Format:** `mark INDEX`

* `INDEX` refers to the index number shown in the displayed internship list.
* The index **must be a positive integer** (e.g., 1, 2, 3, ...).
* **Constraint:** You cannot mark an internship that is already tagged as `[OFFER RECEIVED]` or `[REJECTED]`.

**Examples:**
* `list` followed by `mark 4`
  Marks the 4th internship in the current list as having an offer.

**Expected Output (Success):**
```text
HUGE CONGRATS! Marked this internship as [OFFER RECEIVED]:
  Grab - Software Engineer [OFFER RECEIVED]
```

**Expected Output (Error - Already Offered):**
```text
Error: This internship is already marked as OFFER RECEIVED!
```

**Expected Output (Error - Already Rejected):**
```text
Error: You cannot mark an internship that has already been rejected!
```

### Marking an internship application as rejected: `reject`

Updates the status of an existing internship application to indicate that it is no longer an active prospect (e.g., the company rejected your application, or you declined their offer).

**Format:** `reject INDEX`

* `INDEX` refers to the index number shown in the displayed internship list.
* The index **must be a positive integer** (e.g., 1, 2, 3, ...).
* **Constraint:** You cannot reject an internship that is already tagged as `[REJECTED]`.
* **Note:** You *can* reject an internship that is currently tagged as `[OFFER RECEIVED]` if you decide to decline their offer!

**Examples:**
* `list` followed by `reject 4`
  Marks the 4th internship in the current list as rejected.

**Expected Output (Success):**
```text
Rejection builds character! Marked this internship as [REJECTED]:
  Grab - Software Engineer [REJECTED] 
```

**Expected Output (Error - Already Rejected):**
```text
Error: This internship has already been rejected!
```

### Clearing rejected internships: `clear-rejected`

Removes all internships marked as rejected from the tracker, along with their
associated interviews (if any).

Format: `clear-rejected`

- If there are no rejected internships, a message is shown indicating nothing to clear.
- A summary of the removed internships is printed after clearing.

Example:
- `clear-rejected`

## Data Storage

GoldenCompass automatically saves your data to your hard drive after any command that modifies your internships, interviews, or aliases. There is no need to manually save your data.

When you launch GoldenCompass, it will automatically load your saved data from previous sessions.

### File Locations
By default, GoldenCompass stores its data in a folder named `data` located in the same directory as the GoldenCompass application.
* **Internships:** `data/internships.txt`
* **Interviews:** `data/interviews.txt`
* **Aliases:** `data/aliases.txt`

### Manual File Editing (For Advanced Users)
You can choose to manually edit the data files using a standard text editor (like Notepad or TextEdit). However, you must adhere strictly to the following formats.

**Data Formats:**
* **`internships.txt`**: `TITLE | COMPANY | STATUS`
* **`interviews.txt`**: `COMPANY | TITLE | DATE(YYYY-MM-DDTHH:mm)`
* **`aliases.txt`**: `ALIAS | COMMAND`

**⚠️ Auto-Healing & Data Protection System**
GoldenCompass features a robust, self-healing storage system to protect your data from accidental corruption during manual editing:
1. **Format Enforcement:** If a line does not match the exact format (e.g., missing a pipe `|`, or text that is too short), GoldenCompass will skip the corrupted line safely rather than crashing.
2. **Duplicate Prevention:** If you accidentally paste the same data multiple times, the system will detect the duplicate, load only the first instance, and ignore the rest.
3. **Ghost Link Protection:** If an interview is listed in `interviews.txt`, but the corresponding internship no longer exists in `internships.txt`, the "ghost" interview will be safely skipped.
4. **Validation Check:** Any custom aliases manually entered that point to invalid commands will be blocked from loading.
5. **Auto-Cleaning:** Any corrupted lines, duplicates, or invalid entries detected during startup will trigger a warning message detailing the exact errors. The system will then **automatically clean and overwrite** the text file to restore it to a healthy state. However, the sanitized data is only saved back to the text file after you execute your first active command (e.g., `list`, `add`). If you launch the application and immediately exit using the `bye` command without doing anything else, the text file on your hard drive will remain uncleaned until your next active session.

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: Copy the `data/` folder from your GoldenCompass home directory to the
same location on the other computer.

**Q**: Can I use GoldenCompass without an internet connection?

**A**: Yes. GoldenCompass is a fully offline application.

## Known Issues

1. **Long company names or titles** may cause the display to wrap awkwardly
   in narrow terminal windows. Use a terminal width of at least 80 characters.

## Command Summary

| Action                                        | Undoable?      | Format                                              | Example                               |
|-----------------------------------------------|----------------|-----------------------------------------------------|---------------------------------------|
| Add internship                                | Yes            | `add COMPANY_NAME /t TITLE`                         | `add Grab /t Software Engineer`       |
| Add interview                                 | Yes            | `add-interview INDEX /d DATE`                       | `add-interview 1 /d 2025-06-15 10:00` |
| Update interview date                         | Yes            | `update-date INDEX /d DATE`                         | `update-date 1 /d 2025-06-20 09:00`   |
| List internships                              | No             | `list`                                              | `list`                                |
| Delete internship                             | Yes            | `delete INDEX`                                      | `delete 1`                            |
| Delete interview                              | Yes            | `delete-interview INDEX`                            | `delete-interview 1`                  |
| Search internships                            | No             | `search [/c COMPANY] [/t TITLE]`                    | `search /c Google`                    |
| Search interviews                             | No             | `search-interview [/c COMPANY] [/t ROLE] [/d DATE]` | `search-interview /d 2025-06-15`      |
| Clear rejected                                | Yes            | `clear-rejected`                                    | `clear-rejected`                      |
| List interviews                               | No             | `list-interview`                                    | `list-interview`                      |
| List upcoming interviews                      | No             | `upcoming [N]`                                      | `upcoming` / `upcoming 3`             |
| Add alias                                     | Yes            | `alias /c COMMAND /a ALIAS`                         | `alias /c list /a ls`                 |
| Remove alias                                  | Yes            | `remove-alias ALIAS`                                | `remove-alias ls`                     |
| Undo a command                                | Yes (by`redo`) | `undo`                                              | `undo`                                |
| Redo a command                                | Yes (by`undo`) | `redo`                                              | `redo`                                |
| Mark internship application as offer received | Yes            | `mark INDEX`                                        | `mark 4`                              |
| Mark internship application as rejected       | Yes            | `reject INDEX`                                      | `reject 4`                            |
| Exit the program                              | No             | `bye`                                               | `bye`                                 |
