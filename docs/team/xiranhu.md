# Hu Xiran's Project Portfolio Page

## Project: GoldenCompass

GoldenCompass is a CLI-based internship application tracker specifically designed for NUS Computer Engineering students. It helps busy students manage their internship applications, track interview schedules, and monitor offer statuses seamlessly via a fast and efficient command-line interface. It is written in Java and utilizes a local, text-based storage system for data persistence.

Given below are my contributions to the project.

### Code Contributed: [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=xiranhu&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

### New Features:
- **Feature:** `add` command for adding an internship application [PR#34](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/34)
    - **What it does:** Adds a new internship application (company and role) to the tracker.
    - **Justification:** Forms the core functionality for users to begin populating their tracker.
    - **Highlights:** Engineered an "Accumulated Validation" parser that aggregates all syntax errors into a single feedback message. This significantly improves CLI UX by preventing a frustrating "whack-a-mole" error loop.

- **Feature:** `mark` command for updating an internship status [PR#64](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/64)
    - **What it does:** Updates an existing internship's status to `OFFER RECEIVED`.
    - **Justification:** Fulfills a primary use case of visually separating secured offers from pending applications.
    - **Highlights:** Implemented strict bounds-checking to prevent out-of-bounds crashes. The state mutation also seamlessly triggers the backend "Eager Saving" architecture to safely update the local `.txt` file without duplicating data.

- **Feature:** `reject` command for updating an internship status [PR#80](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/80)
    - **What it does:** Updates an existing internship's status to `REJECTED`.
    - **Justification:** Allows users to close the loop on unsuccessful applications or declined offers, keeping their active tracker uncluttered.
    - **Highlights:** Built upon the same robust bounds-checking and state mutation logic as the `mark` command (adhering to DRY principles), ensuring safe data modification and immediate, crash-free file persistence.

- **Feature:** Local Storage Architecture (`InternshipStorage`, `InterviewStorage`, `AliasStorage`) [PR#61](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/61)
    - **What it does:** Automatically saves and loads all internship data, interview schedules, and command aliases to the local hard drive.
    - **Justification:** Guarantees data persistence across sessions, which is a mandatory requirement for a reliable, real-world tracking tool.
    - **Highlights:** Engineered an "Eager Saving" mechanism that triggers after every command to ensure zero data loss. Implemented complex relational object reconstruction to dynamically link loaded interviews back to their parent internships in memory. Built in high fault-tolerance to safely skip corrupted file lines and auto-create missing directories without crashing the app.

### Project Management:
* Managed project releases and tracked feature deliverables for the `v1.0` and `v2.0` milestones on GitHub.
* Managed issue tracking and maintained project traceability by systematically linking Pull Requests (PRs) to their corresponding issues.

### Enhancements to Existing Features:
* **OOP Refactoring [PR#81](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/81):** Refactored the `add` and `mark` commands to inherit from a centralized `CommandClass.java`, enforcing polymorphism and reducing code duplication. 
* **Defensive Programming [PR#58](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/58):** Integrated Java `assert` statements and system logging (`java.util.logging`) across core operations to enforce runtime invariants and enhance the application's fault tolerance. 
* **State Logic Optimization [PR#87](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/87):** Restructured the internship status handling by introducing a distinct `PENDING` state and replacing nested conditionals with a `switch` statement, streamlining state transitions and improving parser readability. 

### Documentation:

* **User Guide [PR#128](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/128):**
    * Added documentation for the core features `add`, `mark`, and `reject`.
    * Wrote the comprehensive `Saving the Data` section, detailing the backend file architecture, update behaviors, and ISO-8601 formatting to ensure peer testers understand the application's persistent state.
* **Developer Guide [PR#60](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/60) [PR#62](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/62) [PR#121](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/121):**
    * Added implementation details, design considerations, and test coverage for the `add` `mark` `reject` command, including all the corresponding `ClassDiagram` and `SequenceDiagram`.
    * Documented the complete architecture of the `Storage` component, explaining the eager saving execution flow and relational mapping strategies. Designed and added the `StorageClassDiagram` and `StorageSequenceDiagram`.

### Community:
* Team PRs reviewed (with non-trivial review comments): [PR#124](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/124#pullrequestreview-4056818424), [PR#127](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/127#pullrequestreview-4056854521)
* Reviewed other team's Developer Guide [(link)](https://github.com/NUS-CS2113-AY2526-S2/tp/pull/11#pullrequestreview-4045030275). 
