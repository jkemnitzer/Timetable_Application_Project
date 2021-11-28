FROM node:14-alpine as build

ARG ENV

WORKDIR /usr/local/app
COPY . /usr/local/app/

RUN \[[ $ENV != "local" ]] && npm config set proxy "http://proxy.netlab.hof-university.de:3128" || echo "Skipping PROXY"
RUN \[[ $ENV != "local" ]] && npm config set https-proxy "http://proxy.netlab.hof-university.de:3128" || echo "Skipping HTTPS_PROXY"
RUN npm config list
RUN npm install
RUN npm run build

FROM nginx:latest
COPY --from=build /usr/local/app/dist/stundenplaner-app /usr/share/nginx/html
