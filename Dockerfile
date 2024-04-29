FROM openjdk:21-ea-24-oracle

WORKDIR /app
COPY target/s3_d-0.0.1-SNAPSHOT.jar app.jar
COPY Wallet_T8CBNE3Y507ZUV36 /app/oracle_wallet
EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]