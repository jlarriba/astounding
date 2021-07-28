# astounding
A multiplatform GUI for the reMarkable tablet. It tries to address the lack of a GUI to manage the book collection via the reMarkable Cloud, so linux is it's primary target.

Currently it is able to upload epub and pdf, export pdf (without annotations), display books and folders, navigate and delete books and folders.

Double clicking on each book will export the pdf. Double click on each folder will open it. For the moment, only uploads to the root folder is allowed.

# Usage
Java 11 is required to be installed. OpenJDK11 will do fine.

The authentication is not yet implemented, but it uses [jrmapi](https://github.com/jlarriba/jrmapi) to communicate with the reMarkable cloud, which is compatible with the .rmapi file created by the [rmapi](https://github.com/juruen/rmapi) project. If you are already using it, you dont have to do anything. If not, download it and use its login. That will generate a .rmapi in you userdir with the corresponding tokens. They will be used by astounding to connect to your reMarkable cloud.

### rmapi
Download the last release from the [Github releases page](https://github.com/juruen/rmapi/releases).
```
$ tar -zxvf rmapi-linuxx86-64.tar.gz
$ chmod +x rmapi
$ ./rmapi
```
Then create a symlink for the token file:
```
$ ln -s ~/.config/rmapi/rmapi.conf ~/.rmapi   
```

### astounding
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



