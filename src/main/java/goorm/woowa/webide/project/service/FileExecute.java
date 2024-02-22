package goorm.woowa.webide.project.service;


import com.google.gson.Gson;
import goorm.woowa.webide.project.domain.ProjectLanguage;
import goorm.woowa.webide.project.domain.dto.ProjectExecute;
import goorm.woowa.webide.project.domain.dto.ProjectResult;
import goorm.woowa.webide.project.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static goorm.woowa.webide.project.domain.ProjectLanguage.*;

@Slf4j
public class FileExecute {

    public static Map<String, String> executeFile(String code, ProjectLanguage language) throws IOException, InterruptedException {

        String command1, command2;
        String fileName;
        Map<String, String> response = null;

        switch (language.name()) {
            case "PYTHON": {
                fileName = FileUtil.makeFileFromCode(code, language);
                command1 = "python3";
                log.info("command={}, filename={}", command1, fileName);
                response = commonProcess(command1, fileName);
                FileUtil.deleteFile(fileName);
                break;
            }
            case "JAVA": {
                fileName = FileUtil.makeFileFromCode(code, language);
                command1 = "javac";
                command2 = "java";
                log.info("command={}, filename={}", command1, fileName);
                response = commonProcess(command1, fileName);
                if (response.keySet().)
                FileUtil.deleteFile(fileName);
                break;
            }
        }

        return response;
    }

    private static ProjectResult commonProcess(String command, String fileName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command, fileName);
        Process p = pb.start();
//        Map<String, String> response = new HashMap<>();
        ProjectResult response = new ProjectResult();

        if (p.getInputStream().read() > 0) { // 결과값이 있으면
//            response.put("result", resultToJson(p.getInputStream()));
            response.setData(resultToJson(p.getInputStream()));
        } else if (p.getErrorStream().available() > 0) { // 오류가 있으면
//            response.put("error", resultToJson(p.getErrorStream()));
        }

        // 프로세스 종료 대기 : 0이면 에러 없음, 1이면 에러 있음
        int exitCode = p.waitFor();
        System.out.println("종료 코드: " + exitCode);

        // 프로세스 강제 종료
        p.destroy();

        // 종료 코드 확인 : 0이면 에러 없음, 1이면 에러 있음
        int exitCode2 = p.exitValue();
        System.out.println("외부 프로그램 종료 코드: " + exitCode2);

        return !response.isEmpty() ? response : Map.of("result", "");
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
