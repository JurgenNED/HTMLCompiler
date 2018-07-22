## HtmlCompiler

The HTML compiler is a maven plugin.
Its purpose is to provide compile time features for generating static HTML resources for a frontend project.

The idea is to make it easier to write webapps by allowing the developer to write source code and have the build system generate resources for deployment.
No more minified files everywhere. No more manually compressing resources.

### Dependencies

To compile typescript the code calls `tsc`. Have it installed.

### Supported features

- Parses HTML as HTTL templates using the HTTL template engine
- Compresses HTML (remove comments, remove whitespace)
- Inlines javascript
- Minifies javascript
- Inlines images
- Inlines stylesheets
- Minifies stylesheets (removing comments, removing whitespace)
- Generates integrity tag for remote resources
- Adds an include statement to JavaScript
- Also compresses HTML inside script tag with type text/x-jquery-tmpl
- Allow for calling htmlcompiler features through the command line
- Inlining of link tags (favico)
- SASS and LESS compiling
- Inline link tag to stylesheet into style tag

### Planned features (bugfixes)

- Add an error reporter for when JavaScript or CSS has errors
- Add smart CSS diffing
- Add CSS file merging / filtering
- Add JS file merging / filtering
- Add combining of multiple inlined script and style tags into a single tag
- Scaling images to a given size
- Remove unnecessary meta tag in output
- Removing unused JavaScript functions
  - Integrate https://github.com/google/closure-compiler
- Obfuscate css and javascript by rearranging code
- Support different template engines
- Inline remotely hosted JavaScript and CSS
  - Check validity using integrity tag
- Add --help option

### How to use as maven plugin (1)

1. Checkout the code using `git clone repo`
2. Run `mvn install`.
3. Add this code to the pom:
```
<plugin>
    <groupId>com.github.codemonstur</groupId>
    <artifactId>htmlcompiler</artifactId>
    <version>0.0.11</version>
    <executions>
        <execution><goals><goal>htmlcompile</goal></goals></execution>
    </executions>
</plugin>
```

The code will compile all HTML files in `src/main/websrc` to `target/classes/webbin`.

### How to use as maven plugin (2)

Import jitpack as a plugin repository
```
<pluginRepositories>
    <pluginRepository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </pluginRepository>
</pluginRepositories>
```

Import the plugin
```
<plugin>
    <groupId>org.bitbucket.codmonster</groupId>
    <artifactId>htmlcompiler</artifactId>
    <version>0.0.6</version>
    <executions>
        <execution><goals><goal>htmlcompile</goal></goals></execution>
    </executions>
</plugin>
```

There is only a single goal at this time: htmlcompile. 

### How to use as maven plugin (3)

I want to put it in maven central. Haven't gotten to it yet.

### Configure the plugin

If you want to override the default configuration you can do that through the pom like this:
```
<configuration>
    <outputDir>${project.build.directory}/classes</outputDir>
    <templateSets>
        <templateSet>
            <rootDir>${basedir}/src/main/websrc/</rootDir>
            <templateDir>${basedir}/src/main/websrc/pages</templateDir>
        </templateSet>
        <templateSet>
            <rootDir>${basedir}/src/main/websrc/</rootDir>
            <templateDir>${basedir}/src/main/websrc/error</templateDir>
        </templateSet>
    </templateSets>
</configuration>
```

### How to use from the command line

The tool can be used from the command line as well.
In order to set this up do the following:
1. Clone the repository into a directory
2. Run `mvn clean package shade:shade@shade`
3. Copy the generated jar file (`c`) to a location of your choice
4. Open `~/.bashrc` or `~/.bash_profile` and add an alias `alias hc='java -jar /your/chosen/dir/htmlcompiler.jar'`
5. Reload the above file by doing `source ~/.bashrc` or `source ~/.bash_profile`

You can now run the tool from the command line using the `hc` command.
Running it directly will show the following output:
```
$ hc
Missing required option: i
usage: htmlcompiler [OPTIONS] compile|compress|diff|verify
 -i,--input <arg>    Input file
 -o,--output <arg>   Output file
 -r,--root <arg>     Resource loading root
 -t,--type <arg>     File type
```