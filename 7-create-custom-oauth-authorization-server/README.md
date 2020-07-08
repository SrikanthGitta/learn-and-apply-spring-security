#Create a Custom Authorization Server using OAuth2

#### Steps to develop a OAuth2 server (Triangle Call)
* Add a OAuth Server Dependency (Cloud OAuth2 from Spring Cloud Security)
```xml
<properties>
	<spring-cloud.version>Hoxton.SR3</spring-cloud.version>
</properties>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
</dependency>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
* Create a `@Configuration` class to `@EnableAuthorizationServer`. This annotation will register all the OAuth server
related endpoints automatically. How? Because, it uses `AuthorizationServerEndpointsConfiguration` and `AuthorizationServerSecurityConfiguration`.  
`AuthorizationServerEndpointsConfiguration` - contains lot of `@Bean` will enable respective endpoints.
`AuthorizationServerSecurityConfiguration` - responsible for to secure those endpoints not to access without provide access.
Refer `AuthorizationServerConfiguration` class.

```java
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfiguration
```

* In order to customize your own Authorization server, You have to extends your `AuthorizationServerConfiguration` from 
`AuthorizationServerConfigurerAdapter`

```java
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
```

* Override the `configure(ClientDetailsServiceConfigurer clients)` method implementation to configure for your clients in
`AuthorizationServerConfiguration`. You could also use, `clients.jdbc().dataSource()` instead `clients.inMemory()`.

```java
@Override
public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
            //Client-1
            .withClient("way2learnappclientid") //username
            .authorizedGrantTypes("password", "authorization_code")
            .secret(encoder().encode("secret")) //password
            .scopes("user_info", "read", "write")
            .redirectUris("https://localhost:8443/myapp/login/oauth2/code/way2learnappclient")
            .autoApprove(false)
            
            .and()
            //Client-2
            .withClient("microclient")
            .authorizedGrantTypes("password", "authorization_code", "client_credentials")
            .secret(encoder().encode("secret"))
            .scopes("user_info")
            .redirectUris("https://localhost:8443/myapp/login/oauth2/code/way2learnappclient")
            .autoApprove(false)
    ;
}
```

* With this current status of the code run the app, create a POST request in PostMan with the following steps.
    * use request url as `localhost:8081/auth/oauth/token`
        * add Authorization type as Basic and give `username` as `microclient` and `password` as `secret`
        * with the above two steps when you hit request, you will get an error response stating that `invalid request and missing grant_type`
        * How to fix this?
            - you have to add `grant_type = client_credentials` either by using `x-www-form-urlencoded` or `query param`.
            - after this step, now when you hit the request you will get an authentication token.

* How to configure that my OAuth Server to support `Password Grant Type` or `Resource Owner Password Grant Type`?
    * You have to create a `@Configuration` class which extends `WebSecurityConfigurerAdapter` and override `configure(AuthenticationManagerBuilder auth)` method.
    * You have to create a `@Bean` of type `AuthenticationManager`
    * After this step run your application
    * Develop a POST MAN request
        * use request url as `localhost:8081/auth/oauth/token`
            * add Authorization type as Basic and give `username` as `way2learnappclientid` and `password` as `secret`
            * In the request body by using `x-www-form-urlencoded`
                * you have to add `grant_type = password`
                * you have to add `username = karthi` and `password = secret`
            * after this step, now when you hit the request you will get an error. `unsupported_grant_type, Unsupported Grant Type Password`. 
    * How to fix this error? Refer //Code Block - 2. You have to add this code in `AuthorizationServerConfiguration` class.
    * After added a //Code Block - 2, hit start the application and hit the same POST MAN request, this time you will get a token. :-)
    
```java
//Code Block 1
@Configuration
@Order(1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("karthi").password(passwordEncoder.encode("secret"))
                .roles("USER");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
```

```java
//Code Block - 2 in AuthorizationServerConfiguration
@Autowired
private AuthenticationManager authenticationManager;

@Override
public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager);
}
```

* How to verify the token details?
    * Once you received a token from your Authentication Server, you can call `check_token` endpoint.
    * POST MAN
        * GET Call to `localhost:8081/auth/oauth/check_token?token=<your-token-value`
        * In this case, you will get an error response as `401 | Unauthorized`
    * Why? Because, by default `check_token` endpoint is secure. You have to supply authentication information.
    * How to fix this? In your `AuthorizationServerConfiguration` override another method `configure(AuthorizationServerSecurityConfigurer oauthServer)`
    * After the above fix, now when you generate a token and hit check_token endpoint, you will get a JSON response which contains
      information about the configured user and his/her authorities.
    * However, the below fix `permitAll` is not a good practice. Beacuse anyone who has token can access this endpoint to see the details of the user.
    * To avoid such issue, we have to configure it as `oauthServer.checkTokenAccess("isAuthenticated()")`. MOST Secure way!!
    * POST MAN
        * GET Call to `localhost:8081/auth/oauth/check_token?token=<your-token-value`
        * add Authorization type as Basic and give `username` as `way2learnappclientid` and `password` as `secret`
        * execute the request, you will get a success response
```java
@Override
public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer.checkTokenAccess("permitAll");
}
```

#### Steps to Customize OAuth2 Server to generate a JWT Token
To generate a JWT token we need a JKS file (KeyStore).
```java
keytool -genkey -alias tomcat -storetype jks -keyalg RSA -keysize 2048 -keystore mykeystore.jks
```
* Once you generate a KeyStore file, place it inside src/main/resources
* In your `AuthorizationServerConfiguration` class, you have to create a two beans and `configure(AuthorizationServerEndpointsConfigurer endpoints)` as shown below
```java
@Bean
public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
}

