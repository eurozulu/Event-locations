Test Code Example

To get repository:
git clone https://github.com/eurozulu/Event-locations.git


To build, from within the main project directory:
(Not the '>' )
>mvn clean install

This will generate a target/ directory containing the executable jar:
"codetest-1.0-SNAPSHOT.jar"

To execute:

>java -jar target/codetest-1.0-SNAPSHOT.jar

Usage:

enter the user location as x,y where x and y are integers between -10 and +10.
A list of upto five events will be shown in order of distance from that location,
with the nearest event being first.
e.g 
-6, 9
Will return a list of five events closest to the location (-6, 9).

An additional, optional parameter can be given, by following the location with a 
'~'D, where D is the maximum distance, in blocks, to conduct the search.
This returns ALL the events within the given distance. The limitation of five does
NOT apply when the distance is provided.

e.g
-6, 9 ~ 15
Returns a list of all events within a 15 block distance from the location.
 
