# Individual classes built per team member

---

### Harmohat Khangura

<details>
<summary>Back-end</summary>

- Controller
    - EventController.java
        - Path: `src/main/java/nl/hva/amsserver/controller/EventController.java`
    - QuizController.java
        - Path: `src/main/java/nl/hva/amsserver/controller/QuizController.java`
    - CountingController.java -> Worked on certain methods
        - Methods:
          - `getTotalAmountOfCountingLevels()`
          - `getScores()`
          - `deleteById()`
        - Path: `src/main/java/nl/hva/amsserver/controller/CountingController.java`
    - QuizResultController.java -> Worked on certain methods
        - Methods:
          - `getAllResults()`
          - `getTotalAmountOfQuizResults()`
          - `getScores()`
          - `deleteById()`
          - `findByUserIdAndQuizId()`
        - Path: `src/main/java/nl/hva/amsserver/controller/QuizResultController.java` 
    - UsersController.java -> Worked on certain methods
        - Methods:
          - `getAllUsers()`
          - `getTotalAmountOfUsers`
        - Path: `src/main/java/nl/hva/amsserver/controller/UsersController.java`
- Exceptions
    - ConflictException.java
        - Path: `src/main/java/nl/hva/amsserver/exceptions/ConflictException.java`
    - InternalServerError.java
        - Path: `src/main/java/nl/hva/amsserver/exceptions/InternalServerError.java`
- Models
    - event/Event.java
        - Path: `src/main/java/nl/hva/amsserver/models/event/Event.java`
    - helper/EventProcessor.java
        - Path: `src/main/java/nl/hva/amsserver/models/helper/EventProcessor.java`
    - helper/Message.java
        - Path: `src/main/java/nl/hva/amsserver/models/helper/Message.java`
    - quiz/Question.java
        - Path: `src/main/java/nl/hva/amsserver/models/quiz/Question.java`
    - quiz/QuestionOption.java
        - Path: `src/main/java/nl/hva/amsserver/models/quiz/QuestionOption.java`
    - quiz/Quiz.java
        - Path: `src/main/java/nl/hva/amsserver/models/quiz/Quiz.java`
    - user/User.java
        - Path: `src/main/java/nl/hva/amsserver/models/user/User.java`
- Repositories
    - event/EventRepositoryJPA.java
        - Path: `src/main/java/nl/hva/amsserver/repositories/event/EventRepositoryJPA.java`
    - quiz/QuestionOptionRepositoryJPA.java
        - Path: `src/main/java/nl/hva/amsserver/repositories/quiz/QuestionOptionRepositoryJPA.java`
    - quiz/QuestionRepositoryJPA.java
        - Path: `src/main/java/nl/hva/amsserver/repositories/quiz/QuestionRepositoryJPA.java`
    - quiz/QuizRepositoryJPA.java
        - Path: `src/main/java/nl/hva/amsserver/repositories/quiz/QuizRepositoryJPA.java`
    - CountingLabelRepositoryJPA.java -> Worked on certain methods
       - Methods:
         - `deleteByCounting()`
       - Path: `src/main/java/nl/hva/amsserver/repositories/counting/CountingLabelRepositoryJPA.java`
    - CountingRepositoryJPA.java -> Worked on certain methods
      - Methods:
        - `totalScore()`
        - `averageScore()`
      - Path: `src/main/java/nl/hva/amsserver/repositories/counting/CountingRepositoryJPA.java`
    - QuizResultRepositoryJPA.java -> Worked on certain methods
      - Methods:
        - `findByQuizId()`
        - `findByUserIdAndQuizId()`
        - `totalScore()`
        - `averageScore()`
      - Path: `src/main/java/nl/hva/amsserver/repositories/quiz/QuizResultRepositoryJPA.java`
- Serializers
    - QuestionSerializer.java
        - Path: `src/main/java/nl/hva/amsserver/serializers/QuestionSerializer.java`
    - QuizWithUserSerializer.java
        - Path: `src/main/java/nl/hva/amsserver/serializers/QuizWithUserSerializer.java`
- Service
    - QuizService.java
        - Path: `src/main/java/nl/hva/amsserver/service/QuizService.java`
