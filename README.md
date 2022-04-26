# What is Shopfly?

Shopfly is modular, high performance, headless e-commerce(ecommerce) platform built with Java,Springboot, Vue.

# Architecture

Shopfly is built based on spring boot + Vue technology, and the infrastructure is Mysql, Redis, Elasticsearch, RabbitMq

![Architecture](https://www.shopfly.cloud/images/Architecture_Overview.png) 

# Demo

[**storefront**](http://demo.shopfly.cloud)

[**dashboard**](http://dashboard.shopfly.cloud)

# Quick Start

## Requirements
1. [Docker](https://docs.docker.com/install/)
2. [Docker Compose](https://docs.docker.com/compose/install/)
3. Recommended server hardware

    4CPU & 8G Memory

## How to run it?

1. Clone the repository:

```
sudo git clone https://github.com/shopflix/docker.git
```


2. Modify domain name configuration:

```
cd docker
sudo vi backend.env
```

Specifies the address of the API service：
```
API_BASE=your API service address
API_BUYER=your API service address
API_SELLER=your API service address
```
If you do not specify the API address where the shopflix demo will be used

3.Run the application:

```
docker-compose up
```
or 

```
docker-compose up -d
```
Will run in the background

## Visit address

storefront: `yourip:3001`

dashboard: `yourip:3002`

api: `yourip:8080`

# Dashboard Source

https://github.com/shopflix/dashboard

# Storefront Source

https://github.com/shopflix/vue-storefront




# Framework Detail

## backend

| Core Framework             | Spring Boot          |
| -------------------------- | -------------------- |
| ORM                        | Spring Jdbc Template |
| Database  connection pools | Druid                |
| Cache                      | Redis                |
| Database                   | Mysql                |
| Search  Engine             | Elasticsearch        |
| Mq                         | RabbitMq             |



## frontend

| Base Framework   | Vue、Uniapp |
| ---------------- | ----------- |
| Base  UI         | Element UI  |
| HTTP  Client     | Axios       |
| SSR              | Nuxt.js     |
| Pattern  Checker | eslint      |
| CSS  Extension   | Sass        |
| Report           | ECharts     |

# Contact Us

service@shopflix.dev



# website

https://www.shopflix.dev/



