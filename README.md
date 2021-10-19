# Praktikum
Repository für das Projekt im Modul "Praktikum"

## Summary
Im Rahmen des Praktikums soll eine Anwendung zur automatischen Verwaltung der Raum- und Zeitplanung erstellt werden. Dabei sollen neben den notwendigen Kriterien (Raumgröße zur Anzahl der Studenten, zeitliche Belegung) auch weitere Dinge berücksichtigt werden, beispielsweise in den Räumen verfügbare Technik und zeitliche Zusammenlegung von Blöcken.

## Stack Blueprint

### Backend
#### Programmlogik
Die Programmlogik wird in der Programmiersprache Java geschrieben. Das wird mit der allgemeinen Vertrautheit der Entwickler mit Java begründet.
Die zu verwendende Version ist aktuell Java 16, sollte aber im Laufe der nächsten Wochen auf die aktuelle (Stand 10/2020) LTS Version Java 17 hochgezogen werden.

#### Frameworks
Um unnötigen Code zu vermeiden und die Anwendung von Anfang an etwas stabiler zu machen wird das Web-Framework Spring-Boot genutzt.

Das bringt den Vorteil, dass das Spring-Boot Framework schon einige Bibliotheken und Funktionen "out of the box" bereitstellt. Dazu gehören REST-Funktionalitäten (Client und Server), In-Memory Datenbanken, Profilmanagement (dev, prod, test) und vieles mehr. Zudem ist das Spring Boot Framework hervorragend dokumentiert und erfreut sich einer großen Nutzerbasis.

Zu Spring Boot werden ebenfalls die folgenden Bibliotheken eingebunden:

Lombok: Reduzierung von "Boilerplate" Code, automatisches Einbinden von z.B. Gettern, Settern, Logging, etc.
H2: In-Memory Datenbank zum einfachen testen der Anwendung ohne eigentliche Datenbank. Leichgewichtig und garantiert gleichen Stand durch Generierung über Source Code
Spring Data JPA: Einfache Datenanbindung an Datenbanken, ohne sich auf genaue Struktur der DB, sowie genaue Instanz und Art der DB festzulegen. Einfaches Tracking von Änderungen über sogenannten "Kontext"

#### Datenbank

Die Datenbank wird zwangsläufig eine klassisch relationale Datenbank sein. Da am Anfang das Arbeiten mit Testdaten durchaus ausreichend ist muss sich zu diesem Zeitpunkt keine Gedanken um den Hersteller der DB gemacht werden.

#### Hosting

##### Lokal

Da Spring-Boot einen integrierten Tomcat Server mitbringt, auf dem die Anwendung automatisch gehostet wird, sobald die gepackagete *.jar gestartet wird, wird lokal kein hosting in irgendeiner Art benötigt.

##### Remote

Um den Aufwand des Deployments zu minimieren und die Möglichkeiten einer Pipeline bestmöglichst ausnutzen zu können werden die einzelnen Module in Containern gehostet. Hierfür wird Docker genutzt. Somit werden für die gesamte Architektur folgende Container:

Backend Container: Bestehend aus der Spring Boot Anwendung, leichtgewichtig und ohne persistente Daten
Frontend Container: Bestehend aus der node.js Umgebung mit einer Angular CLI, alternativ nur statisch gehostet nach build und deployment
Datanbank Container: Bestehend aus Container und persistenter Volume

### Frontend
Das Frontend wird klassisch über statischen Content gehostet. Programlogik werden somit über HTML, Javascript und CSS generiert. Jedoch stehen dahinter die Funktionalitäten des Web-Frameworks Angular.

#### Angular

Angular ist ein Web-Framework, das Webseiten durch verschiedene Programmiersprachen und das Einbinden eigener Funktionen zu einer klassischen HTML/CSS/JS Seite kompiliert.

Für diesen Stack nutzen wir Angular in Verbindung mit Typescript.

Angular ist einer der aktuell führenden und verbreiteten Web-Frameworks und somit gut dokumentiert.

#### Lokales Arbeiten

Lokal kann Angular nach dem Installieren von node.js (V16+) und npm (V8+) über die Angular CLI genutzt werden. Diese kann über npm installiert werden.

Nach Initiierung des Projektes aus dem Repository und Installation der Dependencies (node_modules) kann das Projekt lokal über den Befehl ng serve [--open] gehostet werden.

Das hat den großen Vorteil, dass der Angular Compiler Änderungen "on the fly" erkennt, neu baut und den Webcontent neu lädt.

#### Deployment und Hosting

Vor einem Deployment oder produktiven Hosting muss der statische Webcontent kompiliert und im Build-Stage des Docker Containers (siehe "Hosting" in Wiki/Stack-Blueprint/Backend) kopiert und eingebunden werden. Dann kann er über node js gehostet werden oder Alternativ auf einem beliebigen Server (Apache, etc.) gehostet werden.