- Tests
  - QuizControllerTest.java
    - Path: `src/test/java/nl/hva/amsserver/controller/QuizControllerTest.java`
  - QuizRepositoryJPATest.java
    - Path: `src/test/java/nl/hva/amsserver/repositories/QuizRepositoryJPATest.java`
</details>

<details>
<summary>Front-end</summary>

- Components
  - Carousel.vue
    - Path: `fe/src/components/home/Carousel.vue`
  - GameInformation.vue
    - Path: `fe/src/components/home/GameInformation.vue`
  - Homepage.vue
    - Path: `fe/src/components/home/Homepage.vue`
  - NavBar.vue
    - Path: `fe/src/components/includes/NavBar.vue`
  - Footer.vue
    - Path: `fe/src/components/includes/Footer.vue`
  - SubMenuAdmin.vue
    - Path: `fe/src/components/includes/submenu/SubMenuAdmin.vue`
  - SubMenuDefault.vue
    - Path: `fe/src/components/includes/submenu/SubMenuDefault.vue`
  - SubMenuUser.vue
    - Path: `fe/src/components/includes/submenu/SubMenuUser.vue`
  - dangerAlert.vue
    - Path: `fe/src/components/includes/alerts/dangerAlert.vue`
  - infoAlert.vue
    - Path: `fe/src/components/includes/alerts/infoAlert.vue`
  - successAlert.vue
    - Path: `fe/src/components/includes/alerts/successAlert.vue`
  - levels/index.vue
    - Path: `fe/src/components/Admin/levels/index.vue`
  - levels/level1/view.vue
    - Path: `fe/src/components/Admin/levels/level1/view.vue`
  - levels/level2/create.vue
    - Path: `fe/src/components/Admin/levels/level2/create.vue`
  - levels/level2/delete.vue
    - Path: `fe/src/components/Admin/levels/level2/delete.vue`
  - levels/level2/show.vue
    - Path: `fe/src/components/Admin/levels/level2/show.vue`
  - levels/level2/update.vue
    - Path: `fe/src/components/Admin/levels/level2/update.vue`
  - questions/index.vue
    - Path: `fe/src/components/Admin/questions/index.vue`
  - questions/create.vue
      - Path: `fe/src/components/Admin/questions/create.vue`
  - questions/delete.vue
      - Path: `fe/src/components/Admin/questions/delete.vue`
  - questions/show.vue
      - Path: `fe/src/components/Admin/questions/show.vue`
  - questions/update.vue
      - Path: `fe/src/components/Admin/questions/update.vue`
  - userLevels/index.vue
    - Path: `fe/src/components/Admin/userLevels/index.vue`
  - userLevels/level1/delete.vue
    - Path: `fe/src/components/Admin/userLevels/level1/delete.vue`
  - userLevels/level1/show.vue
    - Path: `fe/src/components/Admin/userLevels/level1/show.vue`
  - userLevels/level2/delete.vue
    - Path: `fe/src/components/Admin/userLevels/level2/delete.vue`
  - userLevels/level2/show.vue
    - Path: `fe/src/components/Admin/userLevels/level2/show.vue`
  - users/index.vue
      - Path: `fe/src/components/Admin/users/index.vue`
  - users/delete.vue
      - Path: `fe/src/components/Admin/users/delete.vue`
  - users/show.vue
      - Path: `fe/src/components/Admin/users/show.vue`
  - users/create.vue
      - Path: `fe/src/components/Admin/users/create.vue`
  - users/update.vue
      - Path: `fe/src/components/Admin/users/update.vue`
- Services
  - eventService.js
    - Path: `fe/src/services/events/eventService.js`
  - quizService.js
    - Path: `fe/src/services/quiz/quizService.js`
  - usersService.js
    - Path: `fe/src/services/user/usersService.js`
  - quizResultService.js -> Worked on certain functions
    - Methods:
      - `findAllResults()`
      - `deleteById()`
      - `getTotalAmountOfQuizResults()`
      - `getScores()`
    - Path: `fe/src/services/quiz/quizResultService.js`
  - countingService.js -> Worked on certain functions
    - Methods:
      - `getScores()`
      - `getTotalAmountOfCountingResults()`
      - `deleteById()`
    - Path: `fe/src/services/countingService.js`