@Bean
public JwtAccessTokenConverter accessTokenConverter() {
    final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    //converter.setSigningKey("123");
    //Notes: Here KeyStoreFactory uses my classpath KeyStore file JKS and using "password" to access the
    //Alias Keypair "tomcat"
    final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
            new ClassPathResource("mykeystore.jks"), "password".toCharArray());
    converter.setKeyPair(keyStoreKeyFactory.getKeyPair("tomcat"));
    return converter;
}

//Modify this method to configure the tokenStore and accessTokenConverter
@Override
public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager)
    
    // Enable this line for JWT
    .tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter());
    ;
}
```
* Start the application
* Hit the POSTMAN POST request to get a token by using previous request which we discussed earlier. Now, your access token 
will be in the form of JWT format.

#### Customize Auth Server to support Authorization Code Grant

* We have a `UserInfoController` which contains few REST endpoints. I want to configure those endpoints to be secured.
* For this, we have to create a `@Configuration` class `MyResourceServerConfiguration` which enables `@EnableResourceServer`
* An Important step is to configure CLIENT application to use this Authorization Server to validate the username and password to get the token
we have to do below configuration in client application `application.properties or yml`
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: 694424912843-55b1bl200s1tl8tgr7k07rji2ptd1772.apps.googleusercontent.com
            client-secret: g6yJEFdW8BPwTYKqivcLJ_QU
            scope: openid,profile,email
            client-name: SivaGoogle
          way2learnclient:
            client-id: way2learnappclientid
            client-secret: secret
            client-name: Way2learn
            scope: user_info,read,write
            redirect-uri: https://localhost:8443/myapp/login/oauth2/code/way2learnappclient
            client-authentication-method: basic
            authorization-grant-type: authorization_code
            provider: way2learn-authserver-provider
        provider:
            way2learn-authserver-provider:
              token-uri: http://localhost:8081/auth/oauth/token
              authorization-uri: http://localhost:8081/auth/oauth/authorize
              user-info-uri: http://localhost:8081/auth/userinfo
              user-name-attribute: username
```



    