This library provides a variety of largely independent utility classes and routines for constructing GWT applications. Detailed information is best obtained by inspecting the Javadoc for the individual classes. A brief overview of the types of things available follows:

  * [FluentTable](http://ooo-gwt-utils.googlecode.com/svn/apidocs/com/threerings/gwt/ui/FluentTable.html) simplifies the process of constructing tables in UIs.
  * [PagedGrid](http://ooo-gwt-utils.googlecode.com/svn/apidocs/com/threerings/gwt/ui/PagedGrid.html) and [PagedTable](http://ooo-gwt-utils.googlecode.com/svn/apidocs/com/threerings/gwt/ui/PagedTable.html) in conjunction with [PagedResult](http://ooo-gwt-utils.googlecode.com/svn/apidocs/com/threerings/gwt/util/PagedResult.html), [SimpleDataModel](http://ooo-gwt-utils.googlecode.com/svn/apidocs/com/threerings/gwt/util/SimpleDataModel.html), [ServiceBackedDataModel](http://ooo-gwt-utils.googlecode.com/svn/apidocs/com/threerings/gwt/util/ServiceBackedDataModel.html) and [PagedServiceDataModel](http://ooo-gwt-utils.googlecode.com/svn/apidocs/com/threerings/gwt/util/PagedServiceDataModel.html) assist with the on-demand fetching and display of large data sources.
  * [LimitedTextArea](http://ooo-gwt-utils.googlecode.com/svn/apidocs/com/threerings/gwt/ui/LimitedTextArea.html) allows entry of a fixed amount of text and displays feedback to the user on the number of characters remaining.

Browse the [API documentation](http://ooo-gwt-utils.googlecode.com/svn/apidocs/overview-summary.html) for additional useful classes.

## Adding gwt-utils to your build ##

Integration into a Maven- or Ivy-based build is easy. Add a dependency on `com.threerings:gwt-utils:1.2`. gwt-utils is published to Maven Central, so you need not add it to your local Maven repository.

Then add the following to your `.gwt.xml` file:

```
  <inherits name="com.threerings.gwt.Utils"/>
```

You can also obtain the necessary jar files manually from the following locations:

  * [samskivert-1.1.jar](http://repo1.maven.org/maven2/com/samskivert/samskivert/1.1/samskivert-1.1.jar)
  * [gwt-utils-1.2.jar](http://repo1.maven.org/maven2/com/threerings/gwt-utils/1.2/gwt-utils-1.2.jar)

## Discussion ##
Feel free to pop over to the [OOO Libs](http://groups.google.com/group/ooo-libs) Google Group to ask questions and get (and give) answers.

## Errata ##
The library is actively developed by engineers at <a href='http://www.threerings.net/'>Three Rings</a> (affectionately known as OOO) and is in use on a few sizable GWT applications like http://www.whirled.com/ and http://apps.facebook.com/everythinggame/.