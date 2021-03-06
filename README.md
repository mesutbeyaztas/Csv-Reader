
# CSV Reader

A sample Csv Reader android app that shows how to use ViewModels and Repository pattern together with Coroutines & Hilt, in Kotlin by MVVM Architecture.


### Sample Csv Files
Used https://sample-videos.com/download-sample-csv.php files for testing

### What's Csv?
A Comma Separated Values (CSV) file is a plain text file that contains a list of data. 
These files are often used for exchanging data between different applications. 
For example, databases and contact managers often support CSV files.

Sample Csv File:

```
Name,Email,Phone Number,Address

Bob Smith,bob@example.com,123-456-7890,123 Fake Street

Mike Jones,mike@example.com,098-765-4321,321 Fake Avenue
```

### Communication between layers

1. UI calls method from ViewModel.
2. ViewModel executes Repository methods.
3. Each Repository returns data from a Data Source (CsvCore).
4. Information flows back to the UI where we display the list of posts.


### Showing data in table view

Used https://github.com/ISchwarz23/SortableTableView library to show details of csv file

