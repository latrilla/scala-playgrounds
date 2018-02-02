# scala-playgrounds
Scala Repository to do stuff


## Aggregates : 

Compile it from sources: 
> sbt compile

Run unit tests
> sbt test

Run the program
> sbt "run src/resources/xag.csv"

It uses a VM with 3Gb and runs for a few minutes (212s on an I7-7500U processor)

TODO : 
* Check the RandomAccessFile to read and write more efficiently
  * Reading : https://blog.knoldus.com/2012/03/20/handling-large-data-file-using-scala-and-akka/
  * Writing : https://stackoverflow.com/questions/1062113/fastest-way-to-write-huge-data-in-text-file-java (answer 2)
* Take a look at a Spark solution, if one day you have a spark cluster sous la main

