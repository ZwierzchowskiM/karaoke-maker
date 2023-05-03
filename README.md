# KaraokeMaker

## Table of Contents
* [General Info](#general-information)
* [Demo](#demo)
* [Technologies Used](#technologies-used)
* [Requirements](#requirements)
* [Room for Improvement](#room-for-improvement)


## General Information
This application is a generator for instrumantal piano for vocalists. It's allows to create full piano instrumental track using pre-recorded fragments by merging them.
User can choose each single chord with diffrent variation to make full instrumental track. 


## Demo
Here is a song created using KaraokeMaker :
<div align="left">
  <a href="https://www.youtube.com/watch?v=tBCPIKT7ZMw"><img src="https://github.com/ZwierzchowskiM/ZwierzchowskiM/blob/main/KaraokeMakerDemo.PNG" width="600" height="300">
</div>
	
Piano instrumental was generated by KaraokeMaker API, 
vocal path for presentation purposes was merged in DAW software.


## Technologies Used
Project is created with:

* Java 17
* Spring Boot version 2.7.5
* Spring Data JPA
* H2 Database
* Swagger
* Docker
* Lombok
* Ehcahe

## How to run

To get this project up and running, navigate to root directory of an application and execute following commands:

* Create a jar file.
```
$ mvn package
```

* Then build docker image using already built jar file.

```
$ docker build -t karaokemaker .

```

* Run whole setup

```
$ docker run -p 8080:8080 karaokemaker
```
You can open Swagger UI at address:
http://localhost:8080/swagger-ui/#/

Something similar to this should appear:

<div align="left">
 <img src="https://github.com/ZwierzchowskiM/karaoke-maker/blob/main/Files/images/swagger_ui.PNG" width="600" height="300">
</div>
	
## Instruction of use

While creating song user can choose each chord:

* Single Note:
	C, DB, D, Eb, E, F, Gb, G, A, Bb, B
* Type:
	MAJ, MIN
* Bass Note:
	ROOT, THIRD
* Complexity:
	SINGLE_CHORD, VERSE_1, VERSE_2, VERSE_3, VERSE_4, BUILD_UP, CHORUS_1, CHORUS_2, CHORUS_3, CHORUS_4, LAST_CHORD
* Length:
	HALF_BAR, FULL_BAR

Here is an example of Json request which was used for create demo song:
[link](/Files/demo_request.json)

## Requirements

* JDK 17
* Docker

## Room for Improvement

* Application is using piano samples recorded in 60bpm. To allow creating song in others bpm needs to add other samples.

