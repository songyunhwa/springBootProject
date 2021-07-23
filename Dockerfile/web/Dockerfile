#!/bin/sh

## vue.cmd create vue-test

FROM nginx:1.16.1-alpine

COPY ./default.conf /etc/nginx/conf.d/default.conf
COPY ./nginx.conf   /etc/nginx/nginx.conf

EXPOSE 8080

VOLUME [ "/etc/nginx" ]

CMD ["nginx", "-g", "daemon off;"]