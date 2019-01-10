package Repository;

import Interfaces.IQuestionModel;
import Interfaces.IRepository;
import Models.QuestionModel;
import Utilities.LogHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Repository implements IRepository {
    private ArrayList<IQuestionModel> questions;
    private String filePath;

    public Repository(String filePath) {
        this.filePath = filePath;

        loadData();

        assert (questions.size() > 0);
    }

    private void loadData() {
        questions = new ArrayList<>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));

        while (true) {
                String statement = reader.readLine();
                if (statement == null || statement.length() == 0) {
                    break;
                }

                ArrayList<String> options = new ArrayList<>();
                for(int optionsCnt = Integer.parseInt(reader.readLine()); optionsCnt > 0; optionsCnt--) {
                    options.add(reader.readLine());
                }

                ArrayList<String> attachments = new ArrayList<>();
                for (int attachmentsCnt = Integer.parseInt(reader.readLine()); attachmentsCnt > 0; attachmentsCnt--) {
                    attachments.add(reader.readLine());
                }

                String answer = reader.readLine();
                int points = Integer.parseInt(reader.readLine());

                questions.add(
                        new QuestionModel(
                                statement,
                                options,
                                points,
                                attachments,
                                answer
                        )
                );
            }
        } catch (Exception e) {
            LogHandler.Instance.LogError(e);
        }
    }

    @Override
    public ArrayList<IQuestionModel> getQuestions() {
        return questions;
    }
}
