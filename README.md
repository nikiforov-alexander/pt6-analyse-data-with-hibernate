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
    https://github.com/nikiforov-alexander/pt4-spark-blog#eclipse "https://github.com/nikiforov-alexander/pt4-spark-blog#eclipse"
[soccer_league_organizer]:
    https://github.com/nikiforov-alexander/pt2-soccer-league-organizer "https://github.com/nikiforov-alexander/pt2-soccer-league-organizer"
[PearsonsCorrelation]:
    http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/stat/correlation/PearsonsCorrelation.html "http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/stat/correlation/PearsonsCorrelation.html"
[apache_math3_library]:
    https://mvnrepository.com/artifact/org.apache.commons/commons-math3 "https://mvnrepository.com/artifact/org.apache.commons/commons-math3"
[stack_overflow_correlation_question]:
   http://stackoverflow.com/questions/28428365/how-to-find-correlation-between-two-integer-arrays-in-java "http://stackoverflow.com/questions/28428365/how-to-find-correlation-between-two-integer-arrays-in-java"
<!--Dirs-->
[data]: data "data"
[resources]: 
    src/main/resources "src/main/resources" 
<!--Files-->
[worldbank.mv.db]: data/worldbank.mv.db "data/worldbank.mv.db"
[hibernate.cfg.xml]: 
    src/main/resources/hibernate.cfg.xml "src/main/resources/hibernate.cfg.xml"
[hibernate-test.cfg.xml]: 
    src/main/resources/hibernate-test.cfg.xml "src/main/resources/hibernate-test.cfg.xml"
[data-table-sample.txt]:
   public-data/data-table-sample.txt "public-data/data-table-sample.txt"
<!--Classes-->
[Country]: 
    src/main/java/com/techdegree/hibernate/model/Country.java "src/main/java/com/techdegree/hibernate/model/Country.java"
[DatabaseMenuPrompter]: 
    src/main/java/com/techdegree/hibernate/controller/DatabaseMenuPrompter.java "src/main/java/com/techdegree/hibernate/controller/DatabaseMenuPrompter.java"
[Prompter]: 
    src/main/java/com/techdegree/hibernate/controller/Prompter.java "src/main/java/com/techdegree/hibernate/controller/Prompter.java"
[CountriesDaoImplementation]: 
    src/main/java/com/techdegree/hibernate/dao/CountriesDaoImplementation.java "src/main/java/com/techdegree/hibernate/dao/CountriesDaoImplementation.java"
[Main]: 
    src/main/java/com/techdegree/hibernate/Main.java "src/main/java/com/techdegree/hibernate/Main.java"


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
    connected to `@Table` name `Country` in database. It is 
    added in mapping tag in [hibernate.cfg.xml] 
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
5. <a id="task-5"></a>
    The username for the supplied database is “sa” and there is no 
    password.
    <hr>
    Settings for database are in [hibernate.cfg.xml] file in
    [resources] directory.
    [hibernate-test.cfg.xml] file in same directory is used in 
    testing database.
<hr>
6. <a id="task-6"></a>
    Write the application code for a console application that 
    allows a user to view a list of well-formatted data for 
    all countries. This should be formatted in columns, contain 
    column headings, and numbers should be rounded to the nearest 
    hundredth. For any data that is unreported 
    (NULL in the database), this should be clear in the displayed 
    table. Please reference the provided example for data 
    formatting.
    <hr>
    In order to assist user in console the [DatabaseMenuPrompter] 
    class is created, it inherits [Prompter] class with basic
    functionality, like, simple prompting and printing. It is 
    a copy of class created in 
    [Soccer League Organizer][soccer_league_organizer]
    project with small modification. [DatabaseMenuPrompter] first is
    created with a reference to [CountriesDaoImplementation], that
    is reference to our database (For the structure of the project
    see [Structure]). [DatabaseMenuPrompter] runs in [Main] class
    using `presentMenuWithPossibleOptions` method. It presents 
    user with 6 options:
    - 0: `Exit`
    - 1: `Add`
    - 2: `Delete`
    - 3: `Edit`
    - 4: `Show all`
    - 5: `Show statistics`
    <hr>
    - `Exit` option is self-explanatory: quits database menu, closes
        hibernate's session factory.
    - `Show all` option shows `code`, `name` and two decimals in 
        format: `%7s %40s %40s %15.2f %15.2f`. For more on that 
        see `showAll()` method in [DatabaseMenuPrompter]. `null`
        values are replaced by "--" Strings. It is achieved in
        `toString()` method in [Country] class, which is used later
        in printing each Country in `showAll` method.
        <br>
        NOTE: its not how it is presented in [data-table-sample.txt]
        file, simply because I wanted for country codes to be 
        shown as well, so that user can pick which country to
        edit, delete or remove by its code.
