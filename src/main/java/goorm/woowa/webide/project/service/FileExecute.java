package goorm.woowa.webide.project.service;


import com.google.gson.Gson;
import goorm.woowa.webide.project.domain.ProjectLanguage;
import goorm.woowa.webide.project.domain.dto.ProjectResult;
import goorm.woowa.webide.project.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FileExecute {

    public static ProjectResult executeFile(String code, ProjectLanguage language) throws IOException, InterruptedException {

        String command1, command2;
        String fileName;
        ProjectResult response = null;

        switch (language.name()) {
            case "PYTHON" -> {
                fileName = FileUtil.makeFileFromCode(code, language);
                command1 = "python3";
                log.info("command={}, filename={}", command1, fileName);
                response = commonProcess(command1, fileName);
                FileUtil.deleteFile(fileName);
            }
            case "JAVA" -> {
                fileName = FileUtil.makeFileFromCode(code, language);
                command1 = "javac";
                command2 = "java";
                log.info("command={}, filename={}", command1, fileName);
                response = commonProcess(command1, fileName);
                if (response.getStatus().equals("error")) {
                    FileUtil.deleteFile(fileName);
                    break;
                }
                FileUtil.deleteFile(fileName);
                response = commonProcess(command2, "Test");
                log.info("java response={}", response);
                FileUtil.deleteFile("Test.class");
            }
            case "CPP" -> {
                fileName = FileUtil.makeFileFromCode(code, language);
                command1 = "g++ -o";
                log.info("command={}, filename={}", command1, fileName);
                response = commonProcess(command1, fileName);
            }
        }

        return response;
    }

    private static ProjectResult commonProcess(String command, String fileName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command, fileName);
        Process p = pb.start();
        ProjectResult response = new ProjectResult();

        response.setData(resultToJson(p.getInputStream()));

        String str = resultToJson(p.getErrorStream());
        if (str.length() > 2) {
            log.info("str={}", (str.length()));
            response.setData(str);
        }

        // 프로세스 종료 대기 : 0이면 에러 없음, 1이면 에러 있음
        int exitCode = p.waitFor();
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

    private static String resultToJson(InputStream stream) throws IOException {
        Gson gson = new Gson();
        Map<Integer, String> result = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream));

        int i = 1;
        for (String str; (str = br.readLine()) != null; ) {
            log.info("result={}", str);
            result.put(i++, str);
        }

        return gson.toJson(result);
    }
}
