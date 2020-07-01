# astounding
A multiplatform GUI for the reMarkable tablet, it tries to address the lack of a GUI to manage the book collection via the reMarkable Cloud, so Linux is its primary target.

Currently it is able to upload epub and pdf, export pdf (without annotations), display books and folders, navigate and delete books and folders

Double click on each book will export the pdf. Double click on each folder will open it. For the moment, only uploads to the root folder is allowed.

# Usage
Java 11 is required to be installed. OpenJDK11 will do fine.

Download the corresponding release from the [Github releases page](https://github.com/jlarriba/astounding/releases).

```
$ java -jar astounding.jar
```

# Build
Building requires Java 11. OpenJFX is downloaded via Maven so no need to install the SDK.

 ```
 $ mvn clean package
 $ mvn javafx:run
 ```

If the fatjar wants to be build, use the fatJar profile while building:

```
$ mvn clean package -PfatJar
```

# disclaimer
The project is provided as-is, without warranty or support.

The author(s) and contributor(s) are not associated with reMarkable AS, Norway. reMarkable is a registered trademark of reMarkable AS in some countries. Please see https://remarkable.com for their product.



