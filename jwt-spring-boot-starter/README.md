# jwt-spring-boot-starter

a spring-boot-starter to integrate JWT(com.auth0) which is easy to use 。

## How to use

add repository into your pom.xml

    <repositories>
        <repository>
            <id>chenhuanming-repo</id>
            <name>chenhuanming-repo</name>
            <url>https://raw.githubusercontent.com/zerouwar/my-maven-repo/master</url>
        </repository>
    </repositories>

add `jwt-spring-boot-starter` denpendency into your spring-boot project

    <dependency>
        <groupId>cn.chenhuanming</groupId>
        <artifactId>jwt-spring-boot-starter</artifactId>
        <version>0.0.2</version>
    </dependency>

run your spring boot Application,it already integrate JWT authorization

Now you can get token through your POST /login with username `user` and a generated password (by default is create by spring security),and access the protected API with token you got(set value of http header `Authorization` to `Bearer your-token`).

Token will be required to refresh(default short expiration is 15 minutes and expire is 7 days) when it expired.Your server will return 412 http status and a json carrying refresh token.

## Customization
If you want to change short expiration,set `utils.jwt.shortExp` into `application.yml`.More customizable setting in `cn.chenhuanming.utils.jwt.core.JWTProperties`.

If you donn't want to use default user created by spring security ,just register your `UserDetailsService` as spring `Bean`,`jwt-spring-boot-starter` will automatically discover it.

Some customizable component can be registered as `Bean` into spring IOC and `jwt-spring-boot-starter` will automatically discover them and apply.

- `TokenUtils`
- `JWTSuccessHandler`
- `TokenAuthorizationFilter`
- `JWTAuthorizationManager`
- `TokenExpiredHandler`
- `TokenRefreshHandler`
- `TokenSuccessHandler`
- `TokenInvalidHandler`

If you need have your `WebSecurityConfigurerAdapter`,please inherit `JWTSecurityAutoConfiguration` which sets some jwt config.Sub class of `JWTSecurityAutoConfiguration` is not allow overwrite `protected final void configure(HttpSecurity http) throws Exception ` although `JWTSecurityAutoConfiguration` inherits `WebSecurityConfigurerAdapter`.Thus,you can overwrite `protected void configureHttpSecurity(HttpSecurity http) throws Exception` from `JWTSecurityAutoConfiguration`.There is not any different between them,you can see the source.
