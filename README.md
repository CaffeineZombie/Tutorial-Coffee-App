# Tutorial-Coffee-App
This is a simple andorid app that incorporates the basics of OOP as used by Java and Andorid. 
The bulk of the learning provided in this project is faimliarity with Android Studio, XML, Java, and basic OOP principles.

Intended use:
  - This is a mock coffee ordering app where the user specifies number of drinks, additional toppings, and requests the order. On submission of the order, the suammy of drink specifics, who ordered, and price should be auto-populated to an email which defaults to the address specified in the 'strings.xml' document as a default user. 

The program from a functionality standpoint:
  - Responsive to user input
  - Update on user actions
  - Support mulitple languages through XML ref resource management
  - Use Intents to create email summary
  - Incorporate styles and themes
  - Build off of design guidelines as provided in the Android documentation
  
Coding Principles Used:
- XML layout design
  - Nested views
  - ScrollView, LinearLayout, CheckBox, EditText, TextView
  - Initialization values versus @string resources
  - @id/ resource definition
  - onClick method link to Java code
  - style="@style/" attributes from overall app/res/values/styles.xml file
  - app/res/values/strings.xml files for language support

- Java language, classes, and methods
  - Local versus global variables
  - Methods:
      - Private versus public methods
      - Return types
      - Arguments
      - Constructor versus factory methods
      - Nested calls
      - 
  - Classes:
      - AppCompatActivity, Intent, View, EditText, CheckBox, TextView, String, NumberFormat
      - Casting to initialized a class as a specific subclass type
      - R (resource) class methods, namely id
      - @Override to add custom functionality to a defined class method
        -Specifically to onCreate method of app creation
      - extend a class AppCompatActivity with methods of created app class MainActivity
  - Basic logical statements and operators (if, elseif, else)
  - Logging with use of Log.v() 
