This is a fork of https://github.com/borissmidt/gradle-mima-plugin with updated dependencies and some changes to allow additional configuration.

Adds mima dependency check to your gradle project
it looks for the previous version by looking at the git version tags:
```groovy
mima {
    direction = "backward"  can be "forward" , "backward" or "both"
    //makes the task fail if there was an error detected    
    failOnException = false 
    //the versions to check, defaults to the latest version detected in the git tag.
    compareToVersions = ["0.0.0", "0.1.0"]
  
    // Use this if the previous version had a different group.
    oldGroup = 'com.example'
    // Use this if the previous version had a different artifact name.
    oldName = 'oldname'

  // You can manually pick how to extract versions from tags.
  final Pattern pattern = Pattern.compile("version(\\d+\\.\\d+\\.\\d+)")
  tagFilter = { String tag ->
    final Matcher match = pattern.matcher(tag)
    return java.util.Optional.ofNullable(match.matches() ? match.group(1) : null)
  } as java.util.function.Function
}
```
