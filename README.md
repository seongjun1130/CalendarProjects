#     CalendarProjects 📝
이 CalendarProjects는 Spring Boot, JDBC, MySQL을 사용하여 개발된 일정 및 유저 관리 시스템입니다. 사용자는 일정을 생성, 수정, 삭제할 수 있으며, 유저 관리 기능도 포함되어 있습니다. RESTful API와 MySQL을 통한 데이터베이스 연동이 특징입니다.

해당 프로그램은 하단 구현 가이드라인에 초점을 맞추어 구현하였습니다.

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fseongjun1130%2FCalendarProjects&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://github.com/seongjun1130)

## 목차
- [개요](#개요)
- [기능 구현 가이드라인](#기능-구현-가이드라인)
- [API 명세서](#API-명세서)
- [DateBase ERD](#DataBase-ERD)
- [프로그램 플로우차트](#프로그램-플로우차트)
- [프로그램 동작화면](#프로그램-동작-화면)
- [아키텍쳐](#architecture)

## 개요
- 프로젝트 이름 : CalendarProjects
- 프로젝트 지속기간 : 2024.09.26 ~ 2024.10.04
- 개발언어 : JAVA, SpringBoot, MySQL, GRADLE
- 멤버 : 조성준

## 기능 구현 가이드라인
<details>
<summary>접기/펼치기</summary>
  
![image](https://github.com/user-attachments/assets/f9c36edf-a626-401f-8420-37fb78558f1e)
![image](https://github.com/user-attachments/assets/3f39e504-10dd-4da1-9a94-b78965facc98)
![image](https://github.com/user-attachments/assets/df350530-5c61-40a0-835c-443958723cd0)


</details>

## API 명세서
- 명세서 WebPage
  
[API 명세서 Link](https://documenter.getpostman.com/view/38557957/2sAXxLBZMC#intro)

## DataBase ERD

![image](https://github.com/user-attachments/assets/5e0a5a5a-b758-4460-b4c6-968e4cbe45f6)


## 프로그램 플로우차트
- ### Create Event Flow
![image](https://github.com/user-attachments/assets/aefd0871-a281-4dd4-b217-37f407e6b535)

- ### Read Event Flow
![image-1](https://github.com/user-attachments/assets/2944097b-b2a6-4178-9935-d2a80fb3f348)

- ### Update Event Flow
![image](https://github.com/user-attachments/assets/7522447b-5c3d-4940-857c-13c210ffb52a)

- ### Delete Event Flow
![image](https://github.com/user-attachments/assets/a86d42d9-b6b4-4d90-9dcd-d605daef4af9)


## 주요기능
- 일정 생성/수정/삭제: 사용자는 자신의 일정을 생성하고, 수정 및 삭제할 수 있습니다.
- 일정 조회: 특정 날짜 및 작성자를 필터링하여 일정을 조회할 수 있으며, 페이징 기능을 지원합니다.
- 유저 관리: 유저의 등록, 수정, 삭제 기능을 제공합니다.


## 프로그램 동작 화면
<details>
<summary>접기/펼치기</summary>


- 일정 생성
![image](https://github.com/user-attachments/assets/606388d2-cc26-4da8-ac34-26d1467ab9ee)
- 일정 조회 (페이징)
![image-1](https://github.com/user-attachments/assets/88e8f281-414e-4bcf-8462-3dad79e2eb27)
- 일정 수정
![image](https://github.com/user-attachments/assets/778d05e9-8ed2-4e0a-b26e-01b1172cdc0b)
- 일정 삭제
![image-1](https://github.com/user-attachments/assets/efd264b3-345d-4b26-8500-18969773489f)


</details>

## Stacks
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">

## Communication
- ### Main blog
https://velog.io/@lionjojo/posts
- ### project troubleshooting
https://velog.io/@lionjojo/Spring-Calendar-%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85

## Architecture
```
📦 
├─ .gitignore
├─ build.gradle
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
├─ settings.gradle
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ sparta
   │  │        └─ calendarprojects
   │  │           ├─ CalendarProjectsApplication.java
   │  │           ├─ controller : 유저, 일정 관련 API 컨트롤러 패키지
   │  │           │  ├─ EventController.java : 일정 관리 컨트롤러
   │  │           │  └─ UserController.java : 유저 관리 컨트롤러
   │  │           ├─ dto : 유저, 일정 관련 정보처리 DTO 패키지
   │  │           │  ├─ EventRequestDto.java : 일정관련 요청정보 DTO
   │  │           │  ├─ EventResponseDto.java : 일정관련 응답정보 DTO
   │  │           │  ├─ PageResponseDto.java : 일정 페이징 정보 DTO
   │  │           │  ├─ UserReponseDto.java : 유저관련 응답정보 DTO
   │  │           │  └─ UserRequestDto.java : 유저관련 요청정보 DTO
   │  │           ├─ entity : DB 조회용 Entity 패키지
   │  │           │  ├─ Event.java : 일정 관련 Entity
   │  │           │  └─ User.java : 유저 관련 Entity
   │  │           ├─ exception : 예외처리 패키지
   │  │           │  ├─ CustomErrorCode.java : 예외발생 에러 코드 ENUM 클래스
   │  │           │  ├─ CustomException.java : 예외 객체 생성용 상속 클래스
   │  │           │  ├─ CustomExceptionHandler.java : 예외 핸들링 클래스
   │  │           │  └─ ErrorResponse.java : 예외정보 DTO
   │  │           ├─ info : 서버안에서 운용하는 Info 객체
   │  │           │  └─ PageInfo.java : 페이징 정보 객체
   │  │           ├─ mapper : 페이징 기능 활용 mapper 패키지
   │  │           │  └─ EventMapper.java : Page 기능을 활용해 DB에 객체들을 맵핑해주는 Mapper
   │  │           ├─ repository : 유저, 일정 관련 DB 조회 레파지토리 패키지
   │  │           │  ├─ EventPageRepository.java : 페이징 기능 활용을 위한 인터페이스 구현체 레파지토리
   │  │           │  ├─ EventRepository.java : 일정 관련 DB 관리 레파지토리
   │  │           │  └─ UserRepository.java : 유저 관련 DB 관리 레파지토리
   │  │           └─ service : 유저, 일정 관련 서비스 로직 패키지
   │  │              ├─ EventService.java : 일정 관련 서비스로직
   │  │              └─ UserService.java : 유저 관련 서비스로직
   │  └─ resources
   │     └─ application.properties
   └─ test
      └─ java
         └─ com
            └─ sparta
               └─ calendarprojects
                  └─ CalendarProjectsApplicationTests.java
```
©generated by [Project Tree Generator](https://woochanleee.github.io/project-tree-generator)
