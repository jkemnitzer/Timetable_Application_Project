FROM node:14-alpine as build

WORKDIR /usr/local/app
COPY . /usr/local/app/

RUN npm install
RUN npm run build

FROM nginx:latest
COPY --from=build /usr/local/app/dist/stundenplaner-app /usr/share/nginx/html
