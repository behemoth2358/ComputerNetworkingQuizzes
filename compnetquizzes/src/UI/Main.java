package UI;

import Interfaces.IQuestionsManager;
import Interfaces.IRepository;
import Managers.QuestionsManager;
import UI.Controllers.StartAppController;
import Utilities.CommitDetails;
import Utilities.CommitDetailsHandler;
import Utilities.GlobalVars;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Repository.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CommitDetailsHandler.SetCommitId();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/StartAppView.fxml"));
        Parent root = loader.load();
        StartAppController controller = loader.getController();

        IRepository repository = new Repository(GlobalVars.PATHTOPROJ + "/Data/questions.data");
        IQuestionsManager questionsManager = new QuestionsManager(repository);

        controller.setQuestionsManager(questionsManager);

        primaryStage.setTitle("Computer Networks Quizzes - " + new CommitDetails().CommitId);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
