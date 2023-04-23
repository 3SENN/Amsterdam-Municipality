package nl.hva.amsserver;


import nl.hva.amsserver.models.counting.*;
import nl.hva.amsserver.models.journal.JournalCard;
import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.models.quiz.QuizResult;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.counting.CountingLabelRepositoryJPA;
import nl.hva.amsserver.repositories.counting.CountingRepositoryJPA;
import nl.hva.amsserver.repositories.journals.JournalRepositoryJPA;
import nl.hva.amsserver.repositories.quiz.QuizRepositoryJPA;
import nl.hva.amsserver.repositories.quiz.QuizResultRepositoryJPA;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class DataInserter implements CommandLineRunner {
    @Autowired
    private UserRepositoryJPA userRepositoryJPA;
    @Autowired
    private QuizRepositoryJPA quizRepositoryJPA;
    @Autowired
    private QuizResultRepositoryJPA quizResultRepositoryJPA;
    @Autowired
    private CountingRepositoryJPA countingRepositoryJPA;
    @Autowired
    private CountingLabelRepositoryJPA countingLabelRepositoryJPA;
    @Autowired
    private JournalRepositoryJPA journalRepositoryJPA;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    List<User> users = new ArrayList<>();
    List<Quiz> quizzes = new ArrayList<>();
    List<QuizResult> quizResults = new ArrayList<>();
    List<Counting> countingList = new ArrayList<>();
    List<CountingLabels> countingLabelsList = new ArrayList<>();

    List<JournalCard> journalCardList = new ArrayList<>();

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("Running CommandLine Startup");
        try {
            this.createUsers();
            this.createCountingData();
            this.createQuizzes();
            this.createQuizResults();
            this.createJournalCards();
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private void createAdmin() {
        try {
            Optional<User> user = this.userRepositoryJPA.findByEmail("john@gmail.com");

            if (user.isPresent()) return;

            User admin = new User(
                    "John",
                    "Doe",
                    "john@gmail.com",
                    "https://ui-avatars.com/api/?name=John+Doe",
                    "$2a$10$m8WpLH0awDPTjWr0vfE/leMYFFJKqFoLLfYKxDWgcSPUO3oAJeNIa",
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now());

            this.userRepositoryJPA.save(admin);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private void createUsers() {
        try {
            User johnDoe = new User("John", "Doe", "john@gmail.com", "https://ui-avatars.com/api/?name=John+Doe", "$2a$10$m8WpLH0awDPTjWr0vfE/leMYFFJKqFoLLfYKxDWgcSPUO3oAJeNIa", true, LocalDateTime.now(), LocalDateTime.now());
            users.add(johnDoe);
            User janeDoe = new User("Jane", "Doe", "jane@gmail.com", "https://ui-avatars.com/api/?name=Jane+Doe", "$2a$10$m8WpLH0awDPTjWr0vfE/leMYFFJKqFoLLfYKxDWgcSPUO3oAJeNIa", false, LocalDateTime.now(), LocalDateTime.now());
            users.add(janeDoe);
            User bobDoe = new User("Bob", "Doe", "bob@gmail.com", "https://ui-avatars.com/api/?name=Bob+Doe", "$2a$10$m8WpLH0awDPTjWr0vfE/leMYFFJKqFoLLfYKxDWgcSPUO3oAJeNIa", false, LocalDateTime.now(), LocalDateTime.now());
            users.add(bobDoe);
            User jakeDoe = new User("Jake", "Doe", "jake@gmail.com", "https://ui-avatars.com/api/?name=Jake+Doe", "$2a$10$m8WpLH0awDPTjWr0vfE/leMYFFJKqFoLLfYKxDWgcSPUO3oAJeNIa", false, LocalDateTime.now(), LocalDateTime.now());
            users.add(jakeDoe);
            User susanGaines = new User("Susan", "Gaines", "susan@gmail.com", "https://ui-avatars.com/api/?name=Susan+Gaines", "$2a$10$m8WpLH0awDPTjWr0vfE/leMYFFJKqFoLLfYKxDWgcSPUO3oAJeNIa", false, LocalDateTime.now(), LocalDateTime.now());
            users.add(susanGaines);
            User husseinWeeks = new User("Hussein", "Weeks", "hussein@gmail.com", "https://ui-avatars.com/api/?name=Hussein+Weeks", "$2a$10$m8WpLH0awDPTjWr0vfE/leMYFFJKqFoLLfYKxDWgcSPUO3oAJeNIa", false, LocalDateTime.now(), LocalDateTime.now());
            users.add(husseinWeeks);
            User johnSnow = new User("Johna", "Snow", "johnsnow@gmail.com", "https://ui-avatars.com/api/?name=Hussein+Weeks", "$2a$10$m8WpLH0awDPTjWr0vfE/leMYFFJKqFoLLfYKxDWgcSPUO3oAJeNIa", false, LocalDateTime.now(), LocalDateTime.now());
            users.add(johnSnow);

            this.userRepositoryJPA.saveAll(users);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private void createQuizzes() {

        try {
            Quiz quiz1 = new Quiz("Level 2 - Quiz 1", 10, "2", null, "WBHA05", 0, LocalDateTime.of(2022, 2, 2, 15, 0, 0), LocalDateTime.of(2002, 2, 2, 16, 0, 0), LocalDateTime.now(), LocalDateTime.now());
            quiz1.setCreatedBy(users.get(0));
            quizzes.add(quiz1);
            Quiz quiz2 = new Quiz("Level 2 - Quiz 2", 12, "2", null, "WBHA06", 0, LocalDateTime.of(2022, 8, 2, 15, 0, 0), LocalDateTime.of(2002, 2, 2, 16, 0, 0), LocalDateTime.now(), LocalDateTime.now());
            quiz2.setCreatedBy(users.get(0));
            quizzes.add(quiz2);
            Quiz quiz3 = new Quiz("Level 2 - Quiz 3", 14, "2", null, "WBHA07", 0, LocalDateTime.of(2022, 7, 2, 15, 0, 0), LocalDateTime.of(2002, 2, 2, 16, 0, 0), LocalDateTime.now(), LocalDateTime.now());
            quiz3.setCreatedBy(users.get(0));
            quizzes.add(quiz3);
            Quiz quiz4 = new Quiz("Level 2 - Quiz 4", 16, "2", null, "WBHA08", 0, LocalDateTime.of(2022, 6, 2, 15, 0, 0), LocalDateTime.of(2002, 2, 2, 16, 0, 0), LocalDateTime.now(), LocalDateTime.now());
            quizzes.add(quiz4);
            quiz4.setCreatedBy(users.get(0));

            this.quizzes = this.quizRepositoryJPA.saveAll(quizzes);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private void createQuizResults() {
        try {
            QuizResult quizResult1 = new QuizResult(users.get(0), quizzes.get(0), 1200);
            quizResults.add(quizResult1);
            QuizResult quizResult2 = new QuizResult(users.get(1), quizzes.get(0), 100);
            quizResults.add(quizResult2);
            QuizResult quizResult3 = new QuizResult(users.get(2), quizzes.get(0), 250);
            quizResults.add(quizResult3);
            // added for demo
            QuizResult quizResult5 = new QuizResult(users.get(3), quizzes.get(0), 550);
            quizResults.add(quizResult5);
            QuizResult quizResult6 = new QuizResult(users.get(4), quizzes.get(0), 850);
            quizResults.add(quizResult6);
            QuizResult quizResult7 = new QuizResult(users.get(5), quizzes.get(0), 150);
            quizResults.add(quizResult7);
            this.quizResultRepositoryJPA.saveAll(quizResults);
        } catch (Exception error) {
            error.printStackTrace();
        }

    }

    private void createCountingData() {
        try {
            CountingOV countingOV1 = new CountingOV(1, 0, 2, 1, 0, 3, 2, 1);
            CountingPublicSpaces countingPublicSpaces1 = new CountingPublicSpaces(0, 1, 2, 0, 2, 1, 2, 1);
            Counting counting1 = new Counting("Waterlooplein, Amsterdam, Netherlands", LocalDateTime.of(2022, 12, 14, 16, 0, 0), null, null, null, "Wibautstraat, Amsterdam, Netherlands", LocalDateTime.of(2022, 12, 14, 17, 0, 0), countingPublicSpaces1, countingOV1, 400);
            counting1.setUserId(users.get(0));
            CountingLabels countingLabel1 = new CountingLabels("Person with autism", 2, 1);
            counting1.associateLabel(countingLabel1);
            countingList.add(counting1);
            countingLabelsList.add(countingLabel1);

            CountingOV countingOV2 = new CountingOV(0, 1, 3, 1, 2, 0, 2, 1);
            CountingPublicSpaces countingPublicSpaces2 = new CountingPublicSpaces(1, 2, 1, 2, 2, 0, 2, 2);
            Counting counting2 = new Counting("Amsterdamstraat 4, Zoetermeer, Netherlands", LocalDateTime.of(2022, 11, 19, 15, 0, 0), null, null, null, "Burgemeester de Vlugtlaan, Amsterdam, Netherlands", LocalDateTime.of(2022, 11, 19, 19, 0, 0), countingPublicSpaces2, countingOV2, 400);
            counting2.setUserId(users.get(1));
            CountingLabels countingLabel2 = new CountingLabels("Crippled person", 0, 1);
            counting2.associateLabel(countingLabel2);
            countingList.add(counting2);
            countingLabelsList.add(countingLabel2);

            CountingOV countingOV3 = new CountingOV(2, 1, 4, 1, 0, 3, 2, 0);
            CountingPublicSpaces countingPublicSpaces3 = new CountingPublicSpaces(4, 3, 1, 0, 1, 0, 2, 3);
            Counting counting3 = new Counting("Rotterdam Airportplein, Rotterdam, Netherlands", LocalDateTime.of(2023, 1, 3, 12, 0, 0), null, null, null, "Burgemeester Hogguerstraat, Amsterdam, Netherlands", LocalDateTime.of(2023, 1, 3, 14, 0, 0), countingPublicSpaces3, countingOV3, 400);
            counting3.setUserId(users.get(2));
            CountingLabels countingLabel3 = new CountingLabels("Deaf person", 1, 0);
            counting3.associateLabel(countingLabel3);
            countingList.add(counting3);
            countingLabelsList.add(countingLabel3);

            // Added for demo


            CountingOV countingOV5 = new CountingOV(1, 4, 4, 7, 0, 9, 2, 0);
            CountingPublicSpaces countingPublicSpaces5 = new CountingPublicSpaces(2, 1, 0, 9, 9, 2, 2, 7);
            Counting counting5 = new Counting("Sloterdijk, Amsterdam, Netherlands", LocalDateTime.of(2022, 11, 3, 12, 0, 0), null, null, null, "Burgemeester Hogguerstraat, Amsterdam, Netherlands", LocalDateTime.of(2023, 1, 3, 14, 0, 0), countingPublicSpaces3, countingOV3, 400);
            counting5.setUserId(users.get(3));
            countingList.add(counting5);

            CountingOV countingOV6 = new CountingOV(11, 0, 0, 0, 0, 9, 2, 0);
            CountingPublicSpaces countingPublicSpaces6 = new CountingPublicSpaces(2, 1, 0, 9, 9, 2, 2, 7);
            Counting counting6 = new Counting("Westpoort, Amsterdam, Netherlands", LocalDateTime.of(2022, 11, 5, 12, 0, 0), null, null, null, "Burgemeester Hogguerstraat, Amsterdam, Netherlands", LocalDateTime.of(2023, 1, 3, 14, 0, 0), countingPublicSpaces3, countingOV3, 400);
            counting6.setUserId(users.get(4));
            countingList.add(counting6);

            CountingOV countingOV7 = new CountingOV(3, 0, 0, 0, 0, 2, 2, 0);
            CountingPublicSpaces countingPublicSpaces7 = new CountingPublicSpaces(2, 0, 0, 4, 4, 2, 2, 7);
            Counting counting7 = new Counting("Wibauthuis, Amsterdam, Netherlands", LocalDateTime.of(2022, 11, 5, 12, 0, 0), null, null, null, "Burgemeester Hogguerstraat, Amsterdam, Netherlands", LocalDateTime.of(2023, 1, 3, 14, 0, 0), countingPublicSpaces3, countingOV3, 400);
            counting7.setUserId(users.get(5));
            countingList.add(counting7);

            CountingOV countingOV8 = new CountingOV(3, 0, 0, 0, 0, 2, 2, 0);
            CountingPublicSpaces countingPublicSpaces8 = new CountingPublicSpaces(2, 11, 0, 4, 4, 2, 2, 7);
            Counting counting8 = new Counting("Wibauddthuis, Amsterdam, Netherlands", LocalDateTime.of(2022, 11, 5, 12, 0, 0), null, null, null, "Burgemeester Hogguerstraat, Amsterdam, Netherlands", LocalDateTime.of(2023, 1, 3, 14, 0, 0), countingPublicSpaces3, countingOV3, 400);
            counting8.setUserId(users.get(6));
            countingList.add(counting8);

            this.countingLabelRepositoryJPA.saveAll(countingLabelsList);
            this.countingRepositoryJPA.saveAll(countingList);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void createJournalCards() {
        try {
            JournalCard journalCard1 = new JournalCard(1, "Level 1", "Saturday 14 January 2023", "Nice game");
            journalCard1.setUser(users.get(1));
            journalCardList.add(journalCard1);

            JournalCard journalCard2 = new JournalCard(2, "Level 2", "Sunday 15 January 2023", "Nice game!!!");
            journalCard2.setUser(users.get(1));
            journalCardList.add(journalCard2);

            JournalCard journalCard3 = new JournalCard(3, "Level 3", "Sunday 8 January 2023", "Ahhhhhh");
            journalCard3.setUser(users.get(1));
            journalCardList.add(journalCard3);

            JournalCard journalCard4 = new JournalCard(4, "Level 4", "Monday 16 January 2023", "Nice idea!");
            journalCard4.setUser(users.get(1));
            journalCardList.add(journalCard4);

            JournalCard journalCard5 = new JournalCard(5, "Counting game", "Monday 16 January 2023", "Great game!");
            journalCard5.setUser(users.get(1));
            journalCardList.add(journalCard5);

            JournalCard journalCard6 = new JournalCard(6, "Quizzzz", "Monday 16 January 2023", "Like kahoot");
            journalCard6.setUser(users.get(1));
            journalCardList.add(journalCard6);

            JournalCard journalCard7 = new JournalCard(7, "Level 5", "Tuesday 17 January 2023", "Whatttttt");
            journalCard7.setUser(users.get(1));
            journalCardList.add(journalCard7);

            JournalCard journalCard8 = new JournalCard(8, "Quiz", "Tuesday 17 January 2023", "Very nice quiz!");
            journalCard8.setUser(users.get(1));
            journalCardList.add(journalCard8);
            JournalCard journalCard9 = new JournalCard(9, "Level 5", "Monday 16 January 2023", "Can't play it yet");
            journalCard9.setUser(users.get(1));
            journalCardList.add(journalCard9);
            JournalCard journalCard10 = new JournalCard(10, "Level 5", "Wednesday 18 January 2023", "I want to play it");
            journalCard10.setUser(users.get(1));
            journalCardList.add(journalCard10);

            this.journalCardList = this.journalRepositoryJPA.saveAll(journalCardList);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public List<JournalCard> getJournalCardList() {
        return journalCardList;
    }
}