- assets
  - axiosRequest.js
    - Path: `fe/src/assets/js/utils/axiosRequest.js`
  - cryptoHelper.js
    - Path: `fe/src/assets/js/utils/cryptoHelper.js`
  - httpStatusCodes.js
    - Path: `fe/src/assets/js/utils/httpStatusCodes.js`
  - pagination.js
    - Path: `fe/src/assets/js/utils/pagination.js`
  - timeHelper.js
    - Path: `fe/src/assets/js/utils/timeHelper.js`
  - questionValidator.js
    - Path: `fe/src/assets/js/validation/questionValidator.js`
  - quizValidator.js
    - Path: `fe/src/assets/js/validation/quizValidator.js`
- tests
  - AdminUsersOverview.spec.js
    - Path: `fe/tests/unit/components/AdminUsersOverview.spec.js`
  - quizInMemoryService.spec.js
    - Path: `fe/tests/unit/services/quiz/quizInMemoryService.spec.js`
  - quizService.spec.js
    - Path: `fe/tests/unit/services/quiz/quizService.spec.js`
</details>


### Arunn Lingeswaran

<details>
<summary>Back-end</summary>

- Controller
  - UserController.java
    - Path: `src/main/java/nl/hva/amsserver/controller/UserController.java`
- Exceptions
  - ResourceNotFound.java
    - Path: `src/main/java/nl/hva/amsserver/exceptions/ResourceNotFound.java`
- Models
  - dto/ProfilePassword.java
    - Path: `src/main/java/nl/hva/amsserver/models/dto/ProfilePasswordDTO.java`
  - dto/JournalDTO.java
     - Path: `src/main/java/nl/hva/amsserver/models/dto/JournalDTO.java`
  - journal/JournalCard.java
    - Path: `src/main/java/nl/hva/amsserver/models/JournalCard.java`
- Repositories
  - users/impl/UserRepositoryMock.java
    - Path: `src/main/java/nl/hva/amsserver/repositories/users/impl/UserRepositoryMock.java`
    - Methods: `findByEmail(String email)`
  - users/UserRepository.java
    - Path: `src/main/java/nl/hva/amsserver/repositories/users/UserRepository.java`
  - users/UserRepositoryJPA.java
    - Path: `src/main/java/nl/hva/amsserver/repositories/users/UserRepositoryJPA.java`
  - levels/LevelsRepository.java
      - Path: `src/main/java/nl/hva/amsserver/repositories/levels/LevelsRepository.java`
  - levels/LevelRepositoryMock.java
    - Path: `src/main/java/nl/hva/amsserver/repositories/levels/LevelRepositoryMock.java`
  - quiz/QuizResultRepository.java
     - Path: `src/main/java/nl/hva/amsserver/repositories/quiz/QuizResultRepository.java`
     - Methods: `findByUserId(long id) & findByQuizId(Quiz quiz)`
- Serializers
  - UserSerializer.java
    - Path: `src/main/java/nl/hva/amsserver/serializers/UserSerializer.java`
  - QuizWithUserSerializer.java
    - Path: `src/main/java/nl/hva/amsserver/serializers/QuizWithUserSerializer.java`
- Tests
  - UserControllerTest.java
    - Path: `src/test/java/nl/hva/amsserver/controller/UserControllerTest.java`
  - UserRepositoryJPATest.java
    - Path: `src/test/java/nl/hva/amsserver/repositories/UserRepositoryJPATest.java`
</details>

<details>
<summary>Front-end</summary>

- Components
  - levels/quiz/Home.vue
    - Path: `fe/src/components/levels/quiz/Home.vue`
  - levels/quiz/Lobby.vue
    - Path: `fe/src/components/levels/quiz/Lobby.vue`
  - levels/quiz/Quiz.vue
    - Path: `fe/src/components/levels/quiz/Quiz.vue`
  - events/Events.vue
    - Path: `fe/src/components/events/Events.vue`
  - events/EventsCard.vue
    - Path: `fe/src/components/events/EventsCard.vue`
  - profiles/settings/Background.vue
    - Path: `fe/src/components/profiles/settings/Background.vue`
  - profiles/Profile.vue
    - Path: `fe/src/components/profiles/profile.vue`
  - Admin/dashboard/DashboardCard.vue
    - Path: `fe/src/components/Admin/dashboard/DashboardCard.vue`
  - Admin/dashboard/DashboardLevelCard.vue
     - Path: `fe/src/components/Admin/dashboard/DashboardLevelCard.vue`
