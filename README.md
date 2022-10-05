This is a fork of https://github.com/borissmidt/gradle-mima-plugin with updated dependencies.

Adds mima dependency check to your gradle project
it looks for the previous version by looking at the git version tags:
```groovy
mima {
    direction = "backward"  can be "forward" , "backward" or "both"
    //makes the task fail if there was an error detected    
    failOnException = false 
    //the versions to check, defaults to the latest version detected in the git tag.
    compareToVersions = ["0.0.0", "0.1.0"]
}
```
