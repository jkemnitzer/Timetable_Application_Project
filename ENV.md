# Setup VM (running ubuntu server) 
### nl-1.netlab.hof-university.de

## Install docker

(follow docker sources)

## Global Proxy setup

For Proxy settings in netlab env edit `/etc/systemd/system/docker.service.d/http-proxy.conf`

Add proxy conf in format:   
```
[Service]
Environment="http_proxy=http://proxy.netlab.hof-university.de:3128/"
Environment="https_proxy=http://proxy.netlab.hof-university.de:3128/"
Environment="ftp_proxy=http://proxy.netlab.hof-university.de:3128/"
Environment="NO_PROXY=localhost,127.0.0.1,::1"
```

Restart docker services:
```
systemctl daemon-reload &&
systemctl restart docker
```

## Check status of docker

Visit `https://nl-1.netlab.hof-university.de:9443`
Credentials are `admin:JKTGsLqmM6PusUj`

## Docker Compose

Install via:

`sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose`

Make binary executable:

` sudo chmod +x /usr/local/bin/docker-compose`

Test via: 

`docker-compose --version`

## Deploy app

`docker-compose -env-file ENV=<prod,dev or local>`


