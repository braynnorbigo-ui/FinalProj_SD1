# FinalProj_SD1
├── .gitignore
├── pom.xml
└── src/
├── main/
│    ├── java/com/catc/bookwebcatc/
│    │    ├── BookWebCatcApplication.java
│    │    ├── config/
│    │    │    └── SecurityConfig.java
│    │    ├── controller/
│    │    │    ├── PortalPageController.java
│    │    │    └── LibraryApiController.java
│    │    ├── dto/
│    │    │    ├── BookRequest.java
│    │    │    └── BookResponse.java
│    │    ├── entity/
│    │    │    ├── AppUser.java
│    │    │    └── LibraryBook.java
│    │    ├── repository/
│    │    │    ├── AppUserRepository.java
│    │    │    └── LibraryBookRepository.java
│    │    └── service/
│    │         ├── DbUserDetailsService.java
│    │         └── LibraryService.java
│    └── resources/
│         ├── application.properties
│         └── static/
│              ├── BookWebCATC.html
│              ├── BookWebCATC_LoginView.html
│              ├── BookWebCATC_Studentdashboard.html
│              └── BookWebCATC_Librariandashboard.html