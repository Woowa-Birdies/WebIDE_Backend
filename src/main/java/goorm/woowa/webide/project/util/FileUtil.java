package goorm.woowa.webide.project.util;

import goorm.woowa.webide.project.domain.ProjectLanguage;

import java.io.*;
import java.nio.file.*;

public class FileUtil {

    public static String makeFileFromCode(String str, ProjectLanguage language) {
        BufferedWriter fw;
        String fileType = language.name().equals("PYTHON") ? ".py" : language.name().equals("JAVA") ? ".java" : ".cpp";

        {
            try {
                fw = new BufferedWriter(new FileWriter("test" + fileType, true));
                fw.write(str);
                fw.flush();

                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return "test" + fileType;
    }

    public static int deleteFile(String fileName) {
        Path filePath = Paths.get(fileName);
        Path directoryPath = Paths.get("/file");
        try {
            // 파일 삭제
            Files.delete(filePath);
            // 디렉토리 삭제
//            Files.delete(directoryPath);
        } catch (NoSuchFileException e) {
            System.out.println("삭제하려는 파일/디렉토리가 없습니다");
            return -1;
        } catch (DirectoryNotEmptyException e) {
            System.out.println("디렉토리가 비어있지 않습니다");
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