<hr>
7. <a id="task-7"></a>
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
    The methods returning maxima and minima for both decimals are
    situated in [CountriesDaoImplementation] method:
    - `getMinimumAdultLiteracy()`
    - `getMaximumAdultLiteracy()`
    - `getMinimumInternetUsers()`
    - `getMaximumInternetUsers()`
    They filter countries by non-null values, map to Double
    stream decimal parameter and then if value is found, return
    `Double` or `null`
    <hr>
    Correlation coefficient is calculated using 
    [PearsonsCorrelation] class in 
    [Apache commons math3 library][apache_math3_library].
    That was the first link that I've found upon googling,
    in this 
    [Stack overflow discussion][stack_overflow_correlation_question].
    <br>
    Just like with methods finding minima and maxima, I put this
    method to `CountriesDaoImplementation` with name:
    - `getCorrelationCoefficient()`
    In this method at first all countries are filtered by non-null
    values for both decimals, then mapped to array of doubles 
    `double []`, that are inputs to `correlation` method of 
    `PearsonsCorrelation()` class. The method throws
    `DimensionMismatchException` and `MathIllegalArgumentException` 
    because of possible arrays length mismatch, or length of arrays
    less than 2. I ensure that first one is not the case by
    filtering of both non-null decimals. The second one is handled
    by printing stack trace and returning `null` and later simple
    "--" printing.
    <br>
    Both minima maxima and correlation coefficient are shown
    by choosing "5":`Show statistics` in main menu. 
    In [DatabaseMenuPrompter] they are converted
    to `String` replacing possible `null` values. See
    `showStatistics` method implementation for more on that.
<hr>
8. <a id="task-8"></a>
    Write the application code that allows a user to edit a 
    country’s data.
    <hr>
    User can type 3: `Edit` in main menu, to enter edit menu. 
    he will be asked for country Code. Country code has to be 
    three letters, can be lower cased, after the input letters
    will be uppercased. If found contry will
    be found, user will be asked for the rest of parameters.
    This is primitive edit, where user can change only all the
    fields, there is no "default" or "back" options. Upon 
    successful edit, success message is printed. If country is
    not found, error message appears, redirecting user back
    to main menu. The possibility of `null` value is realized
    by typing "null" for decimals only. For more see, 
    `editCountry()`
    in [DatabaseMenuPrompter]. Actual edit in database will be
    done in [CountriesDaoImplementation] method `update`, like 
    all other database interactions. Problem of wrong user inputs
    is solved at this level, when user is promted, his data is
    processed, so that it can later succesfully go to database.
<hr>
9. <a id="task-9"></a>
    Write the application code that allows a user to add a 
    country with data for each indicator.
    <hr>
    User can type 1: `Add` in main menu, to enter add menu. 
    he will be asked for country Code. Country code has to be 
    three letters, can be lower cased, after the input letters
    will be uppercased. If found contry will
    be found, user will be returned to main menu with error.
    Upon successful add, success message is printed. 
    The possibility of `null` value is realized
    by typing "null" for decimals only. For more see, 
    `addNewCountry()`
    in [DatabaseMenuPrompter]. Actual add to database is
    done in [CountriesDaoImplementation] method `save`, like 
    all other database interactions. Problem of wrong user inputs
    is solved at this level, when user is promted, his data is
    processed, so that it can later succesfully go to database.
<hr>
9. <a id="task-9"></a>
    Write the application code that allows a user to delete a 
    country’s data.
    <hr>
    User can type 2: `Delete` in main menu, to enter delete menu. 
    He will be asked for country Code. Country code has to be 
    three letters, can be lower cased, after the input letters
    will be uppercased. If found contry will
    be found, it will be deleted.
    Upon successful deletion, success message is printed. 
    For more see `deleteCountryByCode()`
    in [DatabaseMenuPrompter]. Actual delete in database is
    done in [CountriesDaoImplementation] method `delete`, like 
    all other database interactions.
<hr>
