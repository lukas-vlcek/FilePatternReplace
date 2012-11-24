# FilePatternReplace

=== A Groovy [GPars](http://gpars.codehaus.org) experiment ===

### What it can do?

You have some files and you want to replace some pattern in them? This little utility can help you.

More specifically, you can specify:

1. Source **folder** where the files are located (all subfolders are processes as well).
2. Regex **pattern** to find in each file.
3. A **value** to replace all occurrences of the pattern.
4. [optional] A number of **threads** to be used for parallel processing. Defaults to `4`.
5. [optional] A file **extension** for new files. Defaults to `mod`.

### How to build it?

- You need Java. I use Java version `1.6.0_37`. 
- You need Maven. I use maven `3.0.3`.
- Clone repository: `git clone https://github.com/lukas-vlcek/FilePatternReplace.git`.
- Then `cd FilePatternReplace`.
- And `mvm clean package`. 

### How to use it?

After you have [built it](#how-to-build-it) navigate to `target` folder. There you will find file called `FilePatternReplace.jar` and folders `classes` and `lib`; everything else found in `target` folder is not important (and could be deleted).

To make use of the FilePatternReplace just type:

```
java -jar FilePatternReplace.jar folder pattern value [threads] [extension]
```

### How do I learn what it does?

Configure logging!
By default logging configuration can be found in `target\classes\log4j.properties`. You can either change this file or provide your own configuration file using `-Dlog4j.configuration` option.

For example:

```
java -Dlog4j.configuration=/path/to/log4j.properties \
     -jar FilePatternReplace.jar folder pattern value [threads] [extension]
```

### Where does it store modified files?

It stores files next to the original files.

So for example if the following file contains **pattern** that we want to replace with a **value**

```
/Users/lukas/tmp/file.txt
```

Then the file with replaced patterns will be located in 

```
/Users/lukas/tmp/file.txt.mod
```

### I found a bug, what shall I do?

Feel free to open a [new ticket](https://github.com/lukas-vlcek/FilePatternReplace/issues), however; this was implemented as an experiment so I do not promis that I will pay attention to any issues. But if you prepare a [pull request](https://github.com/lukas-vlcek/FilePatternReplace/pulls) then I will definitely have a look at it :-)

_Enjoy!_