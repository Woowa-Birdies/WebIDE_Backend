package goorm.woowa.webide.project.util;

import goorm.woowa.webide.project.domain.ProjectLanguage;

import java.io.*;
import java.nio.file.*;

public class FileUtil {

    private static final String DIRECTORY_PATH = "../";

    public static String makeFileFromCode(String str, ProjectLanguage language) {
        BufferedWriter fw;
        File f;
        String fileType = language.name().equals("PYTHON") ? ".py" : language.name().equals("JAVA") ? ".java" : ".cpp";

        // todo: java, cpp 는 파일명이 대문자로 시작하고 클래스와 이름이 같아야함

        {
            try {
                f = new File("Test" + fileType);
                fw = new BufferedWriter(new FileWriter(f));
                fw.write(str);
                fw.flush();

                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return "Test" + fileType;
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
