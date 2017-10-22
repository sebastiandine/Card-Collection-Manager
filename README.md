# Card Collection Manager
![img1](img1.png)
## Project Description
This projects provides a management application for Magic the Gathering card collections. Its aim is to be a simple solution to keep track of collections. The main reason why I wrote this application is because I required a tool, which can manage single card images of a large collection, beside common card collection management features.

The application is completely written in Java, using Swing for the graphic interface and the built-in serialization for saving the collection data.

## License
This project is licensed under the zlib license. See also the attached [LICENSE](./LICENSE) file.

## How to use
You find the latest build as archive files in folder [build](./build). Unpack and use them.

Be aware that you can edit the directory in which the application stores both, your card data as well as your uploaded images.
It is highly recommended to adjust this to your requirements before you start using the application since later editing might
lead to inconsistent data.

## How to update
If you want to update your local version of the application. Just overwrite the your local `CardCollectionManager.jar` with the one
you find in the archive file of the latest release. Do however not overwrite your data or config files.

## How to build/ contribute
* Increase the build count of the current version in file `pom.xml`. Tag `<version>` is representing the current version and build number. So if the current version is 0.1-01, you should change it to 0.1-02 before you commit your changes. The number before the dash is the current main release which should not be changed, except by the main contributor. 

* Execute `mvn clean` in order to cleanup the build directory.
* Execute `mvn package` in order to compile the project and create various archive files in folder `build/`, ready for shipping.

## Toolkit
* [JDK v.1.8.0_65](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java Development Kit. What else can I say?


* [Apache Maven v.3.5.0](https://maven.apache.org/) - The famous build management tool, specialized for Java projects.
	
		* Maven Clean Plugin v.3.0.0
		* Maven Resources Plugin v.3.0.2
		* Maven Assembly Plugin v.3.1.0


* [Eclipse Java EE IDE for Web Developers v.4.5.0 (Mars)](https://www.eclipse.org) - Maybe the most famous IDE for Java developers.

		* M2Eclipse 1.7.0

* [Apache Log4j v.2.8.2](https://logging.apache.org/log4j/2.x/) - The famous logging framework for Java applications.

* [JDatePicker v.1.3.4](https://jdatepicker.org) - JDatePicker and JDatePanel is an set of advanced DatePicker controls for Java Swing applications.

* [Magic: The Gathering Java SDK v.0.0.6](https://github.com/MagicTheGathering/mtg-sdk-java) - Java based encapsulation of the offical [MtG API](https://magicthegathering.io).
