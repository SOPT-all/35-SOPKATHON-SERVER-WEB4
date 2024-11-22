FROM amd64/amazoncorretto:17
WORKDIR /app
COPY ./build/libs/sopkathon-0.0.1-SNAPSHOT.jar /app/ANDSOPTSOPKATHON-SOPT.jar
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=main", "ANDSOPTSOPKATHON-SOPT.jar"]
