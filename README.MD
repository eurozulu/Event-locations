**Test Code Example**

**Built environment**<br/>
* Java version: 1.8.0_121 (build 1.8.0_121-b13), vendor: Oracle Corporation
https://www.oracle.com/java
* Maven: version 3.5.0
https://maven.apache.org
* Git: version 2.12.2
https://git-scm.com/

**Test Environment**<br/>
OS name: "mac os x", version: "10.12.6", arch: "x86_64"
Default locale: en_US, platform encoding: UTF-8

<br /><br />

**Repository**<br />
    git clone https://github.com/eurozulu/Event-locations.git

**Build Instructions**<br />
To build, from within the project directory:
>`mvn clean install`

This will generate a `target/` directory in the project directory,
containing the executable jar:<br />
`codetest-1.0-SNAPSHOT.jar`
<br />

To execute:
>`java -jar target/codetest-1.0-SNAPSHOT.jar`

<br />
<br />

**Usage**<br />
Enter the user location as `x,y` where x and y are integers between -10 and +10.
A list of up to _five_ events will be shown, in order of distance from that
given location, with the nearest event being first.
e.g.<br />
`6, 9`<br />
`Searching location X:6 , Y:9 over distance of: limitless blocks`<br />
`Event 17 - $5.65, Distance 4`<br />
`Event 28 - $0.91, Distance 7`<br />
`Event 1 - $5.07, Distance 7`<br />
`Event 3 - $29.31, Distance 8`<br />
`Event 23 - $22.43, Distance 9`<br />

-6, 9
Will return a list of five events closest to the location (-6, 9).

An additional, optional parameter can be given, by following the location
 with a '~'D, where D is the maximum distance, in blocks, to conduct the search.
This returns ALL the events within the given distance, from the given location. 
i.e. The limitation of five does NOT apply when the distance is provided.<br />
e.g<br />
`6, 9 ~ 15`<br />
`Searching location X:6 , Y:9 over distance of: 15 blocks`<br />
`Event 17 - $5.65, Distance 4`<br />
`Event 28 - $0.91, Distance 7`<br />
`Event 1 - $5.07, Distance 7`<br />
`Event 3 - $29.31, Distance 8`<br />
`Event 23 - $22.43, Distance 9`<br />
`Event 2 - $4.64, Distance 9`<br />
`Event 19 - $72.30, Distance 9`<br />
`Event 18 - $11.00, Distance 10`<br />
`Event 16 - $29.68, Distance 10`<br />
`Event 9 - $23.40, Distance 10`<br />
`Event 7 - $26.77, Distance 11`<br />
`Event 29 - $26.75, Distance 11`<br />
`Event 26 - $11.18, Distance 12`<br />
`Event 15 - $29.54, Distance 13`<br />
`Event 25 - $13.47, Distance 13`<br />
`Event 13 - $11.95, Distance 15`<br />
`Event 22 - $41.26, Distance 15`<br />

Returns a list of ALL events within a 15 block distance from the location.
