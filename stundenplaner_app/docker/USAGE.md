## Build
Build by changing into this directory (currently important due to relative paths, to be refined)
Note: Dependencies will be downloaded only when missing on machine.

`cd [root folder stundenplan_app]`

`docker build -f ./docker/Dockerfile . --tag=timetable-app:latest`

## Run

Run by executing the following, make sure the tag is exactly the one as defined in the build
`docker run -p 80:80 timetable-app:latest`

## Note

- Builds only work if jar is packaged and in the target folder specified in the Dockerfile
- updates in the sourcecode needs a rebuild in Docker as well
