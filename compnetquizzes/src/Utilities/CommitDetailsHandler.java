package Utilities;

import java.io.*;

public class CommitDetailsHandler {
    private static String readCommitId() {
        try(BufferedReader br =
                new BufferedReader(
                    new InputStreamReader(
                            Runtime.getRuntime().exec("git show").getInputStream()
                    )
                )
        ) {
            if (br.ready()) {
                return br.readLine();
            }
        } catch (IOException e) {
            LogHandler.Instance.LogError(e);
        }

        return null;
    }

    private static void saveCommitId(String commitId) {
        StringBuilder sb = new StringBuilder("package Utilities;public class CommitDetails{public String CommitId = \"");
        sb.append(commitId);
        sb.append("\";}");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(GlobalConstants.PATHTOPROJ + "/Utilities/CommitDetails.java"))) {
            bw.write(sb.toString());
        } catch (IOException e) {
            LogHandler.Instance.LogError(e);
        }
    }

    public static void SetCommitId() {
        saveCommitId(readCommitId());
    }
}