- Services
  - eventService.js
    - Path: `fe/src/services/events/eventService.js`
    - Methods: `getAllEvents()``
  - usersService.js
    - Path: `fe/src/services/user/usersService.js`
    - Methods: `getUserByEmail(email) & updateProfile(email, user)`
  - levelsService.js
    - Path: `fe/src/services/levelsService.js`
- assets
  - errorMessages.js
    - Path: `fe/src/assets/js/utils/errorMessages.js`

</details>


### Senne Chin Tsoen Jien

<details>
<summary>Back-end</summary>

- Controller
  - QuizResultController.java
    - Path: `src/main/java/nl/hva/amsserver/controller/QuizResultController.java`
- Models
  - quiz/QuizResult.java
    - Path: `src/main/java/nl/hva/amsserver/models/quiz/QuizResult.java`
- Repositories
  - quiz/QuizResultRepositoryJPA.java
    - Path: `src/main/java/nl/hva/amsserver/repositories/quiz/QuizResultRepositoryJPA.java`
    - Methods: `findQuizHighscores`
- Service
  - service/QuizResultService.java
    - Path: `src/main/java/nl/hva/amsserver/service/QuizResultService.java`
    - Methods: `findAll, findById`
- Tests
  - QuizResultControllerTest.java
    - Path: `src/test/java/nl/hva/amsserver/controller/QuizResultControllerTest.java`
  - QuizResultRepositoryTest.java
    - Path: `src/test/java/nl/hva/amsserver/repositories/QuizResultRepositoryTest.java`
</details>

<details>
<summary>Front-end</summary>

- Components
  - leaderboard/Carousel.vue
    - Path: `fe/src/components/leaderboard/Carousel.vue`
  - leaderboard/CarouselPerLevel.vue
    - Path: `fe/src/components/leaderboard/CarouselPerLevel.vue`
  - leaderboard/Leaderboards.vue
    - Path: `fe/src/components/leaderboard/Leaderboards.vue`

- Services
  - quizResultService.js
    - Path: `fe/src/services/quiz/quizResultService.js`
    - Methods: ``findAll()``
  - quizResultInMemoryService.js
    - Path: `fe/src/services/quiz/quizResultInMemoryService.js`
    - Methods: `save(quizResult)`
  - countingService.js
    - Path: `fe/src/services/levelsService.js`
    - Methods: `getHighscore()`
- Tests
  - Leaderboard.spec.js
    - Path: `src/tests/unit/components/Leaderboard.spec.js`

</details>

### Ryan Koning
<details>

<summary>Back-end</summary>

- Controller
  - JournalsController.java
    - Path: `src/main/java/nl/hva/amsserver/controller/JournalsController.java`
- Models
  - JournalCard.java
    - Path: `src/main/java/nl/hva/amsserver/models/journal/JournalCard.java`
- Repositories
  - JournalRepositoryJPA.java
    - Path: `src/main/java/nl/hva/amsserver/repositories/journals/JournalRepositoryJPA.java`
- Tests
  - JournalsControllerTest.java
    - Path: `src/test/java/nl/hva/amsserver/controller/JournalsControllerTest.java`
  - JournalsRepositoryJPATest.java
    - Path: `src/test/java/nl/hva/amsserver/repositories/JournalsRepositoryJPATest.java`
</details>

<details>
<summary>Front-end</summary>

- Components
  - journal/CreateNew.vue
    - Path: `fe/src/components/journal/CreateNew.vue`
  - journal/deleteCard.vue
    - Path: `fe/src/components/journal/deleteCard.vue`
  - journal/EditNote.vue
    - Path: `fe/src/components/journal/EditNote.vue`
  - journal/Journal.vue
    - Path: `fe/src/components/journal/Journal.vue`
  - journal/JournalCard.vue
    - Path: `fe/src/components/journal/JournalCard.vue`
  - journal/ViewNote.vue
    - Path: `fe/src/components/journal/ViewNote.vue`

- Services
  - cardsService.js
    - Path: `fe/src/services/cardsService.js`
- Tests
  - Journal.spec.js
    - Path: `src/tests/unit/components/Journal`

</details>


