## Build
Build by changing into this directory (currently important due to relative paths, to be refined)
Note: Dependencies will be downloaded only when missing on machine.

`cd [root folder stundenplan_service]`

`docker build -f ./docker/Dockerfile . --tag=timetable-service:latest`

## Run

Run by executing the following, make sure the tag is exactly the one as defined in the build 
`docker run -p8080:8080 timetable-service:latest`

## Note

- Builds only work if jar is packaged and in the target folder specified in the Dockerfile
- updates in the sourcecode needs a rebuild in Docker as well