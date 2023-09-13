# API Security
All your APIs belong to me - API Security Demos

* [Introduction](#introduction)
* [Prerequisites]()
* [Presentation Slides](#presentation-slides)
* [Demos](#demos)
  * [Discover and Analyze APIs](#discover-and-analyze-apis)
  * [Hack APIs](#hack-apis)
    * [API1:2023 Broken Object Level Authorization](#api12023-broken-object-level-authorization)
    * [API2:2023 Broken Authentication](#api22023-broken-authentication)
    * [API3:2023 Broken Object Property Level Authorization](#api32023-broken-object-property-level-authorization)
    * [API4:2023 Unrestricted Resource Consumption](#api42023-unrestricted-resource-consumption)
    * [API5:2023 Broken Function Level Authorization](#api52023-broken-function-level-authorization)
    * [API6:2023 Unrestricted Access to Sensitive Business Flows](#api62023-unrestricted-access-to-sensitive-business-flows)
    * [API7:2023 Server Side Request Forgery](#api72023-server-side-request-forgery)
    * [API8:2023 Security Misconfiguration](#api82023-security-misconfiguration)
    * [API9:2023 Improper Inventory Management](#api92023-improper-inventory-management)
    * [API10:2023 Unsafe Consumption of APIs](#api102023-unsafe-consumption-of-apis)

## Introduction

This repository contains sample demos to show possible API security risks as listed on the [OWASP API Security Top Ten (2023)](https://owasp.org/API-Security/editions/2023/en/0x00-header/).
You can find two variants of the demo APIs (both implemented as Java applications using Spring Boot):

* insecure-api: This is the insecure one with different built-in flaws of the OWASP API Security 2023.
* secure-api: This the secure variant that shows different ways on how to defend against those kind of attacks

## Prerequisites

To try all the demos in this repository you need:

* A Java SDK, at least LTS version 17
* Any JAVA IDE you like
* Any tool to execute REST or GraphQL API calls (recommended tool is [Postman](https://www.postman.com/), but you may also use [curl](https://curl.se/) or [httpie](https://httpie.io/)) 
* Python 3.6+ and the [JWT_Tool](https://github.com/ticarpi/jwt_tool) to forge JSON Web Tokens (JWTs)
* The [custom spring authorization server](https://github.com/andifalk/custom-spring-authorization-server) as identity provider when you want to try the secure api application. 

## Presentation Slides

* [Spring I/O 2023 Barcelona](presentations/spring_io_2023/All_your_APIs_are_mine_springio_2023.pdf)

## Demos

### Discover and Analyze APIs

#### Google Dorks

```
inurl:"/includes/api/" intext:"index of /"
```

```
intitle:"index of" api_key OR "api key" OR
apiKey -pool
```

#### Shodan

Just try this one on https://shodan.io

```http request
"content-type: application/json"
```

#### OWASP Amass

Try to find Twitter APIs in a _passive_ scan.

```shell
amass enum -passive -d twitter.com |grep api
```

> _Never_ perform __active__ scans on websites you do not own or are not authorized to. __Passive__ scans are not an issue.

#### API Documentation

If your API documentation is available without any restrictions then everybody (also the _bad_ guys) can grab it and for example just import these into [Postman](https://www.postman.com/) and build collections to make it easier to hack your APIs.

Try the following URLs on the _Insecure API_ project:

* [Open API 3 (/v3/api-docs)](http://localhost:9090/v3/api-docs)
* [Swagger (/swagger-ui.html)](http://localhost:9090/swagger-ui.html)
* [GraphQL Schema (/graphql/schema)](http://localhost:9090/graphql/schema)
* [GraphiQL web console (/graphiql)](http://localhost:9090/graphiql)

### Hack APIs

#### API1:2023 Broken Object Level Authorization

Try to access another user's vehicle, you should not have access to.

1. Start the `insecure-api` application
2. Login using http://localhost:9090/api/v1/users/login
   and specify the credentials `bruce.wayne@example.com / wayne` to log in
3. Try to enumerate getting a vehicle from http://localhost:9090/api/v1/vehicles/{vehicle_id}, e.g. http://localhost:9090/api/v1/vehicles/1
4. Check if you can see vehicle data

Now try the same on the secure API.

1. Stop the `insecure-api` application
2. Start the `secure-api` application
3. Login using http://localhost:9090/api/v1/users/login is not possible anymore.
   This is replaced by an identity provider (spring authorization server). So you have to start the [custom spring authorization server](https://github.com/andifalk/custom-spring-authorization-server) and specify the credentials `bwayne / wayne` to log in using the OAuth2/OpenID Connect protocol. You can trigger this by navigating to the _Authorization_ tab of the top level folder of in the Postman collection. Use the token and store it as variable `bearer_token`.
4. Try to enumerate getting a vehicle from http://localhost:9090/api/v1/vehicles/{vehicle_id}, e.g. http://localhost:9090/api/v1/vehicles/1
5. Check again if you still can access other user's vehicle data

#### API2:2023 Broken Authentication

First we try to forge a JWTs using several well known attack types (using the playbook mode of the JWT_Tool):

1. Start the `insecure-api` application
2. Login using http://localhost:9090/api/v1/users/login
   and specify the credentials `bruce.wayne@example.com / wayne` to log in and grab the returned JWT
3. Now try this JWT you grabbed to find any vulnerabilities at http://localhost:9090/api/v1/partner
   ```shell
   export JWT=<the JWT you grabbed from step 2>
   ./jwt_tool.py -t http://localhost:9090/api/v1/partner -rh "Authorization: Bearer $JWT" -cv "Here is your data for partner bruce.wayne@example.com (Bruce Wayne)" -M pb
   ```

After performing the playbook you see which attacks seemed to be successful.
Then you might try specific one separately, like the exploit specifying the `none` signature algorithm as `alg:none`:

   ```shell
   export JWT=<the JWT you grabbed from step 2 above>
   ./jwt_tool.py -t http://localhost:9090/api/v1/partner -rh "Authorization: Bearer $JWT" -cv "Here is your data for partner bruce.wayne@example.com (Bruce Wayne)" -X a
   ```

In our case this is successful:

![JWT_Tool](images/jwt_tool_attack.png)

Finally try to brute force the JWT to find out the secret for signing a JWT using the symmetric HMAC signature algorithm
 `jwt_tool.py $JWT -C -d jwt.secrets.list`

The secrets list was just grabbed from https://github.com/wallarm/jwt-secrets.

#### API3:2023 Broken Object Property Level Authorization

Try to get some sensitive data exposed.

1. Login using http://localhost:9090/api/v1/users/login
   and specify the credentials `bruce.wayne@example.com / wayne` to log in
2. Try to enumerate getting a vehicle from http://localhost:9090//api/v1/community

#### API4:2023 Unrestricted Resource Consumption

Try a Denial of Service attack.

1. Login using http://localhost:9090/api/v1/users/login
   and specify the creds `bruce.wayne@example.com / wayne` to log in
2. Try to flood http://localhost:9090//api/v1/community with requests

For this use the rate limit client with the JWT from login or in step 2 with JWT from Spring Authorization Server.

#### API5:2023 Broken Function Level Authorization

Further demos and hacks are being added soon...stay tuned!

#### API6:2023 Unrestricted Access to Sensitive Business Flows

Further demos and hacks are being added soon...stay tuned!

#### API7:2023 Server Side Request Forgery

Further demos and hacks are being added soon...stay tuned!

#### API8:2023 Security Misconfiguration

Further demos and hacks are being added soon...stay tuned!

#### API9:2023 Improper Inventory Management

Further demos and hacks are being added soon...stay tuned!

#### API10:2023 Unsafe Consumption of APIs

Further demos and hacks are being added soon...stay tuned!
