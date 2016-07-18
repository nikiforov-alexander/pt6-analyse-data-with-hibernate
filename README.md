## Techdegree project 6
### Analyze Public Data With Hibernate
<hr>
### Table of Contents
### Installation instructions
* [Eclipse installation instructions.] (#eclipse)
<hr>

### Tasks
* [1.] (#task-1) 
    In the IDE of your choice, create a Gradle project and include the 
    standard directory structure for a Java application. In the IDE 
    of your choice, create a Gradle project and include the standard 
    directory structure for a Java application.
    <hr>
* [2.] (#task-2) 
    In the Gradle build file, add the following 
    dependencies:
    - Hibernate core
    - Java Transaction API (JTA)
    <hr>
* [3.] (#task-3)
    Create a data directory in your project. Save the downloaded 
    H2 database in this directory.
    <hr>
* [4.] (#task-4)
    Create a model class that can be associated with the table of 
    the given database. The data in the provided database includes 
    one table named `Country`, and each row in that table contains 
    the following columns:
    - `code`: *VARCHAR(3)* - this is the primary key, a String with a 
        maximum length of 3 characters
    - `name`: *VARCHAR(32)* - a String with a maximum length of 32 
        characters
    - `internetUsers`: *DECIMAL(11,8)* - A number with a maximum 
        length of 11 digits and 8 digits of decimal precision
    - `adultLiteracyRate`: *DECIMAL(11,8)* - A number with a 
        maximum length of 11 digits and 8 digits of decimal 
        precision

    <hr>
* [5.] (#task-5)
    The username for the supplied database is “sa” and there is no 
    password.
    <hr>
* [6.] (#task-6)
    Write the application code for a console application that 
    allows a user to view a list of well-formatted data for 
    all countries. This should be formatted in columns, contain 
    column headings, and numbers should be rounded to the nearest 
    hundredth. For any data that is unreported 
    (NULL in the database), this should be clear in the displayed 
    table. Please reference the provided example for data 
    formatting.
    <hr>
* [7.] (#task-7) 
    Add to your console application the code that allows a user to 
    view a list of statistics for each indicator, including (but 
    not limited to) a maximum and minimum for each indicator, and 
    a correlation coefficient for the two indicators together. 
    You may use a third-party library to calculate the correlation 
    coefficient. Keep in mind that all calculated statistics 
    should exclude any country that doesn’t have data reported for 
    the indicators under analysis (instead of using zero for 
    missing values).
    <hr>
* [8.] (#task-8) 
    Write the application code that allows a user to edit a 
    country’s data.
    <hr>
* [9.] (#task-9) 
    Write the application code that allows a user to add a 
    country with data for each indicator.
    <hr>
* [10.] (#task-10) 
    Write the application code that allows a user to delete a 
    country’s data.
    <hr>

### Extra Credit
* [11.] (#task-11) 
    Calculate a correlation coefficient between the two 
    indicators without using a third-party library.
    <hr>
* [12.] (#task-12) 
    Use the builder pattern for creating new country objects.
    <hr>
* [13.] (#task-13) 
    Use Java streams for finding maxima and minima.
    <hr>

<!--Links-->
<!--External URLs-->
[spark-blog-readme]: 
    https://github.com/nikiforov-alexander/pt4-spark-blog#eclipse "Spark Blog README"
<!--Dirs-->
[data]: data "data"
<!--Files-->
[worldbank.mv.db]: data/worldbank.mv.db "data/worldbank.mv.db"
<!--Classes-->
[Country]: 
    src/main/java/com/techdegree/hibernate/model/Country.java "src/main/java/com/techdegree/hibernate/model/Country.java"

### Eclipse Installation instructions
<hr> <a id="eclipse"></a>
    I generated necessary `.classpath`, `.project` and 
    `.userlibraries`. I tested it once again
    It worked. As always there is a problem with `BuildPath` in 
    `Eclipse`.
    So it is better to set `src/main/java` as a source in `BuildPath`
    options, if `Eclipse` does not understand it. Here is a link to old
    [Spark Blog README.md][spark-blog-readme] just in case. 
    Unfortunately colors that I used to color menu options are
    not seen in eclipse run console. So sorry for strange symbols
    just in case, they are meant to be colors of console. In
    `IntellijIdea` should be OK.
<hr>
### Tasks
1. <a id="task-1"></a>
    In the IDE of your choice, create a Gradle project and include the 
    standard directory structure for a Java application. In the IDE 
    of your choice, create a Gradle project and include the standard 
    directory structure for a Java application.
    <hr>
    Gradle project is created, standart directory structure is 
    used.
<hr>
2. <a id="task-2"></a>
    In the Gradle build file, add the following 
    dependencies:
    - Hibernate core
    - Java Transaction API (JTA)
    <hr>
    Both needed dependencies added, also following dependencies 
    added:
    - H2 database dependency, to be able to use database
    - Mockito library, to mock user input through `BufferedReader` 
        in menu prompter
    - Apache math library, to calculate Correlation coefficient,
        see [Task 7](#task-7).

<hr>
3. <a id="task-3"></a>
    Create a data directory in your project. Save the downloaded 
    H2 database in this directory.
    <hr>
    H2 database is named [worldbank.mv.db] and is situated in 
    [data] directory.
<hr>
4. <a id="task-4"></a>
    Create a model class that can be associated with the table of 
    the given database. The data in the provided database includes 
    one table named `Country`, and each row in that table contains 
    the following columns:
    - `code`: *VARCHAR(3)* - this is the primary key, a String with a 
        maximum length of 3 characters
    - `name`: *VARCHAR(32)* - a String with a maximum length of 32 
        characters
    - `internetUsers`: *DECIMAL(11,8)* - A number with a maximum 
        length of 11 digits and 8 digits of decimal precision
    - `adultLiteracyRate`: *DECIMAL(11,8)* - A number with a 
        maximum length of 11 digits and 8 digits of decimal 
        precision
    <hr>
    Model class [Country] is created. It is marked as `@Entity`,
    connected to `@Table` name `Country` in database. 
    - `mCode` member variable is marked as primary key with `@Id` 
        annotation, is set up with given above definition,
        is connected to `code` column in table.
    - `mName` member variable is marked as `@Column`, is set up
        with definition above and is
        connected to `name` column in table 
    - `mInternetUsers` member variable is marked as `@Column`, is
        set up with definition above and 
        is connected to `internetUsers` column in table 
    - `mAdultLiteracyRate` member variable is marked as `@Column`, is
        set up with definition above and 
        is connected to `adultLiteracyRate` column in table 
<hr>
