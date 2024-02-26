package goorm.woowa.webide.project.service;


import goorm.woowa.webide.project.domain.ProjectLanguage;
import goorm.woowa.webide.project.domain.dto.ProjectResult;
import goorm.woowa.webide.project.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileExecute {

    public static ProjectResult executeFile(Long projectId, String code, ProjectLanguage language) throws IOException, InterruptedException {

        String command1, command2;
        ProjectResult response = null;
        String fileName = FileUtil.makeFileFromCode(projectId, code, language);

        switch (language.name()) {
            case "PYTHON" -> {
                command1 = "python3";
                log.info("command={}, filename={}", command1, fileName);
                response = commonProcess(command1, fileName);
                FileUtil.deleteFile(fileName);
            }
            case "JAVA" -> {
                command1 = "javac";
                command2 = "java";
                response = commonProcess(command1, fileName);
                FileUtil.deleteFile(fileName);
                if (response.getStatus().equals("error")) {
                    break;
                }
                response = commonProcess(command2, "Test" + projectId);
                FileUtil.deleteFile("Test" + projectId + ".class");
            }
            case "CPP" -> {
                command1 = "g++";
                response = commonProcess(command1, fileName);
                FileUtil.deleteFile(fileName);
                if (response.getStatus().equals("error")) {
                    break;
                }
                response = commonProcess("./a.out", "1");
                FileUtil.deleteFile("./a.out");
            }
        }

        return response;
    }

    private static ProjectResult commonProcess(String... strings) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(strings);
        Process p = pb.start();
        ProjectResult response = new ProjectResult();

        response.setData(convertToString(p.getInputStream()));

        String str = convertToString(p.getErrorStream());
        if (str.length() > 2) {
            log.info("str={}", (str.length()));
            response.setData(str);
        }

        // 프로세스 종료 대기 : 0이면 에러 없음, 1이면 에러 있음
        int exitCode = p.waitFor();
        log.info("exitCode={}", exitCode);
        // 프로세스 강제 종료
        p.destroy();
        // 종료 코드 확인 : 0이면 에러 없음, 1이면 에러 있음
        p.exitValue();

        if (exitCode == 0) {
            response.setStatus("executed");
        } else {
            response.setStatus("error");
        }

        log.info("response={}", response);

        return response;
    }

    public static String convertToString(InputStream inputStream) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString().trim();
        }
    }
}